package com.library.portal.service;

import com.library.manage.pojo.ActiveUser;

public interface UserService {
    
    ActiveUser getUserInfoByToken(String token) throws Exception;

    void logout(String token) throws Exception;

    void refreshToken(String token) throws Exception;
}
