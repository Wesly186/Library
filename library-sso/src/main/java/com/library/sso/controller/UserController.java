package com.library.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.common.exception.CustomException;
import com.library.common.pojo.ResultModel;
import com.library.manage.pojo.ActiveUser;
import com.library.sso.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/loginSubmit")
    public String loginSubmit(String username, String password, String redirectUrl, HttpServletRequest request,
	    HttpServletResponse response) {
	try {
	    userService.findUserByLoginInfo(username, password, redirectUrl, request, response);
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    request.setAttribute("login.message", ce.getMessage());
	    return "login";
	} catch (Exception e) {
	    e.printStackTrace();
	    request.setAttribute("login.message", "未知错误");
	    return "login";
	}
	if (TextUtils.isBlank(redirectUrl)) {
	    return "redirect:http://www.library.com";
	}
	return "redirect:" + redirectUrl;
    }

    @RequestMapping("/register")
    public String register(String username, String password, HttpServletRequest request) {
	try {
	    userService.register(username, password);
	    return "login";
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    request.setAttribute("register.message", ce.getMessage());
	    return "login";
	} catch (Exception e) {
	    e.printStackTrace();
	    request.setAttribute("register.message", "未知错误");
	    return "login";
	}
    }

    @RequestMapping("/userInfo")
    @ResponseBody
    public ResultModel<ActiveUser> getUserInfoByToken(@RequestParam String token, String callBack) {
	ResultModel<ActiveUser> resultModel = new ResultModel<>();
	try {
	    ActiveUser activeUser = userService.getUserInfoByToken(token);
	    resultModel.setCode(200);
	    resultModel.setData(activeUser);
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    resultModel.setCode(ce.getErrorCode());
	    resultModel.setMessage(ce.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	    resultModel.setCode(40001);
	    resultModel.setMessage(e.getMessage());
	}
	return resultModel;
    }

    @RequestMapping("/refreshSession")
    @ResponseBody
    public ResultModel<String> refreshSession(String token, Integer expire) {
	ResultModel<String> resultModel = new ResultModel<>();
	try {
	    userService.refreshSession(token, expire);
	    resultModel.setCode(200);
	    resultModel.setData("success");
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    resultModel.setCode(ce.getErrorCode());
	    resultModel.setMessage(ce.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	    resultModel.setCode(40001);
	    resultModel.setMessage(e.getMessage());
	}
	return resultModel;
    }
}
