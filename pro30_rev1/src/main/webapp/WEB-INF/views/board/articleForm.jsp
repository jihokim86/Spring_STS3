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
   
   //<input type='file'> 하게되면 사용자가 파일을 선택할수있게 윈도우 창이 활성화 된다.
   //사용자가 파일을 선택하면, 선택된 파일의 목록이 FileList 객체 형태로 files 속성에 저장됩니다.
   function readURL(input) { // 매개변수 input 은 아래 input과 다른놈이다.
      if (input.files && input.files[0]) { // input된 files객체 내에 파일이 있는지 여부를 판별
    	  
    	  var reader = new FileReader(); //FileReader 객체 생성  (데이터(속성)를 읽기위한 객체생성)
    	  
	      reader.onload = function (e) { //(e) 이벤트가 발생할때~ 이벤트가 발생한다는거은 reader객체가 생성될때인가 파일을 선택할때인가?
    		  //e 이벤트의 결과는 무엇일까? 
	        $('#preview').attr('src', e.target.result);//#preview 태크내의 속성(arrt)중 src속성에 e.target.result값이 저장되고 reader에 onload하게된다.
          }		
         reader.readAsDataURL(input.files[0]); //input.files[0]의 reader내의 담겨있는 url데이터를 읽는다.
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
  	 
    <table border="0">
			
			<tr>
				<td align="right">작성자</td>
				<td colspan="2" align="left">
					<input type="text" size="20" maxlength="100" value="${member.name }" readonly />
				</td>
			</tr>
			
			<tr>
				<td align="right">글제목:</td>
				<td colspan="2" align="left">
					<input type="text" size="60" maxlength="500" name="title" />
				</td>
			</tr>
			
			<tr>
				<td align="right" valign="top"><br>글내용:</td>
				<td colspan="2" align="left">
					<textarea name="content" rows="10" cols="65" maxlength="4000"></textarea>
				</td>
			</tr>
			
			<tr>
			<!--이미지 파일 선택  버튼-->
			<!--단일 이미지  -->
				<td align="right">이미지파일 첨부:</td> 
				<td>
					<input type="file" name="imageFileName" multiple onchange="readURL(this);" /> <!--multiple은 다중 이미지 선택가능  -->
					<div id="d_file"></div>
				</td> <!-- onchange : input요소를 클릭했을때  -->
				<td>
					<img id="preview" src="#" width=200 height=200 /> 
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
