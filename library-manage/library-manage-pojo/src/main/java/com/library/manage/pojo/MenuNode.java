package com.library.manage.pojo;

import java.util.List;

public class MenuNode {

    private int id;
    private String name;
    private String url;
    private List<TbPermission> subMenus;

    public MenuNode(int id, String name, String url, List<TbPermission> subMenus) {
	super();
	this.id = id;
	this.name = name;
	this.url = url;
	this.subMenus = subMenus;
    }

    public int getId() {
	return id;
    }

    public void setId(int id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public List<TbPermission> getSubMenus() {
	return subMenus;
    }

    public void setSubMenus(List<TbPermission> subMenus) {
	this.subMenus = subMenus;
    }
}
