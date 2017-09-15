package com.library.sso.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping("/index")
    public String showIndex() {
	return "index";
    }

    @RequestMapping("/login")
    public String showLogin(String redirectUrl, HttpServletRequest request) {
	request.setAttribute("redirectUrl", redirectUrl);
	return "login";
    }

    @RequestMapping("/register")
    public String showRegister() {
	return "login";
    }

}
