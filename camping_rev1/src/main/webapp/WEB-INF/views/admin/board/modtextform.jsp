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
	<form action="${contextPath}/admin/board/modtextview.do" method="get">
	<h1>공지사항 수정하기</h1>
	<br>
	 	<table>
			<tr>
				<td width="50">BNO</td>
				<td width="50">title</td>
				<td>writer</td>
				<td>regdate</td>
				<td>updateDate</td>
			</tr>
			<tr>
				<td><input type="text" name="bno" value="${textview.bno }" readonly size="3"></td>
				<td><input type="text" name="title" value="${textview.title }" size="52"></td>
				<td><input type="text" name="writer" value="${textview.writer }" readonly size="10"></td>
				<td><input type="text" name="regdate" value="${textview.regdate }" disabled size="10"></td>
				<td><input type="text" name="updateDate" value="${textview.updateDate }" disabled size="10"></td>
			</tr>
			<tr>
				 <td colspan="5"><textarea rows="20" cols="100" name="content">${textview.content }</textarea></td> 
			</tr>
		</table>
		
		<table>
			<tr>
				<td >
					<input type="submit" value="수정하기" />
				</td>
			</tr>
		 </table>
	</form>
</body>
</html>