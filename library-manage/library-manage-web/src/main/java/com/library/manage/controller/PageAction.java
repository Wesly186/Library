package com.library.manage.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class PageAction extends ActionSupport {

    public String showIndex() throws Exception {
	return SUCCESS;
    }

    public String showWelcome() {
	return SUCCESS;
    }

    public String showBookAdd() {
	return SUCCESS;
    }

    public String showBookList() {
	return SUCCESS;
    }

    public String showBookEdit() {
	return SUCCESS;
    }

    public String showBookCat() {
	return SUCCESS;
    }

    public String showBookBorrow() {
	return SUCCESS;
    }

    public String showBookReturn() {
	return SUCCESS;
    }

    public String showPay() {
	return SUCCESS;
    }
}
