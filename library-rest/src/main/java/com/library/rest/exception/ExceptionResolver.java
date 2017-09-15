package com.library.rest.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.library.common.exception.CustomException;
import com.library.common.pojo.ResultModel;
import com.library.common.utils.JsonUtils;

public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
	    Exception ex) {

	ex.printStackTrace();

	CustomException customException = null;
	if (ex instanceof CustomException) {
	    customException = (CustomException) ex;
	} else {
	    customException = new CustomException(40001, "未知错误");
	}
	// 封装错误信息
	ResultModel<String> responseData = new ResultModel<String>(customException.getErrorCode(),
		customException.getMessage(), null);
	response.setContentType("application/json;charset=utf-8");
	try {
	    response.getWriter().write(JsonUtils.toJson(responseData));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return new ModelAndView();
    }

}
