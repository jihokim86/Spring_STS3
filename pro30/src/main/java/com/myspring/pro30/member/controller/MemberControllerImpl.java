package com.myspring.pro30.member.controller;

import java.io.InputStream;
import java.util.Enumeration;
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

// 컨트롤러에서 view(.jsp)로 이동하는 규칙(아래모두 @RequestMapping이 명시된 상황임)
//1. 메소드의 리턴타입이 String일 경우는 "리턴문자열.jsp"로 이동
//2. 메소드의 리턴타입이 void인 경우는 접근하는 url경로에 해당하는 jsp를 찾는다. 
//2.1 이 경우 기본적으로 web.xml의 DispatcherServlet의 설정 경로에 지정된 prefix,suffix에 의해 jsp를 찾는다.


// ModelAndView에 대해서(feat. redirect)
//1. ModelAndView는 Model정보(DB로부터 획득한 데이터정보)와 View정보(이동할 페이지의 jsp파일 정보)를 같이 담아서 넘기는 클래스
//2. viewResolver와 엮여있음.
//3. setViewName()에서 redirect:가 붙지 않으면 무조건 InternalResourceViewResolver가 설정한 prefix,suffix의 jsp로 이동
//4. redirect:가 붙으면 Contextpath위치애서 jsp를 찾는다.
// 참고로 브라우져에서 직접 jsp는 호출할수없다. 다음과같은 url은 불가능 (http://localhost/BordWebDay3Class05/WEB-INF/board/getBoardList.jsp)


@Controller("memberController")
@EnableAspectJAutoProxy
public class MemberControllerImpl   implements MemberController {
//	private static final Logger logger = LoggerFactory.getLogger(MemberControllerImpl.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	MemberVO memberVO ; //defualt 접근지정자가 생략된거임
	
	//1.회원리스트 컨트롤러(관리자페이지)
	@Override
	@RequestMapping(value="/member/listMembers.do" ,method = RequestMethod.GET)
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {

		String viewName = (String)request.getAttribute("viewName"); // 인터셉터에서 HTTP로 넘어온 uri를 토막낸다.
		List membersList = memberService.listMembers();				// DB에 있는 회원목록 리스트 쿼리를 실행한다.
		ModelAndView mav = new ModelAndView(viewName); 				// 
		mav.addObject("membersList", membersList);					// 실행된 쿼리문에 의해 생성된 레코드들을 저장한다.
		return mav;													// tiles.xml로 이동 /member/listMembers과 매핑
	}

		
	//2.로그인 컨트롤러
	@Override
	@RequestMapping(value="/member/login.do", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member, RedirectAttributes rAttr, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		MemberVO mVO = memberService.login(member);
		HttpSession session = request.getSession();
		
		//id,pwd 유효성
		// 일치하면 1 main.do로 이동. 불일치하면 -1을 가지고 /member/loginForm.do로 이동.
		// 불일치시 경고창이 뜨고 로그인 창을 다시 보여줘야함.
		// 일치시 main 창을 보여주면 됨.
		if(mVO != null) {
			System.out.println("로그인성공");
			session.setAttribute("memberVO", mVO);
			session.setAttribute("login", 1);
			mav.setViewName("redirect:/news/main.do");
		
		//mVO가 null값을 가지면 login값은 false 로 전달
		}else {
			System.out.println("로그인실패");
			session.setAttribute("login", -1);
			mav.setViewName("redirect:/member/loginForm.do"); 

			// redirect가 없는상황이라면 {contextPath}/WEB-INF/views//member/loginForm.do
			// redirect가 있는상황이라면 {contextPath}/member/loginForm.do
		}
		return mav;
	}
	
	//로그인폼 컨트롤러
	@RequestMapping(value="/member/loginForm.do", method=RequestMethod.GET)
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		return mav;
	}
	
	//메인폼 컨트롤러
	@RequestMapping(value="/main.do", method=RequestMethod.GET)
	public ModelAndView main(HttpServletRequest request,HttpServletResponse respons) throws Exception{
		String veiwName = (String) request.getAttribute("veiwName");
		ModelAndView mav = new ModelAndView(veiwName);
		return mav;
	}
	
	//로그아웃 컨트롤러
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response)throws Exception {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession();
		session.removeAttribute("login");
		session.removeAttribute("memberVO");
		mav.setViewName("redirect:/news/main.do");
		return mav;
	}

	
	//회원가입 컨트롤러
	@Override
	@RequestMapping(value="/member/addMember.do", method=RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		int result = memberService.addMember(member);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", member.getId());
		mav.setViewName("redirect:/member/loginForm.do");
		return mav;
	}
	
	//회원가입 폼 컨트롤러
	@RequestMapping(value="/member/memberForm.do", method=RequestMethod.GET)
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		String viewName = (String) request.getAttribute("viewName");
		mav.setViewName(viewName);
		System.out.println("viewName"+viewName);
		return mav;
	}
	
	//회원수정 폼 이동 컨트롤러
	@RequestMapping(value="/member/modForm.do", method=RequestMethod.GET)
	public ModelAndView modForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session = request.getSession();
		MemberVO mVO = (MemberVO) session.getAttribute("memberVO");
		mav.addObject("mVO", mVO);
		return mav;
	}
	
	//회원수정 컨트롤러
	@RequestMapping(value="/member/modMember.do",method=RequestMethod.POST)
	public ModelAndView modMember(@ModelAttribute("memberVO") MemberVO memberVO, HttpServletRequest request, HttpServletResponse reponse) throws Exception{
		ModelAndView mav = new ModelAndView();
		memberService.modMember(memberVO);
		HttpSession session = request.getSession();
		session.setAttribute("memberVO", memberVO);
		mav.setViewName("redirect:/main.do");
		return mav;
	}

	//회원삭제 컨트롤러
	@Override
	@RequestMapping(value="/member/removeMember.do",method=RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		int result = memberService.removeMember(id);
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
}