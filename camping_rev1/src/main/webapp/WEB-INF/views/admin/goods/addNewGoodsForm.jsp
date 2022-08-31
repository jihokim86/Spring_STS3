<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"
	isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />	
<!DOCTYPE html>

<meta charset="utf-8">
<head>
<script type="text/javascript">
  var cnt=0;
  function fn_addFile(){
	  if(cnt ==0){
		  $("#d_file").append("<tr><td>메인이미지</td><td><input  type='file' name='main_image'  /></td></tr>");	  
	  }else{
		  $("#d_file").append("<tr><td>상세이미지"+cnt+"</td><td><input  type='file' name='detail_image"+cnt+"' /></td></tr>");
	  }
  	
  	cnt++;
  }
  
  function resize(obj) {
	  obj.style.height = "1px";
	  obj.style.height = (12+obj.scrollHeight)+"px";
	}
  
  function add_check(){
	  if(add_form.goods_title.value == ""){
		  alert("제목입력바람");
		  goods_title.focus();
		  return false;
	  };
	  if(add_form.goods_writer.value == ""){
		  alert("작성자입력");
		  goods_writer.focus();
		  return false;
	  };
	  if(add_form.goods_publisher.value == ""){
		  alert("출판사입력");
		  goods_publisher.focus();
		  return false;
	  };
	  if(add_form.goods_price.value == ""){
		  alert("제품정가입력");
		  goods_price.focus();
		  return false;
	  };
	  if(add_form.goods_sales_price.value == ""){
		  alert("제품판매가격");
		  goods_sales_price.focus();
		  return false;
	  };
	  if(add_form.goods_point.value == ""){
		  alert("구매포인트");
		  goods_point.focus();
		  return false;
	  };
	  if(add_form.goods_published_date.value == ""){
		  alert("제품출판일");
		  goods_published_date.focus();
		  return false;
	  };
	  if(add_form.goods_delivery_price.value == ""){
		  alert("제품배송비");
		  goods_delivery_price.focus();
		  return false;
	  };
	  if(add_form.goods_intro.value == ""){
		  alert("제품소개");
		  goods_intro.focus();
		  return false;
	  }; 
	  if(add_form.main_image.value == ""){
		  alert("메인이미지첨부");
		  return false;
	  };
	  document.add_form.submit();
}
  
</script>   

<style> textarea.autosize { width:750px; min-height: 300px; } </style>

</head>

<BODY>
<form name="add_form" action="${contextPath}/admin/goods/addNewGoods.do" method="post"  enctype="multipart/form-data">
		<h1>새상품 등록창</h1>
<div class="tab_container">
	<!-- 내용 들어 가는 곳 -->
	<div class="tab_container" id="container">
		<ul class="tabs">
			<li><a href="#tab1">상품정보</a></li>
			<!-- <li><a href="#tab2">상품목차</a></li> -->
			<!-- <li><a href="#tab3">상품저자소개</a></li> -->
			<li><a href="#tab4">상품소개</a></li>
			<!-- <li><a href="#tab5">출판사 상품 평가</a></li> -->
			<!-- <li><a href="#tab6">추천사</a></li> -->
			<li><a href="#tab7">상품이미지</a></li>
		</ul>
		<div class="tab_container">
			<div class="tab_content" id="tab1">
				<table >
			<tr >
				<td width=200 >제품분류</td>
				<td width=500><select name="goods_sort">
						<option value="컴퓨터와 인터넷" selected>컴퓨터와 인터넷
						<option value="디지털 기기">디지털 기기
					</select>
				</td>
			</tr>
			<tr >
				<td >제품이름</td>
				<td><input name="goods_title" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >저자</td>
				<td><input name="goods_writer" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >출판사</td>
				<td><input name="goods_publisher" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품정가</td>
				<td><input name="goods_price" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품판매가격</td>
				<td><input name="goods_sales_price" type="text" size="40" /></td>
			</tr>
			
			
			<tr>
				<td >제품 구매 포인트</td>
				<td><input name="goods_point" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품출판일</td>
				<td><input  name="goods_published_date"  type="date" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품 총 페이지수</td>
				<td><input name="goods_total_page" type="text" size="40" /></td>
			</tr>
			
			<tr>
				<td >ISBN</td>
				<td><input name="goods_isbn" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >제품 배송비</td>
				<td><input name="goods_delivery_price" type="text" size="40" /></td>
			</tr>
			<tr>
				<td >제품 도착 예정일</td>
				<td><input name="goods_delivery_date"  type="date" size="40" /></td>
			</tr>
			
			<tr>
				<td >제품종류</td>
				<td>
				<select name="goods_status">
				  <option value="bestseller"  >베스트셀러</option>
				  <option value="steadyseller" >스테디셀러</option>
				  <option value="newbook" selected >신간</option>
				  <option value="on_sale" >판매중</option>
				  <option value="buy_out" >품절</option>
				  <option value="out_of_print" >절판</option>
				</select>
				</td>
			</tr>
			<tr>
			 <td>
			   <br>
			 </td>
			</tr>
				</table>	
			</div>
			<div class="tab_content" id="tab2">
				<H4>책목차</H4>
				<table>	
				 <tr>
					<td >책목차</td>
					<td><textarea  rows="100" cols="80" name="goods_contents_order"></textarea></td>
				</tr>
				</table>	
			</div>
			<div class="tab_content" id="tab3">
				<H4>제품 저자 소개</H4>
				 <table>
  				 <tr>
					<td>제품 저자 소개</td>
					<td><textarea  rows="100" cols="80" name="goods_writer_intro"></textarea></td>
			    </tr>
			   </table>
			</div>
			<div class="tab_content" id="tab4">
				<!-- <H4>제품소개</H4> -->
				<table>
					<tr>
						<!-- <td >제품소개</td> -->
						<td><textarea name="goods_intro" class="autosize" onkeydown="resize(this)" onkeyup="resize(this)" placeholder="추가 바람"></textarea></td>
				    </tr>
			    </table>
			</div>
			<div class="tab_content" id="tab5">
				<H4>출판사 제품 평가</H4>
				<table>
				 <tr>
					<td>출판사 제품 평가</td>
					<td><textarea  rows="100" cols="80" name="goods_publisher_comment"></textarea></td>
			    </tr>
			</table>
			</div>
			<div class="tab_content" id="tab6">
				<H4>추천사</H4>
				 <table>
					 <tr>
					   <td>추천사</td>
					    <td><textarea  rows="100" cols="80" name="goods_recommendation"></textarea></td>
				    </tr>
			    </table>
			</div>
			<div class="tab_content" id="tab7">
				<!-- <h4>상품이미지</h4> -->
				<table >
					<tr>
						<td align="right">이미지파일 첨부</td>
			            
			            <td  align="left"> <input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
			            <td>
				            <div id="d_file">
				            </div>
			            </td>
					</tr>
				</table>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	
	 <table>
			<tr>
			  <td align=center>
				  <button  type="button" onclick="add_check();">상품등록하기</button>
			  </td>
			</tr>
	 </table>
	 
</div>
</form>	 