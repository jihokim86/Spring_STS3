package com.myspring.pro30.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro30.member.service.MemberService;
import com.myspring.pro30.member.vo.MemberVO;



@Controller("memberController")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
//	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ; //defualt 접근지정자가 생략된거임
	
	
	//메인
	@RequestMapping(value = { "/", "/main.do" }, method = RequestMethod.GET)
	private ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(); //
		mav.setViewName(viewName); //jsp
		return mav;
	}
	
	//회원목록
	
	//로그인 성공시 /member/listMembers.do를 요청하므로 listMembers()함수가 실행된다.
	// 한가지 살펴봐야하는것은 listMembers()의 타입이다. controller에 정의된 함수는 모두 ModelAndView타입이다....retrun되는 값의 타입이 ModelAndView이므로...
	// ModelAndView 클래스의 역할이 jsp로 전달되는 역할을 하는데 아직 정확하게 모르겠다.
	
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String viewName = (String)request.getAttribute("viewName"); // 인터셉터에서 HTTP로 넘어온 uri를 토막낸다.
		List membersList = memberService.listMembers();				// DB에 있는 회원목록 리스트 쿼리를 실행한다.
		ModelAndView mav = new ModelAndView(viewName); 				// 
		mav.addObject("membersList", membersList);					// 실행된 쿼리문에 의해 생성된 레코드들을 저장한다.
		return mav;													// tiles.xml로 이동 /member/listMembers과 매핑
	}		

	
	//회원 추가
	// memberForm.jsp에서 등록하기버튼을 눌렀을 경우.
	// @ModelAttribute로 쿼리스트링으로 넘어온 값들을 바인딩
	@Override
	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
		
		int result = memberService.addMember(member);
		
		// 쿼리문
	    // <insert id="insertMember"  parameterType="memberVO">
		//	<![CDATA[
		//	 insert into t_member(id,pwd, name, email)
		//	 values(#{id}, #{pwd}, #{name}, #{email})
		//	]]>      
		//</insert>
		
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;
	}
	
	
	//삭제
	// 목록jsp에서 삭제하기 버튼 클릭시 ${contextPath}/member/removeMember.do?id=${member.id } 요청된다.
	// /member/removeMember.do 를 어떻게 찾지????
	@Override
	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, 
			           HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		request.setCharacterEncoding("utf-8"); 	// 이건 왜 갑자기 튀어 나온거지? 쿼리스트링때문인가???
		memberService.removeMember(id);			// id에 따른 쿼리문 실행.
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		return mav;								// member/listMembers.do 컨트롤러 이동
	}
	/*
	@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	*/
	
	//로그인
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,RedirectAttributes rAttr,
		                       HttpServletRequest request, HttpServletResponse response) throws Exception {
	//@ModelAttribute -> MemberVO클래스의 객체를 자동으로 생성하고. 빈 클래스여야하고 get set이 있어야한다.
	//HTTP로 넘어온 값들을 자동으로 바인딩한다.	http://localhost:8090/pro30/member/login.do?id=hong&pwd=1212
	//post방식으로 전달했기에 쿼리스트링은 보이지 않는다.
		
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member);		//상단에 Autowired 되어 있음. 참조변수 member에는 id와 pwd값이 들어가 있으며
	// 아래의 쿼리문이 실행된다.
	// parameterType은 아무래도 modelConfig의 경로를 의미하는것같다. 결국 MemberVO를 의미.
	//  <select id="loginById"  resultType="memberVO"   parameterType="memberVO" >
	//<![CDATA[
	// 		select * from t_member	
    //		where id=#{id} and pwd=#{pwd}		
	//	]]>
	//  </select>
	
	// 쿼리를실행하여 저장한 값이 존재하면(쿼리실행 = id, pwd 가 일치했다.)
	if(memberVO != null) {
		
	    HttpSession session = request.getSession(); //HttpSession 객체를 생성하고
	    
	    session.setAttribute("member", memberVO);	//seesion객체에 member이름으로 memberVO(id,pwd)를 저장
	    session.setAttribute("isLogOn", true);		//seesion객체에 isLogOn이름으로 ture 값을 저장
	    
	    
	    String action = (String)session.getAttribute("action"); // session에 저장된 action값을 가져오라는데!? 저장한적이없음.
	    session.removeAttribute("action");						// session에 저장된 action값을 삭제....
	    
	    if(action!= null) {										// action 값이 존재하면~
	       mav.setViewName("redirect:"+action);					// action 페이지로 이동~(action에 저장된게 .jsp인가봄)
	    }else {
	       mav.setViewName("redirect:/member/listMembers.do");	// action 값이 없으면 /member/listMembers.do로 이동.
	    }

	    
	 // 쿼리를실행하여 저장한 값이 존재하지 않으면 (쿼리실행 = id, pwd 가 불일치했다.) 
	}else {
	   rAttr.addAttribute("result","loginFailed");				//result = loginFailed를 저장하고
	   mav.setViewName("redirect:/member/loginForm.do");		//loignForm.do로 이동해라.
	}
	return mav;
	}

	//로그아웃
	// header에서 로그아웃버튼 클릭이 /member/logout.do 매핑된다.
	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");				//session 값을 삭제
		session.removeAttribute("isLogOn");				//session 값을 삭제
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do"); // 회원리스트로 이동.
		return mav;
	}	
/*
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		
		
		
		String viewName = (String)request.getAttribute("viewName");
		System.out.println(viewName);
		HttpSession session = request.getSession();
		session.setAttribute("action", action);  
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		return mav;
	}
*/	
	
	//로그인 Form
	//회원가입 Form
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
							  @RequestParam(value= "action", required=false) String action,
						       HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		String viewName = (String)request.getAttribute("viewName"); //로그인 실패시 loginForm.do로 이동하여 이쪽으로 옴.
																	// 로그인 실패 했기에 result = loginFailed 값이 저장되어 있으면 action = null값이다.
		HttpSession session = request.getSession();					//HttpSession 객체를 생성하고
		session.setAttribute("action", action);  					//Session에 action값을 저장한다. action 값은 null일듯~ 
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);								// ModelAndView객체에 result=loginFailed 을 저장
		mav.setViewName(viewName);									// tiles.xml에서 member/loginForm 매핑 -> jsp 
																	// 회원가입버튼 클릭시 tiles.xml에서 member/memberForm 매팽 => jsp
		return mav;
	}
	
	// 비 로그인상태에서 글쓰기 버튼 클릭시 
	// else{								/* isLogOn 값이 없거나 false이면~ */
	// alert("로그인 후 글쓰기가 가능합니다.")		/* 경고창 이후 */
    // location.href=loginForm+'?action=/board/articleForm.do';
	//}	/* ${contextPath}/member/loginForm.do?action=/board/articleForm.do 
	//  쿼리스트링 action=/board/articleForm.do 값을 가지고 loginForm.do컨트롤러로 이동해라. */
	// action = /board/articleForm.do 값을 sesssion에 저장하고  result는 null값을 가진채로
	// tiles.xml에서 /member/loginForm에 매핑한다.

	/* 인터셉터 사용하기 위해~ 주석처리
	 * private String getViewName(HttpServletRequest request) throws Exception {
	 * String contextPath = request.getContextPath(); 
	 * String uri = (String) request.getAttribute("javax.servlet.include.request_uri"); 
	 * if (uri == null || uri.trim().equals("")) { 
	 * 		uri = request.getRequestURI(); 
	 * }
	 * 
	 * int begin = 0; if (!((contextPath == null) || ("".equals(contextPath)))) {
	 * begin = contextPath.length(); }
	 * 
	 * int end; if (uri.indexOf(";") != -1) { end = uri.indexOf(";"); } else if
	 * (uri.indexOf("?") != -1) { end = uri.indexOf("?"); } else { end =
	 * uri.length(); }
	 * 
	 * String viewName = uri.substring(begin, end); if (viewName.indexOf(".") != -1)
	 * { viewName = viewName.substring(0, viewName.lastIndexOf(".")); } if
	 * (viewName.lastIndexOf("/") != -1) { viewName =
	 * viewName.substring(viewName.lastIndexOf("/", 1), viewName.length()); } return
	 * viewName; }
	 */


}
