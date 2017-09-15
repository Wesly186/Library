package com.library.rest.service;

import com.library.manage.pojo.ActiveUser;

public interface UserService {

    ActiveUser checkLoginInfo(String username, String password) throws Exception;

    void register(String username, String password) throws Exception;
}
