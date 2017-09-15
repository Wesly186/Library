package com.library.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.library.common.exception.CustomException;
import com.library.common.pojo.ResultModel;
import com.library.common.utils.HttpUtils;
import com.library.common.utils.JsonUtils;
import com.library.manage.pojo.ActiveUser;
import com.library.portal.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    private String SSO_BASE_URL;
    @Value("${SSO_USER_INFO_URL}")
    private String SSO_USER_INFO_URL;
    @Value("${SSO_REFRESH_SESSION_URL}")
    private String SSO_REFRESH_SESSION_URL;
    @Value("${SSO_SESSION_EXPIRE}")
    private String SSO_SESSION_EXPIRE;

    @Override
    public ActiveUser getUserInfoByToken(String token) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("token", token);
	String result = HttpUtils.doPost(SSO_BASE_URL + SSO_USER_INFO_URL, param, false);
	ResultModel<ActiveUser> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<ActiveUser>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
	return resultModel.getData();
    }

    @Override
    public void logout(String token) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("token", token);
	param.put("expire", "-1");
	String result = HttpUtils.doPost(SSO_BASE_URL + SSO_REFRESH_SESSION_URL, param, false);
	System.out.println(result);
	ResultModel<String> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<String>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
    }
    
    @Override
    public void refreshToken(String token) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("token", token);
	param.put("expire", SSO_SESSION_EXPIRE);
	String result = HttpUtils.doPost(SSO_BASE_URL + SSO_REFRESH_SESSION_URL, param, false);
	ResultModel<String> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<String>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
    }
}
