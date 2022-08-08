<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<%
  request.setCharacterEncoding("UTF-8");
%>  
<!DOCTYPE html>
<html>
<head>

 <style>
   .cls1 {text-decoration:none;} 
   
   /* text-decoration : 선으로 텍스트를 꾸미는 속성 
    	- overline : 글자 위에 선을 만듭니다.
    	- none : 선을 만들지 않습니다.
    	속성값을 여러개 사용하여 여러 선을 만들 수 있습니다 */ 
     
    
   .cls2 {text-align:center; font-size:30px;}
   
   /* text-align : 텍스트의 정렬 방향
    	- justify: 양쪽 정렬 (자동 줄바꿈시 오른쪽 경계선 부분 정리) */ 
  </style>
  
  
  <meta charset="UTF-8">
  <title>글목록창</title>
</head>


<script>


	function fn_articleForm(isLogOn,articleForm,loginForm){ 
		/* 글쓰기 버튼 클릭시 함수 실행 
			javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do','${contextPath}/member/loginForm.do')
			매개변수
			1. isLogOn : memberControllerIpml -> public ModelAndView login() -> session.setAttribute("isLogOn", true);
						 => 로그인시 isLogOn =true값을 session 에 저장
			2. articleForm = ${contextPath}/board/articleForm.do
			3. loginForm = ${contextPath}/member/loginForm.do	 
		 */
		
	  if(isLogOn != '' && isLogOn != 'false'){ /* isLogOn 값이 있거나 true이면~ */
	    location.href=articleForm;			   /* location.href=${contextPath}/board/articleForm.do 로 이동하시오 */
	  
	  }else{									/* isLogOn 값이 없거나 false이면~ */
	    alert("로그인 후 글쓰기가 가능합니다.")		/* 경고창 이후 */
	    location.href=loginForm+'?action=/board/articleForm.do';
	  }						/* ${contextPath}/member/loginForm.do?action=/board/articleForm.do 
		  						쿼리스트링 action=/board/articleForm.do 값을 가지고 loginForm.do컨트롤러로 이동해라. */
		  					
	}
</script>



<body>
	<table align="center" border="1" width="80%">
	
		<tr height="10" align="center" bgcolor="lightgreen">
			<td>글번호</td>
			<td>작성자</td>
			<td>제목</td>
			<td>작성일</td>
		</tr>

		<c:choose>
		
			<c:when test="${articlesList ==null }"> 
				<tr height="10">
					<td colspan="4">
						<p align="center">
							<b><span style="font-size: 9pt;">등록된 글이 없습니다.</span></b>
						</p>
					</td>
				</tr>
			</c:when>

		
			<c:when test="${articlesList !=null }">
			
				<c:forEach var="article" items="${articlesList }" varStatus="articleNum">
					
					
					<tr align="center">
					
						<td width="5%">${articleNum.count}</td> 
						
						<td width="10%">${article.id }</td>		
						
						<td align='left' width="50%">
						<span style="padding-left: 30px"></span> 
							<c:choose>
								<c:when test='${article.level > 1 }'> 
								
									<c:forEach begin="1" end="${article.level }" step="1">
										<span style="padding-left: 20px"></span>
									</c:forEach>
									
									<span style="font-size: 12px;">[답변]</span>
									
									<a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title}</a>
								
								</c:when>

								<c:otherwise>
								
									<a class='cls1' href="${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}">${article.title }</a>
							
								</c:otherwise>
								
							</c:choose>
						</td>
						
						<td width="10%">${article.writeDate}</td>
						
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<!-- <a  class="cls1"  href="#"><p class="cls2">글쓰기</p></a> -->
<a  class="cls1"  href="javascript:fn_articleForm('${isLogOn}','${contextPath}/board/articleForm.do', 
                                                    '${contextPath}/member/loginForm.do')"><p class="cls2">글쓰기</p></a>
</body>
</html>