<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
	<form action="${contextPath}/admin/board/addtextview.do">
		
		<h1>공지사항 글쓰기</h1> 
		<table>
		<tr>
			<td>
			<input type="text" id="writer" name="writer" value="${memberInfo.member_id}" size=4 style="border:0 solid black;">
			<span style="border-left-width:0.1em; border-left-style:solid; border-left-color:#000"></span>&nbsp
			<input type="text"  value="2022-08-30" size=10 style="border:0 solid black;">
			</td>
			
		</tr>
		<tr>
			<td><input type="text" name="title" placeholder="제목을 입력해 주세요" style="width:750px;height:20px;font-size:15px;"></td>
		</tr>
		
		<tr>
			<td><textarea type="text" name="content" placeholder="내용을 입력하세요" cols="98" rows="10" style="padding:10px 10px 10px 10px"></textarea></td>
		</tr>
		
		<tr>
			<td><input type="submit" value="등록"></td>
		</tr>
		</table>
	</form>
</body>
</html>

