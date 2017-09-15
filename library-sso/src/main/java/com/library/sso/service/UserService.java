package com.library.sso.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.library.manage.pojo.ActiveUser;

public interface UserService {

    void findUserByLoginInfo(String username, String password, String redirectUrl, HttpServletRequest request,
	    HttpServletResponse response) throws Exception;

    ActiveUser getUserInfoByToken(String token) throws Exception;

    void register(String username, String password) throws Exception;

    void refreshSession(String token, Integer expire) throws Exception;

}
