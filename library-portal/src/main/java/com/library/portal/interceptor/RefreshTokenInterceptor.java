package com.library.portal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.library.common.utils.CookieUtils;
import com.library.portal.conf.GlobalConstants;
import com.library.portal.service.UserService;

public class RefreshTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	    throws Exception {
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	userService.refreshToken(token);
	return true;
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
