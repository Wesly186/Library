package com.library.portal.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.reflect.TypeToken;
import com.library.common.exception.CustomException;
import com.library.common.pojo.ResultModel;
import com.library.common.utils.HttpUtils;
import com.library.common.utils.JsonUtils;
import com.library.manage.pojo.TbBook;
import com.library.portal.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_SEARCH_BOOK_URL}")
    private String REST_SEARCH_BOOK_URL;
    @Value("${REST_BOOK_DETAIL_URL}")
    private String REST_BOOK_DETAIL_URL;
    @Value("${REST_APPOINTMENT_BOOK_URL}")
    private String REST_APPOINTMENT_BOOK_URL;

    @Override
    public List<TbBook> searchBook(String keyword) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("keyword", keyword);
	String result = HttpUtils.doPost(REST_BASE_URL + REST_SEARCH_BOOK_URL, param, false);
	ResultModel<List<TbBook>> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<List<TbBook>>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
	return resultModel.getData();
    }

    @Override
    public void appointmentBook(int uid, Long bid) throws CustomException {
	Map<String, String> param = new HashMap<>();
	param.put("uid", uid + "");
	String result = HttpUtils.doPost(REST_BASE_URL + REST_APPOINTMENT_BOOK_URL + "/" + bid, param, false);
	ResultModel<TbBook> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<TbBook>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
    }

    @Override
    public TbBook getBookDetail(Long id) throws CustomException {
	Map<String, String> param = new HashMap<>();
	String result = HttpUtils.doPost(REST_BASE_URL + REST_BOOK_DETAIL_URL + "/" + id, param, false);
	ResultModel<TbBook> resultModel = JsonUtils.fromJson(result, new TypeToken<ResultModel<TbBook>>() {
	}.getType());
	if (resultModel.getCode() != 200) {
	    throw new CustomException(resultModel.getCode(), resultModel.getMessage());
	}
	return resultModel.getData();
    }
}
