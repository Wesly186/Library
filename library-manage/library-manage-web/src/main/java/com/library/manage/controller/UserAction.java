package com.library.manage.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.library.common.utils.CookieUtils;
import com.library.manage.conf.GlobalConstants;
import com.library.manage.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class UserAction extends ActionSupport {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private UserService userService;

    private String username;

    public String logout() throws Exception {
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	userService.logout(token);
	response.sendRedirect(constants.getSSO_HOST() + "/login.do?redirectUrl=" + constants.getMANAGE_HOST());
	return NONE;
    }

    public String checkUsername() throws Exception {
	HttpServletResponse response = ServletActionContext.getResponse();
	boolean result = userService.checkUsername(username);
	PrintWriter writer = response.getWriter();
	writer.write(result + "");
	return NONE;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }
}
