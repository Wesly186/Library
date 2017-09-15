package com.library.manage.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.library.common.pojo.EUITreeNode;
import com.library.common.pojo.ResultModel;
import com.library.manage.pojo.TbBookCat;
import com.library.manage.service.BookCatService;
import com.opensymphony.xwork2.ActionSupport;

@Controller
@Scope("prototype")
@SuppressWarnings("serial")
public class BookCatAction extends ActionSupport {

    private Integer id = 0;
    private Integer parentId;
    private String name;
    private List<EUITreeNode> treeResult;
    private ResultModel<EUITreeNode> result;

    @Autowired
    private BookCatService bookCatService;

    public String getBookCatList() {
	treeResult = new ArrayList<>();
	List<TbBookCat> bookCatList = bookCatService.getBookCatList(id);
	for (TbBookCat cat : bookCatList) {
	    EUITreeNode treeNode = new EUITreeNode();
	    treeNode.setId(cat.getId());
	    treeNode.setText(cat.getName());
	    treeNode.setState(cat.getIsParent() ? "closed" : "open");
	    treeResult.add(treeNode);
	}
	return SUCCESS;
    }

    public String createBookCat() {
	TbBookCat bookCat = bookCatService.createNode(parentId, name);
	EUITreeNode node = new EUITreeNode();
	node.setId(bookCat.getId());
	node.setText(bookCat.getName());
	node.setState("open");
	result = new ResultModel<>(200, null, node);
	return SUCCESS;
    }

    public String deleteBookCat() {
	result = new ResultModel<>();
	try {
	    bookCatService.deleteNode(parentId, id);
	    result.setCode(200);
	} catch (Exception e) {
	    e.printStackTrace();
	    result.setCode(40001);
	}
	return SUCCESS;
    }

    public String updateBookCat() {
	result = new ResultModel<>();
	try {
	    bookCatService.updateNode(id, name);
	    result.setCode(200);
	} catch (Exception e) {
	    e.printStackTrace();
	    result.setCode(40001);
	}
	return SUCCESS;
    }

    public Integer getId() {
	return id;
    }

    public void setId(Integer id) {
	this.id = id;
    }

    public List<EUITreeNode> getTreeResult() {
	return treeResult;
    }

    public void setTreeResult(List<EUITreeNode> treeResult) {
	this.treeResult = treeResult;
    }

    public ResultModel<EUITreeNode> getResult() {
	return result;
    }

    public void setResult(ResultModel<EUITreeNode> result) {
	this.result = result;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
