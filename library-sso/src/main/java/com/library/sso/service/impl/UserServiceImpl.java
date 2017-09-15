package com.library.sso.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.library.common.exception.CustomException;
import com.library.common.pojo.ResultModel;
import com.library.common.utils.CookieUtils;
import com.library.common.utils.HttpUtils;
import com.library.common.utils.JsonUtils;
import com.library.manage.pojo.ActiveUser;
import com.library.sso.dao.JedisDao;
import com.library.sso.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_USER_LOGIN_URL}")
    private String REST_USER_LOGIN_URL;
    @Value("${REST_USER_REGISTER_URL}")
    private String REST_USER_REGISTER_URL;
    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;
    @Value("${SSO_SESSION_EXPIRE}")
    private int SSO_SESSION_EXPIRE;
    @Value("${HOST_MANAGE}")
    private String HOST_MANAGE;
    @Value("${HOST_PORTAL}")
    private String HOST_PORTAL;
    @Value("${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Autowired
    private JedisDao jedisDao;

    @Override
    public void findUserByLoginInfo(String username, String password, String redirectUrl, HttpServletRequest request,
	    HttpServletResponse response) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("username", username);
	param.put("password", password);
	String result = HttpUtils.doPost(REST_BASE_URL + REST_USER_LOGIN_URL, param, false);
	ResultModel<ActiveUser> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<ActiveUser>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
	ActiveUser activeUser = resultModel.getData();
	if (!TextUtils.isBlank(redirectUrl)) {
	    if ((redirectUrl.contains(HOST_MANAGE) && activeUser.getRoleId() == 1) || (redirectUrl.contains(HOST_PORTAL)
		    && (activeUser.getRoleId() == 2 || activeUser.getRoleId() == 3))) {
		throw new CustomException(4003, "权限不足，请注册新账户或联系管理员");
	    }
	}
	// 保存用户之前，把用户对象中的密码清空。
	activeUser.setPassword(null);

	String existToken = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
	if (existToken != null) {
	    String info = jedisDao.get(REDIS_USER_SESSION_KEY + ":" + existToken);
	    if (info != null) {
		jedisDao.expire(REDIS_USER_SESSION_KEY + ":" + existToken, SSO_SESSION_EXPIRE);
	    } else {
		// 把用户信息写入redis
		jedisDao.set(REDIS_USER_SESSION_KEY + ":" + existToken, JsonUtils.toJson(activeUser));
		// 设置session的过期时间
		jedisDao.expire(REDIS_USER_SESSION_KEY + ":" + existToken, SSO_SESSION_EXPIRE);
	    }
	} else {
	    // 生成token
	    String token = UUID.randomUUID().toString();
	    // 把用户信息写入redis
	    jedisDao.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.toJson(activeUser));
	    // 设置session的过期时间
	    jedisDao.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
	    // 添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
	    CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
	}
    }

    @Override
    public ActiveUser getUserInfoByToken(String token) throws CustomException {
	String info = jedisDao.get(REDIS_USER_SESSION_KEY + ":" + token);
	if (info == null) {
	    throw new CustomException(40004, "身份认证过期，请重新登陆");
	}
	ActiveUser activeUser = JsonUtils.fromJson(info, ActiveUser.class);
	return activeUser;
    }

    @Override
    public void register(String username, String password) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("username", username);
	param.put("password", password);
	String result = HttpUtils.doPost(REST_BASE_URL + REST_USER_REGISTER_URL, param, false);
	ResultModel<String> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<String>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
    }

    @Override
    public void refreshSession(String token, Integer expire) throws CustomException {
	if (expire == null) {
	    jedisDao.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
	} else {
	    if(expire>SSO_SESSION_EXPIRE){
		throw new CustomException(40007, "超出最大时间");
	    }
	    jedisDao.expire(REDIS_USER_SESSION_KEY + ":" + token, expire);
	}
    }

}
