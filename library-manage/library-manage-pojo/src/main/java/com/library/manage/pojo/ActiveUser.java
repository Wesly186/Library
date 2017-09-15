package com.library.manage.pojo;

import java.util.List;

import com.library.manage.pojo.TbPermission;
import com.library.manage.pojo.TbUser;

public class ActiveUser extends TbUser {

    private List<MenuNode> menus;
    private List<TbPermission> permissions;

    public List<MenuNode> getMenus() {
	return menus;
    }

    public void setMenus(List<MenuNode> menus) {
	this.menus = menus;
    }

    public List<TbPermission> getPermissions() {
	return permissions;
    }

    public void setPermissions(List<TbPermission> permissions) {
	this.permissions = permissions;
    }
}
