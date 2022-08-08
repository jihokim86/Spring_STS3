<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
<c:set var="result" value="${param.result }" />
<%
   request.setCharacterEncoding("UTF-8");
%>     
<!DOCTYPE html>
<html>
<head>

  <meta charset="UTF-8">
<title>로그인창</title>

<!-- header.jsp 에서 로그인 버튼을 클릭했을때 지금 이 페이지인 loginForm.jsp로 이동한다. 
	로그인 버튼을 클릭했을시 action 과 result는 null값으로 넘어왔다는걸 기억하자.
	로그인 버튼 클릭시 reuslt는 null값으로 넘어왔기에. test = false값이므로 when태크는 실행되지 않는다.
	
	아이디와 비밀번호 입력 후 summit 버튼을 클릭시 member/login.do 컨트롤러로 이동하며. 
	
	DB의 id,pwd를 이용한 쿼리문이 실패하면
	result=='loginFailed'값을 저장한채 이쪽 페이지로 이동하기에 alert가 작동하고 ~ 다시 loginForm.jsp 를 보여준다.
	
	DB의 id,pwd를 이용한 쿼리문이 성공하면
	/member/listMembers.do로 이동하게된다.
	
-->
<c:choose>
	<c:when test="${result=='loginFailed' }"> 
	  <script>
	    window.onload=function(){
	      alert("아이디나 비밀번호가 틀립니다.다시 로그인 하세요!");
	    }
	  </script>
	</c:when>
</c:choose>  


</head>

<body>

<!--form형식이며 summit버튼을 누를시 post방식으로 ${contextPath}/member/login.do 컨트롤러로 이동한다는걸 알수 있다.
	post방식이므로 url?id=hong&pwd=1212가 보이지 않게 전달된다.
-->

<form name="frmLogin" method="post"  action="${contextPath}/member/login.do">

<!-- form내부에 table를 생성할수 있구나.  3행으로 구성되어 있다.-->
   <table border="1"  width="80%" align="center" > <!--폭을 80%으로 하게되면 윈도우 창 크기에 따라 가변적이게 된다.  -->

			<tr align="center"> <!-- 1행 아이디 비밀번호-->
				<td>아이디</td>
				<td>비밀번호</td>
			</tr>

			<tr align="center"><!-- 2행 입력칸 -->
				<td><input type="text" name="id" value="" size="20"></td>
				<td><input type="password" name="pwd" value="" size="20">
				</td>
			</tr>
			
			<tr align="center"><!-- 3행 버튼칸 -->
				<td colspan="2">
					<input type="submit" value="로그인"> 
					<input type="reset" value="다시입력">
				</td>
			</tr>
			
		</table>
   
</form>


</body>
</html>
