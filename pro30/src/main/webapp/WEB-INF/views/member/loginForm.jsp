<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="id" value="${param.id}" />

<%
   request.setCharacterEncoding("UTF-8");
%>   

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
<title>로그인창</title>
<!-- choose내에 주석 삽입하면 오류가 빈번히 발생함 -->
<!-- 로그인 실패시 경고창 보여주기 위해-->
<!-- 회원가입 등록시 입력 id값을 id input태그에 삽입 -->
<!-- 관리가 로그인시 회원리스트 버튼 활성 -->
<c:choose>
	<c:when test="${login == -1 }">
		<script>
			alert("아이디나 비밀번호가 틀립니다. 다시 로그인해주세요.");
		</script>
	</c:when>
</c:choose>

<c:if test="${id != null }">
	<c:set var="id" value="${id}"></c:set>
</c:if>

</head>
<body>
	
	<!--로그인 완료시 메인 화면으로  -->
	<form action="${contextPath}/member/login.do" method="post"> 
		<table>
			<tr>
				<td>아이디</td> 
				<td><input type="text" name="id" value="" size="20" /></td>
			</tr>

			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pwd" value="" size="20" /></td>
			</tr>
			
			<tr>
				<td><input type="submit"  value="로그인" size="20" /></td>
				<td><input type="reset"  value="다시입력" size="20" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
