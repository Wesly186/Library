package com.library.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.library.common.pojo.ResultModel;
import com.library.manage.pojo.TbBook;
import com.library.rest.service.BookService;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/search")
    @ResponseBody
    public ResultModel<List<TbBook>> searchBook(String keyword) {
	ResultModel<List<TbBook>> resultModel = new ResultModel<>();
	List<TbBook> searchBook = bookService.searchBook(keyword);
	resultModel.setCode(200);
	resultModel.setData(searchBook);
	return resultModel;
    }

    @RequestMapping("/detail/{bid}")
    @ResponseBody
    public ResultModel<TbBook> bookDetail(@PathVariable Long bid) {
	ResultModel<TbBook> resultModel = new ResultModel<>();
	TbBook searchBook = bookService.getBookDetail(bid);
	resultModel.setCode(200);
	resultModel.setData(searchBook);
	return resultModel;
    }

    @RequestMapping("/appointment/{bid}")
    @ResponseBody
    public ResultModel<TbBook> appointmentBook(Integer uid, @PathVariable Long bid) {
	ResultModel<TbBook> resultModel = new ResultModel<>();
	bookService.appointmentBook(uid, bid);
	resultModel.setCode(200);
	resultModel.setData(null);
	return resultModel;
    }
}