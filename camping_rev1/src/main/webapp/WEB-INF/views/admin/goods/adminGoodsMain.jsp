<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<script>
	

</script>

<!-- adminGoodsMain.jsp에서 필요한 사항 
	1. -->
</head>
<body>
	<H3>상품 조회</H3>
	<form name="frm" method="post"	action="${contextPath }/admin/goods/adminGoodsMain.do">
		<TABLE cellpadding="10" cellspacing="10">
				<tr>
					<td>
					
					<select name="search_condition" id="search_condition">
							<option value="all" <c:if test="${search_condition == 'all'}">selected</c:if>>전체</option>
							<option value="goods_id" <c:if test="${search_condition =='goods_id'}">selected</c:if>>상품번호</option>
							<option value="goods_title" <c:if test="${search_condition =='goods_title'}">selected</c:if>>상품이름</option>
							<option value="goods_publisher" <c:if test="${search_condition =='goods_publisher'}">selected</c:if>>제조사</option>
					</select> 
					
					<input type="text" name="search" id="search" size="30" value="${search}" /> 
					<button type="submit">조회</button>
					</td>
				</tr>
				<tr>
					<td>입고 일자:<input type="text" name="beginYear" size="4" value="${beginYear }" />년 
								<input type="text" name="beginMonth" size="4" value="${beginMonth}" />월 
								<input type="text" name="beginDay" size="4" value="${beginDay}" />일 
								&nbsp; ~ 
								<input type="text" name="endYear" size="4" value="${endYear }" />년 
								<input type="text" size="4" value="${endMonth }" />월 
								<input type="text" size="4" value="${endDay }" />일
					</td>
				</tr>
			
		</TABLE>
		<DIV class="clear">
		
		
		</DIV>
		
	</form>



	<DIV class="clear"></DIV>
	<TABLE class="list_view">
		<TBODY align=center>
			<tr style="background: #33ff00">
				<td>상품번호</td>
				<td>상품이름</td>
				<td>저자</td>
				<td>출판사</td>
				<td>상품가격</td>
				<td>입고일자</td>
				<td>출판일</td>
			</tr>
			<c:choose>
				<c:when test="${empty newGoodsList }">
					<TR>
						<TD colspan=8 class="fixed"><strong>조회된 상품이 없습니다.</strong></TD>
					</TR>
				</c:when>
				<c:otherwise>
					<c:forEach var="item" items="${newGoodsList }">
						<TR>
							<TD><strong>${item.goods_id }</strong></TD>
							
							<TD>
								<a href="${pageContext.request.contextPath}/admin/goods/modifyGoodsForm.do?goods_id=${item.goods_id}">
									<strong>${item.goods_title } </strong>
								</a>
							</TD>
							
							<TD><strong>${item.goods_writer }</strong></TD>
							<TD><strong>${item.goods_publisher }</strong></TD>
							<td><strong>${item.goods_sales_price }</strong></td>
							<td><strong>${item.goods_credate }</strong></td>
							<td><c:set var="pub_date"
									value="${item.goods_published_date}" /> <c:set var="arr"
									value="${fn:split(pub_date,' ')}" /> <strong> <c:out
										value="${arr[0]}" />
							</strong></td>

						</TR>
					</c:forEach>
				</c:otherwise>
			</c:choose>
			<tr>
				<td colspan=8 class="fixed">
				<c:forEach var="page" begin="1"	end="10" step="1">
		         <c:if test="${section >1 && page==1 }">
		          <a href="${contextPath}/admin/goods/adminGoodsMain.do?search=${search }&search_condition=${search_condition }&section=${section-1}&pageNum=${(section-1)*10 +1 }">&nbsp; &nbsp;</a>
		         </c:if>
		          <a href="${contextPath}/admin/goods/adminGoodsMain.do?search=${search }&search_condition=${search_condition }&section=${section}&pageNum=${page}">${(section-1)*10 +page } </a>
		         <c:if test="${page ==10 }">
		          <a href="${contextPath}/admin/goods/adminGooodsMain.do?search=${search }&search_condition=${search_condition }&section=${section+1}&pageNum=${section*10+1}">&nbsp; next</a>
		         </c:if>  
				</c:forEach>
				</td>
			</tr>
		</TBODY>

	</TABLE>




	<DIV class="clear"></DIV>
	<br>
	<br>
	<br>
	
	<DIV id="search">
		<form action="${contextPath}/admin/goods/addNewGoodsForm.do">
			<input type="submit" value="상품 등록하기">
		</form>
	</DIV>
</body>
</html>