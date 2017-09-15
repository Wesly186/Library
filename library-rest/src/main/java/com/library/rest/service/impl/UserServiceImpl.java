package com.library.rest.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.common.exception.CustomException;
import com.library.common.utils.MD5Utils;
import com.library.manage.mapper.TbPermissionMapperCustom;
import com.library.manage.mapper.TbUserMapper;
import com.library.manage.pojo.ActiveUser;
import com.library.manage.pojo.MenuNode;
import com.library.manage.pojo.TbPermission;
import com.library.manage.pojo.TbUser;
import com.library.manage.pojo.TbUserExample;
import com.library.manage.pojo.TbUserExample.Criteria;
import com.library.rest.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private TbPermissionMapperCustom permissionMapperCustom;

    @Override
    public ActiveUser checkLoginInfo(String username, String password) throws CustomException {
	TbUserExample example = new TbUserExample();
	Criteria criteria = example.createCriteria();
	criteria.andUsernameEqualTo(username);
	criteria.andPasswordEqualTo(MD5Utils.str2MD5(password));
	List<TbUser> users = userMapper.selectByExample(example);
	if (users == null || users.size() == 0) {
	    throw new CustomException(4002, "用户名或密码错误");
	}
	// 用户信息
	TbUser user = users.get(0);
	ActiveUser activeUser = new ActiveUser();
	activeUser.setId(user.getId());
	activeUser.setPassword(user.getPassword());
	activeUser.setRegisterTime(user.getRegisterTime());
	activeUser.setRoleId(user.getRoleId());
	activeUser.setUsername(user.getUsername());
	// 菜单信息
	List<TbPermission> menus = permissionMapperCustom.selectMenusByUserId(user.getId());
	List<MenuNode> menuTree = getMenuTree(menus);
	activeUser.setMenus(menuTree);
	// 权限信息
	List<TbPermission> permissions = permissionMapperCustom.selectPermissionsByUserId(user.getId());
	activeUser.setPermissions(permissions);
	return activeUser;
    }

    public List<MenuNode> getMenuTree(List<TbPermission> menus) {
	List<MenuNode> menuNodes = new ArrayList<>();
	for (TbPermission permission : menus) {
	    if (permission.getParentId() == 0) {
		menuNodes.add(new MenuNode(permission.getId(), permission.getName(), permission.getUrl(),
			new ArrayList<TbPermission>()));
	    }
	}
	for (MenuNode node : menuNodes) {
	    for (TbPermission permission : menus) {
		if (permission.getParentId() == node.getId()) {
		    node.getSubMenus().add(permission);
		}
	    }
	}
	return menuNodes;
    }

    @Override
    public void register(String username, String password) throws Exception {
	TbUser user = new TbUser();
	user.setPassword(MD5Utils.str2MD5(password));
	user.setRegisterTime(new Date());
	user.setRoleId(1);
	user.setUsername(username);
	userMapper.insertSelective(user);
    }

}
