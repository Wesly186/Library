<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>图书预约</title>
<link rel="shortcut icon" href="http://www.suda.edu.cn/favicon.ico" />
<!-- 布局管理 -->
<link rel="stylesheet" href="/css/bootstrap.min.css">
<!-- Custom CSS -->
<link rel="stylesheet" href="/css/style.css">
</head>
<body>

	<div class="product-big-title-area">
		<div class="container">
			<div class="row">
				<div class="col-md-12">
					<div class="product-bit-title text-center">
						<h2>图书详情</h2>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="single-product-area">
		<div class="zigzag-bottom"></div>
		<div class="container">
			<div class="row">

				<div class="col-md-8">
					<div class="product-content-right">
						<div class="row">
							<div class="col-sm-6">
								<div class="product-images">
									<div class="product-main-img">
										<c:if test="${book.image!=null&&book.image!='' }">
											<img src="${book.image}" width="200px" height="280px">
										</c:if>
										<c:if test="${book.image==null||book.image==''}">
											<img src="/images/book_default.jpg" width="200px" height="280px">
										</c:if>
									</div>
								</div>
							</div>

							<div class="col-sm-6">
								<div class="product-inner">
									<h2 class="product-name" style="margin-left: -15px;">《${book.title}》</h2>
									<div class="product-inner-price">
										<ins>ISBN：${book.id}</ins>
									</div>

									<div class="product-inner-category">
										<p>
											馆藏量：
											<a href="">${book.totalNum}本</a>
											. 剩余图书：
											<a href="">0本</a>
											.
										</p>
									</div>

									<form action="/book/appointment.html" class="cart">
									<input type="hidden" name="bid" value="${book.id}"></input>
										<button class="add_to_cart_button" type="submit">预 约</button>
									</form>
									<div role="tabpanel">
										<div class="tab-content">
											<div role="tabpanel" class="tab-pane fade in active" id="home">
												<h2>图 书 介 绍</h2>
												<p>${book.intro}</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>