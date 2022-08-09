<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
<title>헤더</title>
</head>
<body>

	<table width="100%">
		<tr>

			<td>
				<a href="${contextPath}/main.do"> 
				<img src="${contextPath}/resources/image/duke_swing.gif" />
				</a>
			</td>

			<td>
				<h1><font size=30>뉴스 페이지 만들기</font></h1>
			</td>

			<td>
				<c:choose>

					<c:when test="${login == 1 && memberVO != null}">
						
						<h3>환영합니다. ${memberVO.name}님!</h3>
						
						<c:choose>
							<c:when test="${memberVO.id == 'admin' }">
								<td><a href="${contextPath}/member/listMembers.do"><h3>회원관리</h3></a>
								</td>
								<td><a href="${contextPath}/member/modForm.do"><h3>회원정보</h3></a>
								</td>
								<td><a href="${contextPath}/member/logout.do"><h3>로그아웃</h3></a>
								</td>
							</c:when>
							<c:otherwise>
								<td></td>
								<td><a href="${contextPath}/member/modForm.do"><h3>회원정보</h3></a>
								</td>
								<td><a href="${contextPath}/member/logout.do"><h3>로그아웃</h3></a>
								</td>
							</c:otherwise>
						</c:choose> 
						
					</c:when>

					<c:otherwise>
							<td>
								<a href="${contextPath}/member/memberForm.do"><h3>회원가입</h3></a>
							</td>
					
							<td>
								<a href="${contextPath}/member/loginForm.do"><h3>로그인</h3></a>
							</td>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
	</table>
</body>
</html>