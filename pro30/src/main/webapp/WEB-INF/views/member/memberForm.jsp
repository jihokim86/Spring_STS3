<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
   request.setCharacterEncoding("UTF-8");
%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입창</title>
</head>
<body>
<form  method="post" action="${contextPath}/member/addMember.do">
	<h1>회원가입</h1>
		<table>
			<tr>
				<td>아이디</td>
				<td><input type="text" name="id" id="id" /></td>
			</tr>

			<tr>
				<td>이름</td>
				<td><input type="text" name="name" id="" /></td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pwd" id="pwd" /></td>
			</tr>

			<tr>
				<td>이메일</td>
				<td><input type="text" name="email" id="eamil" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="가입하기" /></td>
				<td><input type="reset" name="다시입력" /></td>
			</tr>
		</table>
	</form>
</body>
</html>