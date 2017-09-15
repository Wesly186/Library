package com.library.portal.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.library.common.exception.CustomException;
import com.library.common.utils.CookieUtils;
import com.library.common.utils.ResourcesUtil;
import com.library.manage.pojo.ActiveUser;
import com.library.portal.conf.GlobalConstants;
import com.library.portal.service.UserService;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {

	// 校验用户访问是否是公开资源地址(无需认证即可访问)
	List<String> openUrls = ResourcesUtil.gekeyList("anonymousURL");

	// 如果访问的是公开 地址则放行
	String url = request.getRequestURI();
	for (String openUrl : openUrls) {
	    if (url.equals(openUrl)) {
		return true;
	    }
	}

	// 校验用户身份是否认证通过
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	try {
	    ActiveUser activeUser = userService.getUserInfoByToken(token);
	    request.setAttribute("activeUser", activeUser);
	    return true;
	} catch (CustomException e) {
	    // 跳转到登陆页面
	    response.sendRedirect(constants.getSSO_HOST() + "/login.do?redirectUrl=" + constants.getPORTAL_HOST());
	    return false;
	}
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
	    throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
	    throws Exception {

    }
}
