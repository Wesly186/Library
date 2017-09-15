package com.library.manage.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.library.common.exception.CustomException;
import com.library.common.utils.CookieUtils;
import com.library.common.utils.ResourcesUtil;
import com.library.manage.conf.GlobalConstants;
import com.library.manage.pojo.ActiveUser;
import com.library.manage.service.UserService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class LoginInterceptor extends MethodFilterInterceptor {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	// 校验用户访问是否是公开资源地址(无需认证即可访问)
	List<String> openUrls = ResourcesUtil.gekeyList("anonymousURL");

	// 如果访问的是公开 地址则放行
	String url = request.getRequestURI();
	for (String openUrl : openUrls) {
	    if (url.equals(openUrl)) {
		return invocation.invoke();
	    }
	}

	// 校验用户身份是否认证通过
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	try {
	    ActiveUser activeUser = userService.getUserInfoByToken(token);
	    request.setAttribute("activeUser", activeUser);
	    return invocation.invoke();
	} catch (CustomException e) {
	    // 跳转到登陆页面
	    response.sendRedirect(constants.getSSO_HOST() + "/login.do?redirectUrl=" + constants.getMANAGE_HOST());
	    return Action.NONE;
	}
    }
}