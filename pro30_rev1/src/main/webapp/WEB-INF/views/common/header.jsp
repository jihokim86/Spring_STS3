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

<!-- 로그인 관련이 포함된 헤더파일  -->
<!-- 1행에 3열이 존재함 -->

	<table border=0 width="100%">
		<tr>

			<td><a href="${contextPath}/main.do"> <img
					src="${contextPath}/resources/image/duke_swing.gif" />
			</a></td>

			<td>
				<h1>
					<font size=30>스프링실습 홈페이지!!</font>
				</h1>
			</td>

			<td>
			
				<c:choose>

					<c:when test="${isLogOn == true  && member!= null}">
						<h3>환영합니다. ${member.name }님!</h3>
						<a href="${contextPath}/member/logout.do"><h3>로그아웃</h3></a>
						<!-- 초기 main.do 요청시 isLogOn, member등 set되지 않기에 false, null값을 가지므로 "로그인"글씨가 보인다. -->
						<!-- 로그인을 완료하면...header는 자동으로 리셋되나????? 흠.... 
							로그인이 완료되면. 회원목록창으로 이동하게 되는 동시에 header가 리셋되는것같다.
							session.setAttribute("member", memberVO);	
	    					session.setAttribute("isLogOn", true);
	    					session에 위 두개를 저장했기에~ when태그 조건에 부합되여 실행된다. -->
	    				<!-- 로그아웃 버튼 클릭시  ${contextPath}/member/logout.do 컨트롤러로 이동한다.-->
					</c:when>

					<c:otherwise>
						<a href="${contextPath}/member/loginForm.do"><h3>로그인</h3></a>
						
					<!-- 	로그인 버튼을 클릭시 loginForm.do 컨트롤러로 이동 -> 로그인 JSP를 보여줌.
	        	 	loginForm.do 컨트롤러 소스
	       			 @RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
										private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							 									  @RequestParam(value= "action", required=false) String action,
						      										 HttpServletRequest request,HttpServletResponse response) throws Exception {
		
					String viewName = (String)request.getAttribute("viewName"); // 로그인폼.do 요청시 인터셉터에서 url을 토막내어 전달한다.
					HttpSession session = request.getSession();					// HttpSession 객체를 생성하고
					session.setAttribute("action", action);  					// Session에 action값을 저장한다. action에 무엇이 저장되나?
					ModelAndView mav = new ModelAndView();						// 쿼리스트링이 없으니~ null 값 이지 않을까?
					mav.addObject("result",result);								// ModelAndView객체에 result값을 저장. result에 무엇이 저장되나?
					mav.setViewName(viewName);									// loginForm.jsp로 이동.
					return mav;													// 이동될때~ result, action 값은 null 일듯한데?
					
					@RequestParam(value= "action", required=false) => 쿼리스트링~ required = false는 쿼리스트링이 없어도 문제없이 전달되기 위해 사용. -->
					
					</c:otherwise>

				</c:choose>
			
			</td>

		</tr>
	</table>
</body>
</html>