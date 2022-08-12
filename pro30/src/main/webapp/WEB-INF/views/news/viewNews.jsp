<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>뉴스 상세 보기</title>
</head>
<body>

	<div>
		<div><span>${listNews.newsTitle}</span></div>
		<div>${listNews.newsWriteDate}</div>
		<hr>
		<div><img src="${contextPath}/download.do?newsNo=${listNews.newsNo}&imageFileName=${listNews.imageFileName}" id="preview" /></div>
		<div>${listNews.newsContent}</div>
		<div>작성자 / 이메일</div>
		<div>
		<c:forEach var="item" items="${listNewsName }">
			${item.newsTitle}<br>
		
		</c:forEach>
		
		</div>
		<div> 댓글창</div>
		<div> 댓글 목록</div>
	</div>
</body>
</html>

<!-- mav.addObject("listNewsName",listNewsName); -->