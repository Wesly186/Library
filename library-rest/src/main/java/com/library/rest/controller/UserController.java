package com.library.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.common.pojo.ResultModel;
import com.library.manage.pojo.ActiveUser;
import com.library.rest.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/loginSubmit")
    @ResponseBody
    public ResultModel<ActiveUser> loginSubmit(@RequestParam String username, @RequestParam String password) throws Exception {
	ResultModel<ActiveUser> resultModel = new ResultModel<>();
	ActiveUser activeUser = userService.checkLoginInfo(username, password);
	resultModel.setCode(200);
	resultModel.setData(activeUser);
	return resultModel;
    }

    @RequestMapping("/register")
    @ResponseBody
    public ResultModel<String> register(@RequestParam String username, @RequestParam String password) throws Exception {
	ResultModel<String> resultModel = new ResultModel<>();
	userService.register(username, password);
	resultModel.setCode(200);
	return resultModel;
    }
}
