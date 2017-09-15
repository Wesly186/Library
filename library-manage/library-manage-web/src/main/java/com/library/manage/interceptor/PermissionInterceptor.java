package com.library.manage.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.library.common.utils.ResourcesUtil;
import com.library.manage.pojo.ActiveUser;
import com.library.manage.pojo.TbPermission;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class PermissionInterceptor extends MethodFilterInterceptor {

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

	HttpServletRequest request = ServletActionContext.getRequest();

	String url = request.getRequestURI();
	System.out.println("url: " + url);
	// 校验用户访问是否是公开资源地址(无需认证即可访问)
	List<String> openUrls = ResourcesUtil.gekeyList("anonymousURL");
	for (String openUrl : openUrls) {
	    if (url.equals(openUrl)) {
		return invocation.invoke();
	    }
	}

	// 如果访问的是公共地址则放行
	List<String> commonUrls = ResourcesUtil.gekeyList("commonURL");
	for (String commonUrl : commonUrls) {
	    if (url.equals(commonUrl)) {
		return invocation.invoke();
	    }
	}

	// 校验用户身份是否认证通过
	ActiveUser activeUser = (ActiveUser) request.getAttribute("activeUser");
	List<TbPermission> permissions = activeUser.getPermissions();
	System.out.println(permissions.toString());
	// 校验用户访问地址是否在用户权限范围内
	for (TbPermission permission : permissions) {
	    String permissionUrl = permission.getUrl();
	    if (url.contains(permissionUrl)) {
		return invocation.invoke();
	    }
	}
	return "refuse";
    }
}