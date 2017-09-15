package com.library.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.library.manage.pojo.TbBook;
import com.library.portal.service.BookService;

@Controller
public class PageController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/index")
    public ModelAndView showIndex() throws Exception {
	ModelAndView modelAndView = new ModelAndView();
	List<TbBook> searchBook = bookService.searchBook("");
	modelAndView.addObject("searchBook", searchBook);
	modelAndView.setViewName("index");
	return modelAndView;
    }
}
