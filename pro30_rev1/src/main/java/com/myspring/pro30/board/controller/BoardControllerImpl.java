package com.myspring.pro30.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.board.service.BoardService;
import com.myspring.pro30.board.vo.ArticleVO;
import com.myspring.pro30.board.vo.ImageVO;
import com.myspring.pro30.member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO; 
	
	
	//"글 목록" 컨트롤러
	// side.jsp에서 게시판클릭시 ${contextPath}/board/listArticles.do 경로로 이동
	// 디스패치에서 매핑시도 -> /board/listArticles.do 와 일치하는 컨트롤러 찾음
	// member에 관한 컨트롤러의 함수들의 타입은 모두 ModelAndView였다.
	
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName"); // 인터셉터에서 getViewName()으로 /board/listArticles 분할
		List articlesList = boardService.listArticles();			// DB에 있는 "글목록" 레코드들을 articlesList에 저장
		ModelAndView mav = new ModelAndView(viewName);				// addObject()함수를 사용하기 위한 ModelAndView 객체 생성
		mav.addObject("articlesList", articlesList);				// 레코드를 저장. 
		return mav;													// tiles_board.xml 에서 viewName 매핑.??
								// tiles_board.xml에서 JSP로 이동시킴.
								//	<definition name="/board/listArticles" extends="baseLayout">
								//<put-attribute name="title" value="글목록창" />
	     						//<put-attribute name="body" value="/WEB-INF/views/board/listArticles.jsp" />
								//</definition>
	}
	
	 //글쓰기 -> 글 작성 후 -> post, summit 하면 /board/addNewArticle.do 요청
	/*
	@Override
	@RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
	@ResponseBody  //이건 뭐냐.
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest,HttpServletResponse response) throws Exception {
		
		//ResponseEntity (https://thalals.tistory.com/268 참고)
		//제공하는 클래스인 HttpEntity<T>를 상속받고 있으며, RestTemplate 및 @Controller 메서드에 사용하고 있다.
		//HttpEntity 클래스는 HTTP 요청(Request) 또는 응답(Response)에 해당하는 HttpHeader와 HttpBody를 포함하는 클래스이다.
		//HttpEntity 클래스를 상속받아 구현한 클래스가 RequestEntity, ResponseEntity 클래스이다. 
		//따라서 ResponseEntity는 사용자의 HttpRequest에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody를 포함한다. 
		// 결론은 ResponseEntity 클래스를 사용하면, 결과값! 상태코드! 헤더값!을 모두 프론트에 넘겨줄 수 있고, 에러코드 또한 섬세하게 설정해서 보내줄 수 있다는 장점이 있다!
		
		multipartRequest.setCharacterEncoding("utf-8"); //파일 업로드
		
		Map<String,Object> articleMap = new HashMap<String, Object>();
		
		Enumeration enu=multipartRequest.getParameterNames(); // getParameterNames : 요청파라미터 이름을 Enumeration 객체 타입으로 반환
		//  getFileNames를 통해 구하고자 하는 파일 이름들을 구하면 Iterator 형태로 넘어온 file들의 이름들이 리턴된다
		// 	Enumeration는 구버젼. Iteration를 사용을 권장
		//	hasMoreElements() : 읽어올 요소가 남아있는지 확인. 있으면 true, 없으면 false. Iterator의 hasNext()와 같음
		//	nextElement() : 다음 요소를 읽어 옴. Iterator의 next()와 같음
		
		while(enu.hasMoreElements()){ // 저장된 파일이름이 있는지 확인
			 // Iterator 형태로 추출된 파일들의 이름을 키값으로 하여, while문을 돌면서 넘어온 파일들의 정보를 추출한다.
			
			String name=(String)enu.nextElement(); //요소를 읽어서 파일이름을 저장 key 값
			String value=multipartRequest.getParameter(name); //getParameter:요청파라미터 키(name)값에 대한 값(value)을 저장
			articleMap.put(name,value);	// Map에 put함.
		}
		
		String imageFileName= upload(multipartRequest);
		//upload함수를 호출하게 되면 이미지를 지정 경로로 업로드하고~ 파일의 이름을 리턴한다.
		
		HttpSession session = multipartRequest.getSession(); //Session객체 생성?? multipartReques.getSession??????
		
		MemberVO memberVO = (MemberVO) session.getAttribute("member"); //로그인시 id,pwd를 @ModelAttribute를 통해 바인딩하여 ~ session에 저장해두었기에~
																		// get 해서 member에 접근하여 id를 가져올수있다.
		String id = memberVO.getId(); // session에 저장된 member객체내에 id를 가져오기 위해 MemberVO객체를 생성하여 getId()한다.
		
		articleMap.put("parentNO", 0); // key : parentNO , value:0
		articleMap.put("id", id);	// key : id , value:로그인한 id(session에 저장된)
		articleMap.put("imageFileName", imageFileName); // key : imageFileName , value:업로드한 이미지파일 이름
		
		String message; //사용처가???????
		ResponseEntity resEnt=null; // 객체를 생성함..어떻게 사용하는지는 살펴보자.
		HttpHeaders responseHeaders = new HttpHeaders(); //HttpHeaders 객체생성. 
		//http header에는 (요청/응답)에 대한 요구사항이
		//http body에는 그 내용이 적혀있고,
		
		responseHeaders.add("Content-Type", "text/html; charset=utf-8"); // jsp로 보낼때 정보를 header에 저장해둔다고 보면 될것같다.
		//왜 저장하는걸까?가 중요하겠지.
		
		try {
			int articleNO = boardService.addNewArticle(articleMap); // max(articleNO)+1 함수를 진행하고 insert쿼리문 진행 
			
			if(imageFileName!=null && imageFileName.length()!=0) { // 이미지이름이 있으면~~
				
				File srcFile = new File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName); // 
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir,true); // srcFile 을 destDir로 변경(경로를 변경해주는 함수) 왜 변경하노??
			}
	
			message = "<script>";
			message += " alert('새글을 추가했습니다.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
			
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		    
		}catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = " <script>";
			message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	
	*/
	//글 상세창 보기 ( 목록에서 클릭)
	//"${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}"
	// articleNO가....답글달면 변하나?
	/*
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String viewName = (String)request.getAttribute("viewName");
		articleVO=boardService.viewArticle(articleNO);
		ModelAndView mav = new ModelAndView(viewName); //매개변수로 viewName을하면 setviewName()안해도 됨
		//mav.setViewName(viewName);					
		mav.addObject("article", articleVO);
		return mav;										//tiles.xml에서 board/viewArticle 매핑
		
		//<select id="selectArticle" resultType="articleVO" parameterType="int">
		//<![CDATA[ SELECT * from t_board where articleNO = #{articleNO} ]]> </select>
		 
	}*/
	
	
	//다중 이미지 보여주기
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO, //articleNO를 get하고 쿼리문에 이용하여 해당 글번호에 따른 레코드들을 가져올수있다.
			  HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String viewName = (String)request.getAttribute("viewName"); // url경로
		
		Map articleMap=boardService.viewArticle(articleNO);         // DB 레코드를 map에 저장. 중요한것은 map으로 반환받았다는거?
		
		ModelAndView mav = new ModelAndView();						//왜 map으로 받았으며? 어떻게 받았나?
		mav.setViewName(viewName);									//map vs list로 했을 경우 jsp에서 사용하는 방법은 다르나??? ->  vo와 list를 map에 저장함
		mav.addObject("articleMap", articleMap);
		return mav;
	}
   //해당 글목록 클릭시 NO를 가지고 넘어온다~
	//${contextPath}/board/viewArticle.do?articleNO=${article.articleNO}

	/*
  //한 개 이미지 수정 기능
  @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
    HttpServletResponse response) throws Exception{
    multipartRequest.setCharacterEncoding("utf-8");
	Map<String,Object> articleMap = new HashMap<String, Object>();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
	}
	
	String imageFileName= upload(multipartRequest);// 타입 수정??
	articleMap.put("imageFileName", imageFileName);
	
	String articleNO=(String)articleMap.get("articleNO");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    try {
       boardService.modArticle(articleMap);
       if(imageFileName!=null && imageFileName.length()!=0) {
         File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
         File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
         FileUtils.moveFileToDirectory(srcFile, destDir, true);
         
         String originalFileName = (String)articleMap.get("originalFileName");
         File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
         oldFile.delete();
       }	
       message = "<script>";
	   message += " alert('글을 수정했습니다.');";
	   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	   message +=" </script>";
       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }catch(Exception e) {
      File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
      srcFile.delete();
      message = "<script>";
	  message += " alert('오류가 발생했습니다.다시 수정해주세요');";
	  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	  message +=" </script>";
      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }
    return resEnt;
  }
  */
	
  @Override
  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setContentType("text/html; charset=UTF-8");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		boardService.removeArticle(articleNO);
		File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
		FileUtils.deleteDirectory(destDir);
		
		message = "<script>";
		message += " alert('글을 삭제했습니다.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	       
	}catch(Exception e) {
		message = "<script>";
		message += " alert('작업중 오류가 발생했습니다.다시 시도해 주세요.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    e.printStackTrace();
	}
	return resEnt;
  }  
  
  
  
  
  
  
  //글목록 -> 글쓰기 -> 제목,내용,이미지업로드 -> 글쓰기(sumit) 버튼 누를시.post방식으로 다중데이터를가지고 넘어옴
  //다중 이미지 글 추가하기
  @Override
  @RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
	
	//ResponseEntity : http클래스를 상속받고있는 클래스로~ http의 헤더와 바디 값을 사용한다고만 생각하자.
	  
	multipartRequest.setCharacterEncoding("utf-8");
	
	String imageFileName=null; //사용처? 이미지파일의 이름을 저장하기 위한 변수
	
	Map articleMap = new HashMap(); // map 객체 생성. 사용처?
	
	Enumeration enu=multipartRequest.getParameterNames(); // Iterator의 구형버젼. 기능은 똑같음.
	//폼에서 전송된 파라미터들의 이름을 enu에 저장. 파라미터의 이름이라면~ 태그의 name을 말하는것같다.(file타입은 제외)
	
	while(enu.hasMoreElements()){ // hasnext()와 같은 기능으로 객체내부의 값들이 존재하는지 판별(true, false)
		
		String name=(String)enu.nextElement(); // 요소값(파라미터의 이름, 태그의 name)을 읽어서 저장
		String value=multipartRequest.getParameter(name); //파라미터의 이름을 매개변수로 해당 값을 읽는다.
		articleMap.put(name,value);	// map에 저장해줌
		//글쓰기 창에 입력된 제목/내용을 articleMap에 저장하게 된다.  
		//insert에 필요한 매개변수가 필요하기에 map에 저장해서 전달함~
		//int articleNO = boardService.addNewArticle(articleMap);
	}
	
	System.out.println("articleNO.map="+articleMap.get("articleNO")); //articleNO=null 흠????
	//로그인 시 세션에 저장된 회원 정보에서 글쓴이 아이디를 얻어와서 Map에 저장합니다.
	//왜 회원정보를 왜 저장할까??
	
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member"); //로그인 할때   session에 member객체가 저장했었다.
																	//기억으로는 id,pwd를 저장했던거같다.
		
		String id = memberVO.getId(); // id를 추출하여 다시 저장/ 어디에 사용하려고??
		articleMap.put("id",id);		// articleMapdp에 저장.
		articleMap.put("parentNO", 0);	// parentNO를 0으로 저장..흠..왜할까. 글쓰기 눌렀을 경우 부모글로 설정???
	
		List<String> fileList =upload(multipartRequest);
		// multipartRequest에서 넘어온 값들은 무엇이 있을까? 모든것~
		// upload()실행시 리턴 값은 파일명들이 저장된 fileList 이다.
		
		List<ImageVO> imageFileList = new ArrayList<ImageVO>();
		
		if(fileList!= null && fileList.size()!=0) { //fileList가 존재하면~
			
			for(String fileName : fileList) { //fileList객체의 요소를 fileName변수에 저장한다.
				
				ImageVO imageVO = new ImageVO(); 
				
				imageVO.setImageFileName(fileName); // 이 행위가 이해가 안되네~~ vo에 저장해야
				
				imageFileList.add(imageVO); // 이 행위가 이해가 안되네.
			}
			articleMap.put("imageFileList", imageFileList);
		}
		//왜 위와 같은 for문이 필요할까??
		//리스트에 들어있는 값들은 이미지파일명들이다. 이미지파일명들을 set해야하는 이유는 멀까?  jsp로 보내줘야한다.
		// 어떻게 보내줄껀데? 폼에서 넘어온 정보는 모두 map에 저장했기에 이미지명도 map에 저장하면좋을텐데~
		// 어떻게 보내???
		
		String message;
		ResponseEntity resEnt=null; // http로 보내기위한?? 무튼 뭐 그런거....
		
		HttpHeaders responseHeaders = new HttpHeaders(); // http헤더만들기~ 내용저장해서 넘기기위한? 이미지할때만 사용하나?
		
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		
		try {
			/////////////////////////////////////////////////////////////////////////////
			int articleNO = boardService.addNewArticle(articleMap);  //쿼리문 수행
			///////////////////////////////////////////////////////////////////////////////
			//int 로 왜 저장했을까?
			System.out.println("articleNO="+articleNO);
			//articleNO=25?????
			// 호출 함수의 리턴값이 articleMap.put("articleNO", articleNO);
			//articleNO으로 되어 있다. max(articleNO)+1 을 거쳐서 온다~
			
			if(imageFileList!=null && imageFileList.size()!=0) { //imageFileList.add(imageVO); 넣어주었기에~ 
				
				for(ImageVO  imageVO:imageFileList) { //변수타입이 문제네~
					
					imageFileName = imageVO.getImageFileName();
					
					File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
					System.out.println("imageFileName"+imageFileName);
					// imageFileName=duke.png
					// 디렉토리 경로
					//ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
					File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
					
					//destDir.mkdirs();
					FileUtils.moveFileToDirectory(srcFile, destDir,true); //경로 변경
				}
			}
		    
		message = "<script>";
		message += " alert('새글을 추가했습니다.');";
		message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
		message +=" </script>";
		resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED); 
		//이거 없으면 글쓰기(submit)누르면 오류는 발생하지 않으나 경고창도 안뜨고 페이지이동이 되지 않는다.
	    
		 
	}catch(Exception e) {
		
		if(imageFileList!=null && imageFileList.size()!=0) {
		  for(ImageVO  imageVO:imageFileList) {
		  	imageFileName = imageVO.getImageFileName();
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
		 	srcFile.delete();
		  }
		}

		
		message = " <script>";
		message +=" alert('오류가 발생했습니다. 다시 시도해 주세요');');";
		message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
		message +=" </script>";
		resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		e.printStackTrace();
	}
	return resEnt;
  }
	


	//로그인 상태에서 글쓰기버튼 클릭시 /board/articleForm.do에 매핑한다.

	@RequestMapping(value = "/board/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String viewName = (String)request.getAttribute("viewName"); //인터셉터에서 Url를 분해한다.
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);									// url를 저장하고
		return mav;													// tiles.xml에 viewName을 매핑하여 jsp로 이동시킨다.
	}

	
	
	
	//글쓰기에서 글 작성 + 이미지 추가 해서 글쓰기(sumit)누르면 -> /board/addNewArticle.do 컨트롤러로 이동하게 되는데
	// 해당 컨트롤러에서 String imageFileName= upload(multipartRequest); 아래 함수를 호출하는 코드가 작성되어 있다.
/*	
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		
		String imageFileName= null;
		Iterator<String> fileNames = multipartRequest.getFileNames(); // 업로드된 파일의 이름 목록을 제공하는 iterator를 구함
		
			//JSP에서 넘어온 <input> 태그의 name을 확실히 알고 있다면 getFile(), 확실히 알수 없다면 getFileNames() 를 사용합니다
			//파일 업로드를 할때 파일을 한개만 올릴수도 있지만.. 여러개를 올리는 경우도 있으므로 getFileNames()를 많이 사용하는 편입니다
			//mRequest.getFileNames()를 사용해서 몇개가 넘어왔든 Iterator 형태로 name을 다 받아주고 그 
			//name으로 mRequest.getFile()을 사용해서 실제 파일 객체를 뽑습니다
		
		    //	Enumeration는 구버젼. Iteration를 사용을 권장
			//	hasMoreElements() : 읽어올 요소가 남아있는지 확인. 있으면 true, 없으면 false. Iterator의 hasNext()와 같음
			//	nextElement() : 다음 요소를 읽어 옴. Iterator의 next()와 같음
		
		while(fileNames.hasNext()){ // 요소가 없을때까지 실행
			
			String fileName = fileNames.next(); //해당 요소를 저장
			
			MultipartFile mFile = multipartRequest.getFile(fileName); // 파라미터 이름이 name인 업로드 파일 정보를 구함.
			//MultipartFile 객체에서는 업로드한 파일명과 파일사이즈를 뽑아내고 DB에 저장해주면 됩니다
			//업로드한 파일명은 mFile.getOriginalFilename(), 파일 사이즈는 mFile.getSize()를 사용
			
			imageFileName=mFile.getOriginalFilename();	// 업로드한 파일 명 ,.. 함수의 리턴 값~ 쓸때가 있나봐.
			
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName); // 이미지(업로드)저장 경로
			//private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";\\temp\\
			
			if(mFile.getSize()!=0){ // 업로드한 파일 사이즈를 get해서 그 값이 0이 아니면~~
				
				if(!file.exists()){ //경로상에 파일이 존재하지 않을 경우
					
					file.getParentFile().mkdirs();  //경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+imageFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
				}			                 //C:\\board\\article_image";
			}
			
		}
		return imageFileName; // 함수를 호출하면 이미지를 업로드하고~ 이미지파일 이름을 리턴함.
	}
	*/
	
	//다중 이미지 업로드하기
	private List<String> upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		
		List<String> fileList= new ArrayList<String>(); // 여러 이미지를 저장하기 위해 List객체 생성
		
		Iterator<String> fileNames = multipartRequest.getFileNames(); // 폼 요소중 input태그에 file속성으로 지정된 태그의 name을 가져옴
		
		while(fileNames.hasNext()){	// 객체에 요소가 존재하느냐? true , false
			
			String fileName = fileNames.next(); // 각각의 태크name을 저장한다.
			
			MultipartFile mFile = multipartRequest.getFile(fileName); // 태그 name을 매개변수로 서버상에 업로드된 파일을 객체에 저장.
			
			String originalFileName=mFile.getOriginalFilename(); // 사용자가 직접 지정한 파일명을 저장 = 이미지 파일명을 의미하는듯함.
			System.out.println("originalFileName="+originalFileName);
			//originalFileName=duke_swing.gif
			
			fileList.add(originalFileName); // 파일명을  리스트에 저장. 어디에 사용하나??
			
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName); // 이미지 저장 경로지정?????
			
			if(mFile.getSize()!=0){ //File Null Check
				
				if(!file.exists()){ //경로상에 파일이 존재하지 않을 경우
					
					file.getParentFile().mkdirs();  //경로에 해당하는 디렉토리들을 생성
					
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //임시로 저장된 multipartFile을 실제 파일로 전송
				}
			}
		}
		return fileList; // 파일명이 저장된 리스트를 리턴
	}
	
}
