package com.library.manage.controller;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.library.common.exception.CustomException;
import com.library.common.pojo.EUDateGridRsp;
import com.library.common.pojo.ResultModel;
import com.library.manage.pojo.TbBook;
import com.library.manage.service.BookService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class BookAction extends ActionSupport {

    private Long id;
    private String title;
    private Integer cid;
    private String image;
    private Integer totalNum;
    private String intro;
    private String ids;
    private Integer page;
    private Integer rows;
    private String username;

    @Autowired
    @Resource
    private BookService bookService;
    private ResultModel<String> result;
    private EUDateGridRsp euResult;

    public String addBook() {

	result = new ResultModel<>();

	if (cid == null) {
	    result.setCode(40000);
	    result.setMessage("请检查参数");
	    return SUCCESS;
	}

	TbBook book = new TbBook();
	book.setId(id);
	book.setTitle(title);
	book.setCid(cid);
	book.setImage(image);
	book.setTotalNum(totalNum);
	book.setBorrowedNum(0);
	book.setIntro(intro);

	try {
	    bookService.addBook(book);
	    result.setCode(200);
	} catch (Exception e) {
	    result.setCode(40001);
	    result.setMessage("未知错误");
	    e.printStackTrace();
	}
	return SUCCESS;
    }

    public String deleteBook() {

	result = new ResultModel<>();
	try {
	    String[] idList = ids.split(",");
	    for (String id : idList) {
		bookService.deleteById(Long.parseLong(id));
	    }
	    result.setCode(200);
	} catch (Exception e) {
	    result.setCode(40001);
	    result.setMessage("数据库出错");
	}
	return SUCCESS;
    }

    public String updateBook() {

	result = new ResultModel<>();

	TbBook book = new TbBook();
	book.setId(id);
	book.setTitle(title);
	book.setCid(cid);
	book.setImage(image);
	book.setTotalNum(totalNum);
	book.setBorrowedNum(0);
	book.setIntro(intro);

	try {
	    bookService.updateBook(book);
	    result.setCode(200);
	} catch (Exception e) {
	    result.setCode(40001);
	    result.setMessage("数据库出错");
	}
	return SUCCESS;
    }

    public String getBookList() {
	euResult = bookService.getBookList(page, rows);
	return SUCCESS;
    }

    public String borrowBook() {
	result = new ResultModel<>();
	try {
	    bookService.borrowBook(username, title);
	    result.setCode(200);
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    result.setCode(ce.getErrorCode());
	    result.setMessage(ce.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	    result.setCode(4001);
	    result.setMessage("未知错误");
	}
	return SUCCESS;
    }

    public String returnBook() {
	result = new ResultModel<>();
	try {
	    bookService.returnBook(username, title);
	    result.setCode(200);
	} catch (CustomException ce) {
	    ce.printStackTrace();
	    result.setCode(ce.getErrorCode());
	    result.setMessage(ce.getMessage());
	} catch (Exception e) {
	    e.printStackTrace();
	    result.setCode(4001);
	    result.setMessage("未知错误");
	}
	return SUCCESS;
    }

    public String checkBookName() throws Exception {
	HttpServletResponse response = ServletActionContext.getResponse();
	boolean result = bookService.checkBookName(title);
	PrintWriter writer = response.getWriter();
	writer.write(result + "");
	return NONE;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public Integer getCid() {
	return cid;
    }

    public void setCid(Integer cid) {
	this.cid = cid;
    }

    public String getImage() {
	return image;
    }

    public void setImage(String image) {
	this.image = image;
    }

    public Integer getTotalNum() {
	return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
	this.totalNum = totalNum;
    }

    public String getIntro() {
	return intro;
    }

    public void setIntro(String intro) {
	this.intro = intro;
    }

    public String getIds() {
	return ids;
    }

    public void setIds(String ids) {
	this.ids = ids;
    }

    public Integer getPage() {
	return page;
    }

    public void setPage(Integer page) {
	this.page = page;
    }

    public Integer getRows() {
	return rows;
    }

    public void setRows(Integer rows) {
	this.rows = rows;
    }

    public String getUsername() {
	return username;
    }

    public void setUsername(String username) {
	this.username = username;
    }

    public ResultModel<String> getResult() {
	return result;
    }

    public void setResult(ResultModel<String> result) {
	this.result = result;
    }

    public EUDateGridRsp getEuResult() {
	return euResult;
    }

    public void setEuResult(EUDateGridRsp euResult) {
	this.euResult = euResult;
    }

}
