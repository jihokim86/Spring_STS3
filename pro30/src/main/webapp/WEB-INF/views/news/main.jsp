<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<%
request.setCharacterEncoding("UTF-8");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뉴스 목록</title>
</head>
<body>
	<h2>메인페이지이다</h2>
	<div style="height: auto; width: 100%; border: 1px solid gold;">
		<div style="border: 1px solid black; width: 30%;">
			<h1 style="display: table-cell;">
				<a href="#">home</a>
			</h1>
			<div style="display: table-cell;">
				<a href="#">스포츠</a> <a href="#">날씨</a> <a href="#">증권</a>
			</div>
			<div style="display: table-cell;">
				<a href="${contextPath}/news/addNewsForm.do">글쓰기(관리지만)</a> 
			</div>
		</div>
		<c:forEach var="listNews" items="${listNews }">
		 	
			<div style="height: auto; width: 100%; border: 1px solid red;">

				<div style="height: auto; width: 100px; border: 1px solid red; display: table-cell">
				<a href="${contextPath}/news/viewNews.do?newsNo=${listNews.newsNo}&name=${listNews.name}">
					<img src="${contextPath}/download.do?newsNo=${listNews.newsNo}&imageFileName=${listNews.imageFileName}" id="preview" width="100px"/>
				</a>
				</div>

				<div style="height: auto; border: 1px solid red; display: table-cell; vertical-align: middle;">
					<a href="${contextPath}/news/viewNews.do?newsNo=${listNews.newsNo}&name=${listNews.name}">
					<span>${listNews.newsTitle }</span>
					</a>

					<div style="height: auto; border: 1px solid red; display: table-cell; vertical-align: middle;">
						<a>${listNews.newsContent }</a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</body>
</html>