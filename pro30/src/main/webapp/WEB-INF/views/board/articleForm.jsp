<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  /> 
<%
  request.setCharacterEncoding("UTF-8");
%> 

<!--글쓰기 버튼 이후 tiles.xml에 url이 매핑되여 이 페이지로 이동된다.  -->
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script> <!-- jquery 사용하기  -->

<script type="text/javascript">
   
   function readURL(input) {
      if (input.files && input.files[0]) { //input 태그에 파일이 있는 경우???
	     
    	  var reader = new FileReader(); //FileReader 객체 생성
    	  
	      reader.onload = function (e) { //이미지가 로드가 된 경우??
	        $('#preview').attr('src', e.target.result);
          }
         reader.readAsDataURL(input.files[0]); //reader가 이미지를 읽도록 하기.??
      }
  }  
   
   
  function backToList(obj){ /*목록보기 클릭시 action = board/listArticles.do 의 경로로 submit해라. */
    obj.action="${contextPath}/board/listArticles.do";
    obj.submit();
  }
  
  
  var cnt=1;
  
  function fn_addFile(){
	  $("#d_file").append("<br>"+"<input type='file' name='imageFileName"+cnt+"' />"); 
	  cnt++;
	  
	  /* 파일추가버튼 클릭시 함수 실행 
	  	 input태그를 div태크안에 넣는듯함. 선택된파일없은 글귀는 자동생성인듯함.
	  */
  }  

</script>
 <title>글쓰기창</title>
</head>
<body>
<h1 style="text-align:center">글쓰기</h1>
  <form name="articleForm" method="post"   action="${contextPath}/board/addNewArticle.do"   enctype="multipart/form-data">
  	 <!-- enctype 꼭 명시해야 파일이 정상적으로 서버쪽으로 첨부된다 -->
  	 
    <table border="1" align="center">
			
			<tr>
				<td align="right">작성자</td>
				<td colspan=2 align="left">
					<input type="text" size="20" maxlength="100" value="${member.name }" readonly />
				</td>
			</tr>
			
			<tr>
				<td align="right">글제목:</td>
				<td colspan="2">
					<input type="text" size="60" maxlength="500" name="title" />
				</td>
			</tr>
			
			<tr>
				<td align="right" valign="top"><br>글내용:</td>
				<td colspan=2>
					<textarea name="content" rows="10" cols="65" maxlength="4000"></textarea>
				</td>
			</tr>
			
			<tr>
			<!--파일 선택  버튼-->
				<td align="right">이미지파일 첨부:</td> 
				<td>
					<input type="file" name="imageFileName" onchange="readURL(this);" />
					<div id="d_file"></div>
				</td> <!--onchange : input요소를 클릭했을때 ??  파일선택이란 값이 없는데 버튼에 이름 자동생성?-->
				<td>
					<img id="preview" src="#" width=200 height=200 /> <!--파일추가버튼 누를시  파일선택버튼 생성-->
				</td>
			</tr>
			
			<tr>
				<!--파일추가버튼생성  -->
				<td align="right">파일추가버튼</td>
				<td align="left">
					<input type="button" value="파일 추가" onClick="fn_addFile()" />
				</td>
				</tr>
			<tr>
				<td colspan="4"></td>
			</tr> 
			<tr>
				<td align="right"></td>
				<td colspan="2">
					<input type="submit" value="글쓰기" /> 
					<input type=button value="목록보기" onClick="backToList(this.form)" />
				</td>
			</tr>
		</table>
  </form>
</body>
</html>
