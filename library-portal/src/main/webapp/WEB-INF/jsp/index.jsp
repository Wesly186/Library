<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>图书检索预约系统</title>
<link rel="shortcut icon" href="http://www.suda.edu.cn/favicon.ico" />
<link rel="stylesheet" type="text/css" href="/css/index.css">
</head>

<body>
	<div id="container1">
		<h1>图书检索</h1>
		<form action="/book/search.html" id="search_box">
			<div class="wrapper">
				<input type="text" id="keyword" name="keyword" placeholder="输入关键字" />
				<button type="submit" class="search_btn">
					<img src="/images/search_icon.png" />
				</button>
			</div>
		</form>
	</div>

	<div id="container2">
		<c:forEach items="${searchBook}" var="book">
			<div class="book_list">
				<div>
					<a target="_blank" href="/book/detail.html?id=${book.id}">
						<c:if test="${book.image!=null&&book.image!='' }">
							<img src="${book.image}" width="170px" height="230px">
						</c:if>
						<c:if test="${book.image==null||book.image==''}">
							<img src="/images/book_default.jpg" width="170px" height="230px">
						</c:if>
					</a>
				</div>
				<div id="title">《${book.title}》</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>