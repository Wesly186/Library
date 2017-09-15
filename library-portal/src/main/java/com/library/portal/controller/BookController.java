package com.library.portal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.library.common.utils.CookieUtils;
import com.library.manage.pojo.ActiveUser;
import com.library.manage.pojo.TbBook;
import com.library.portal.conf.GlobalConstants;
import com.library.portal.service.BookService;
import com.library.portal.service.UserService;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private GlobalConstants constants;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @RequestMapping("/search")
    public ModelAndView searchBook(String keyword) throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	List<TbBook> searchBook = bookService.searchBook(keyword);
	modelAndView.addObject("searchBook", searchBook);
	modelAndView.setViewName("index");
	return modelAndView;
    }

    @RequestMapping("/detail")
    public ModelAndView bookDetail(Long id) throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	TbBook book = bookService.getBookDetail(id);
	modelAndView.addObject("book", book);
	modelAndView.setViewName("bookDetail");
	return modelAndView;
    }

    @RequestMapping("/appointment")
    public String appointmentBook(HttpServletRequest request, Long bid) throws Exception {
	String token = CookieUtils.getCookieValue(request, constants.getCOOKIE_TOKEN_KEY());
	ActiveUser activeUser = userService.getUserInfoByToken(token);
	bookService.appointmentBook(activeUser.getId(), bid);
	return "success";
    }
}
