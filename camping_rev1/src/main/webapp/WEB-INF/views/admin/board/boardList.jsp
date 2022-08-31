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
<title>Insert title here</title>
</head>
<body>
	<form action="#">
		<h1>공지사항 리스트</h1>
		<br>
		<table width="750">
			<tr>
				 <td align="center" width="30" border="1">글번호</td>
				 <td width="250">제목</td>
				 <!-- <td width="150">내용</td> -->
				 <td width="30">작성자</td>
				 <td width="40">등록일자</td>
				 
			</tr>
			<c:forEach var="boardList" items="${boardList}" varStatus="status">
			<tr>
				 <td align="center"><c:out value="${status.count }"></c:out></td>
				 <td><a href="${contextPath}/admin/board/textview.do?bno=${boardList.bno }">${boardList.title }</a></td>
				 <%-- <td>${boardList.content }</td> --%>
				 <td>${boardList.writer }</td>
				 <td>${boardList.regdate }</td>
			</tr>
			</c:forEach>
			</table>
			<table>
			<tr>
				<td>
				 <button type="button" onclick="location.href='${contextPath}/admin/board/addtextform.do';">글쓰기</button> 
				<tr>
			</tr>
		</table>
	</form>
</body>
</html>