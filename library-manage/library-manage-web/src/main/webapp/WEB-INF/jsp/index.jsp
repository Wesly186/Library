<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>苏州大学图书管理系统</title>
<link rel="shortcut icon" href="http://www.suda.edu.cn/favicon.ico" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<link rel="stylesheet" type="text/css" href="css/common.css" />
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<style type="text/css">
.content {
	padding: 10px 10px 10px 10px;
}
</style>
</head>
<body class="easyui-layout" style='background: rgb(103, 143, 194);'>
	<DIV
		style='margin-top: 5px; background: rgb(149, 184, 231); height: 65px; color: rgb(255, 255, 255); line-height: 20px; overflow: hidden; font-family: Verdana, 微软雅黑, 黑体;'
		border="false" split="true" region="north">
		<SPAN style="padding-left: 10px; font-size: 22px;">
			<IMG align="absmiddle" src="images/logo.png" width="200" height="60"> 图书管理系统
		</SPAN>
		<SPAN style="padding-right: 20px; float: right;" class="head">
			欢迎您：${activeUser.username}&nbsp;&nbsp;
			<A id="loginOut" href=javascript:logout()>退出系统</A>
		</SPAN>
	</DIV>

	<div data-options="region:'west',title:'菜单',split:true" style="width: 180px;">
		<c:if test="${activeUser.menus!=null }">
			<ul id="menu" class="easyui-tree" style="margin-top: 10px; margin-left: 5px;">
				<c:forEach items="${activeUser.menus }" var="menu">
					<li>
						<span>${menu.name}</span>
						<c:if test="${menu.subMenus!=null }">
							<ul>
								<c:forEach items="${menu.subMenus }" var="subMenu">
									<li data-options="attributes:{'url':'${subMenu.url}'}">${subMenu.name}</li>
								</c:forEach>
							</ul>
						</c:if>
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
	<div data-options="region:'center',title:''">
		<div id="tabs" class="easyui-tabs" border="false">
		</div>
	</div>

	<DIV style="background: rgb(210, 224, 242); height: 30px;" split="false" region="south">

		<DIV class="footer" style="margin-top: 9px; margin-left: 5px">
			<font color="#60819D">系统版本号：V1.0 &nbsp;&nbsp;&nbsp;发布日期：2017-6-15</font>
		</DIV>
	</DIV>

	<script type="text/javascript">
		$(function() {
			$('#tabs').tabs('add', {
				title : "欢迎使用",
				href : "welcome.do",
				closable : false,
				bodyCls : "content"
			});
			$('#menu').tree({
				onClick : function(node) {
					if ($('#menu').tree("isLeaf", node.target)) {
						var tabs = $("#tabs");
						var tab = tabs.tabs("getTab", node.text);
						if (tab) {
							tabs.tabs("select", node.text);
						} else {
							tabs.tabs('add', {
								title : node.text,
								href : node.attributes.url,
								closable : true,
								bodyCls : "content"
							});
						}
					}
				}
			});
		});
		$(function() {
		    $('#loginOut').click(function() {
		        $.messager.confirm('系统提示', '您确定要退出本次登录吗?',
		        function(r) {
		            if (r) {
		                location.href = '${pageContext.request.contextPath}/user/logout.do';
		            }
		        });

		    })
		});
	</script>
</body>
</html>