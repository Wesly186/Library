package com.library.portal.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.library.common.utils.CookieUtils;
import com.library.portal.conf.GlobalConstants;
import com.library.portal.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) throws Exception {
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	userService.logout(token);
	return "redirect:" + constants.getSSO_HOST() + "/login.do?redirectUrl=" + constants.getPORTAL_HOST();
    }
}
