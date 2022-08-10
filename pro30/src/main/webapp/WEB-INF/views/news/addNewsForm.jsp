<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<c:set var="contextPath"  value="${pageContext.request.contextPath}" />

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
<form method="post" action="${contextPath}/news/addNews.do" enctype="multipart/form-data">
<table>

	<tr>
		<td>작성자</td>
		<td><input type="text" id="" name="name" /></td>
	</tr>
	
	<tr>
		<td>제목</td>
		<td><input type="text" id="" name="newsTitle"/></td>
	</tr>
	
	<tr>
		<td>글내용</td>
		<td><textarea cols="65" rows="10" name="newsContent"></textarea></td>
	</tr>
	
	<tr>
		<td>이미지 업로드(미리보기)</td>
		<td><input type="file" name="imageFileName" onchange="readURL(this)"/></td>
		<td><img id="preview" src="#" width=200 height=200 /></td>
	</tr>
	
	<tr>
		<td><input type="submit" value="등록하기"/></td>
		<td>리스트로 돌아가기</td>
	</tr>
	
	
</table>
</form>
</body>
</html>