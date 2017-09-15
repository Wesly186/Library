package com.library.manage.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.library.common.utils.CookieUtils;
import com.library.manage.conf.GlobalConstants;
import com.library.manage.service.UserService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class RefreshTokenInterceptor extends MethodFilterInterceptor {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

	HttpServletRequest request = ServletActionContext.getRequest();

	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	userService.refreshToken(token);
	return invocation.invoke();
    }
}