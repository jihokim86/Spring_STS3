package com.bookshop01.admin.goods.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.goods.service.AdminGoodsService;
import com.bookshop01.common.base.BaseController;
import com.bookshop01.goods.vo.GoodsVO;
import com.bookshop01.goods.vo.ImageFileVO;
import com.bookshop01.member.vo.MemberVO;

@Controller("adminGoodsController")
@RequestMapping(value="/admin/goods")
public class AdminGoodsControllerImpl extends BaseController  implements AdminGoodsController{
	private static final String CURR_IMAGE_REPO_PATH = "C:\\shopping\\file_repo";
	@Autowired
	AdminGoodsService adminGoodsService;
	
//---------------------상품조회--------------------------------------------------------------------------//	
	//관리자 로그인 후 header.jsp에관리자버튼 생성되고 관리자 버튼 클릭시  
	//${contextPath}/admin/goods/adminGoodsMain.do으로 경로지정 되어 있음.
	//1. 상품조회 창이 나타남.
	//2. 상품등록하기 버튼이 있음.
	
	@RequestMapping(value="/adminGoodsMain.do" ,method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView adminGoodsMain(@RequestParam Map<String, String> dateMap,
									   @RequestParam(value="search_condition", required = false) String search_condition,
									   @RequestParam(value="search",required = false) String search,
			                           HttpServletRequest request, HttpServletResponse response)  throws Exception {
		//dateMap을 전달받음
		//1. adminGoodsMain.jsp에서 자바스크립트에서 <input type="??" name="fixedSearchPeriod" value="fixeSearchPeriod" /> value값을 얻는다.
		//2. 
		
		//getViewName을 통해 url 추출(adminGoodsMain.jsp로 이동시키기 위한 작업)
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		HttpSession session=request.getSession();
		session=request.getSession();
		
		session.setAttribute("side_menu", "admin_mode"); //무엇을 위한 값 저장인가?
		
		
		String section = dateMap.get("section");
		String pageNum = dateMap.get("pageNum");
		String beginYear=dateMap.get("beginYear");
		String beginMonth=dateMap.get("beginMonth");
		String beginDay=dateMap.get("beginDay");
		
		
		if(beginYear ==null) {
			beginYear = "2018";
			beginMonth = "01";
			beginDay = "01";
		}
		
		
		
		// 조회기간 세팅
		String beginDate = beginYear +"-"+ beginMonth +"-"+beginDay;
		String endDate=calcSearchPeriod();
		
		//dataMap으로 넘겨받은 값 출력하기
		/*
		System.out.println("section="+section); //null
		System.out.println("pageNum="+pageNum);	//null
		 
		
		System.out.println("beginDate="+beginDate); // 2022-04-16
		System.out.println("endDate="+endDate);		// 2022-08-16
		
		System.out.println("search_condition="+search_condition);
		System.out.println("search="+search);*/
		
		
		//dateMap에 저장 왜 저장하니?? 어디에 사용????????
		dateMap.put("beginDate", beginDate);
		dateMap.put("endDate", endDate);
		
		
		// 쿼리문에 파라미터 전달하기 위한 hashMap객체 생성
		Map<String,Object> condMap=new HashMap<String,Object>();
		
		if(section== null) {
			section = "1";
		}
		
		if(pageNum== null) {
			pageNum = "1";
		}
		
	
	
//------ 검색 값 유효성 검사--------------------------------------------------------------//
		
		try {
			
			if(search_condition.equals("all")) {
				String search0=search;
				condMap.put("search", search0);
			}
			if(search_condition.equals("goods_id")) {
				
				//입력받은 search가 숫자로 이루어진 문자열인가?
				boolean isNumeric = StringUtils.isNumeric(search);
				
				if(isNumeric == true) {
					int search1 = Integer.parseInt(search);
					condMap.put("search", search1);
				}else {
					int search1 = 1;
					condMap.put("search", search1);
				}
			}
		
			if(search_condition.equals("goods_title")) {
				String search2 = search;
				condMap.put("search", search2);
			}
		
			if(search_condition.equals("goods_publisher")) {
				String search3 = search;
				condMap.put("search", search3);
			
			}
			} catch (Exception e) {
			
		}
		
		
		

//---------------쿼리문 매개변수 저장------------------------------------------------------------------------------------//
		condMap.put("section",section);
		condMap.put("pageNum",pageNum);
		condMap.put("beginDate",beginDate);
		condMap.put("endDate", endDate);
		condMap.put("search_condition",search_condition);
		
		
		
		
//----------------------쿼리문 실행 -----------------------------------------------------------------------------------------------//
		//listNewGoods(condMap)함수는 
		//to_char(goods_creDate,'YYYY-MM-DD')  between #{beginDate} and #{endDate} 기간에서
		//recNum between (#{section}-1)*100+ (#{pageNum}-1)*10+1 and (#{section}-1)*100+(#{pageNum})*10 페이지이지 내에서
		//글 목록~
		List<GoodsVO> newGoodsList=adminGoodsService.listNewGoods(condMap);
		mav.addObject("newGoodsList", newGoodsList); // jsp에서 뿌려줘야하기때문에 저장
		
		
//-----------------조회기간을 월/달/일로 나누어 저장하여 jsp에 전달---------------------------------------------//
		String beginDate1[]=beginDate.split("-"); 	
		String endDate2[]=endDate.split("-");
		
		mav.addObject("beginYear",beginDate1[0]);	
		mav.addObject("beginMonth",beginDate1[1]);	
		mav.addObject("beginDay",beginDate1[2]);	
		mav.addObject("endYear",endDate2[0]);		
		mav.addObject("endMonth",endDate2[1]);		
		mav.addObject("endDay",endDate2[2]);
		mav.addObject("search", search);
		mav.addObject("search_condition", search_condition);
		
		mav.addObject("section", section);			// section = 1 String타입이다.
		mav.addObject("pageNum", pageNum);			// pageNum = 1 String타입이다.
		return mav;
		
	}
	

//------------------------------------------------------------------------------------------------------------------//	
//------------------------------------------------------------------------------------------------------------------//	
//------------------------------------------------------------------------------------------------------------------//	
//------------------------상품 등록 ---------------------------------------------------------//
	
	@RequestMapping(value="/addNewGoods.do" ,method={RequestMethod.POST})
	public ResponseEntity addNewGoods(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)  throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		String imageFileName=null;
		
		Map newGoodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			newGoodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		
		List<ImageFileVO> imageFileList =upload(multipartRequest);
		if(imageFileList!= null && imageFileList.size()!=0) {
			for(ImageFileVO imageFileVO : imageFileList) {
				imageFileVO.setReg_id(reg_id);
			}
			newGoodsMap.put("imageFileList", imageFileList);
		}
		
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int goods_id = adminGoodsService.addNewGoods(newGoodsMap);
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
			message= "<script>";
			message += " alert('����ǰ�� �߰��߽��ϴ�.');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/admin/goods/addNewGoodsForm.do';";
			message +=("</script>");
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			
			message= "<script>";
			message += " alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/admin/goods/addNewGoodsForm.do';";
			message +=("</script>");
			e.printStackTrace();
		}
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}

	
	
//------------------------------------------------------------------------------------------------------------------//	
//------------------------------------------------------------------------------------------------------------------//	
//------------------------------------------------------------------------------------------------------------------//
//-------- 상품 상세보기 = 상품 수정 Form-----------------------------------------//	
	
	@RequestMapping(value="/modifyGoodsForm.do" ,method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView modifyGoodsForm(@RequestParam("goods_id") int goods_id,
			                            HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		String viewName=(String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		
		//상품번호에 대해 쿼리문 실행
	    //select g.*,to_char(g.goods_published_date,'YYYY-MM-DD') as goods_published_date, d.fileName 
	    //from t_shopping_goods g, t_goods_detail_image d
		//상품 정보 와 이미지파일 이름을 추출한다.
		
		Map goodsMap=adminGoodsService.goodsDetail(goods_id);
		mav.addObject("goodsMap",goodsMap);

// goodsMap에 저장된 값들은???
		
//1. service에서 put함
		//GoodsVO goodsVO=adminGoodsDAO.selectGoodsDetail(goods_id);
		//List imageFileList =adminGoodsDAO.selectGoodsImageFileList(goods_id);
		//goodsMap.put("goods", goodsVO);
		//goodsMap.put("imageFileList", imageFileList);
		
//2. goods에 저장된 값들
		//int goods_id;	String goods_title;	String goods_writer;int goods_price; String goods_publisher;
		//String goods_sort; int goods_sales_price; int goods_point; Date goods_published_date;
		//int goods_total_page; String goods_isbn; String goods_delivery_price; Date goods_delivery_date;
		//String goods_fileName; String goods_status; String goods_writer_intro; String goods_contents_order;
		//String goods_intro; String goods_publisher_comment; String goods_recommendation; Date goods_credate

//3. imageFileList
		//int goods_id;	int image_id; String fileName; String fileType; String reg_id; Date create;
		
		
		
		return mav;
	}
	
	
	
//---------------------수정반영 클릭시 ----------------------------------------------------------------//
/*	data : {
			goods_id:goods_id,
			attribute:attribute,	//매개변수로~ 상품분류에 해당되는 값 goods_sort값이 저장되었음
			value:value -> value=frm_mod_goods.goods_sort.value; //goods_sort태그 이름을 가진 놈의 값을 저장
		},
 *  ajax에 담겨서 넘어옴
 * */	
	@RequestMapping(value="/modifyGoodsInfo.do" ,method={RequestMethod.POST})
	public ResponseEntity modifyGoodsInfo( @RequestParam("goods_id") String goods_id,
			                     		   @RequestParam("attribute") String attribute,
			                     		   @RequestParam("value") String value,
			                     		   HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		//System.out.println("modifyGoodsInfo");
		
		//수정반영 버튼 클릭시 requestparma으로 바인딩하고~ map에 저장하여 쿼리문의 매개변수로 전달
		Map<String,String> goodsMap=new HashMap<String,String>();
		goodsMap.put("goods_id", goods_id);
		goodsMap.put(attribute, value);
		
//---------------------------쿼리문 실행 -------------------------------------------------------//
		adminGoodsService.modifyGoodsInfo(goodsMap);
		
		
		//이 아래는 잘 모르겠다.
		String message = null;
		ResponseEntity resEntity = null;
		HttpHeaders responseHeaders = new HttpHeaders();
		message  = "mod_success";
		resEntity =new ResponseEntity(message, responseHeaders, HttpStatus.OK);
		return resEntity;
	}
	
//-------------------------이미지파일 수정 반영시 ------------------------------------------------------------------//
	@RequestMapping(value="/modifyGoodsImageInfo.do" ,method={RequestMethod.POST})
	public void modifyGoodsImageInfo(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)  throws Exception {
		System.out.println("modifyGoodsImageInfo");
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		
		
		Map goodsMap = new HashMap();
		
		//form의 이미지데이터 관련 추출하기 + map에 저장하기
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement(); //form(요소)내의 태그의 name을 추출
			String value=multipartRequest.getParameter(name);// 태그name에 해당하는 값을 추출
			goodsMap.put(name,value);
		}
		
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo"); //로그인할때 저장했나본데?
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		int image_id=0;
		
		try {
			imageFileList =upload(multipartRequest);//이미지 file 이름을 추출하는 함수
			
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id")); //태그 name
					image_id = Integer.parseInt((String)goodsMap.get("image_id")); //태그 name
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setImage_id(image_id);
					imageFileVO.setReg_id(reg_id);
				}
				
//----------------쿼리문 실행-----------------------------------------------------------------------------//
			    adminGoodsService.modifyGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
		
	}
	

	@Override
	@RequestMapping(value="/addNewGoodsImage.do" ,method={RequestMethod.POST})
	public void addNewGoodsImage(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		System.out.println("addNewGoodsImage");
		multipartRequest.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");
		String imageFileName=null;
		
		Map goodsMap = new HashMap();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			goodsMap.put(name,value);
		}
		
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("memberInfo");
		String reg_id = memberVO.getMember_id();
		
		List<ImageFileVO> imageFileList=null;
		int goods_id=0;
		try {
			imageFileList =upload(multipartRequest);
			if(imageFileList!= null && imageFileList.size()!=0) {
				for(ImageFileVO imageFileVO : imageFileList) {
					goods_id = Integer.parseInt((String)goodsMap.get("goods_id"));
					imageFileVO.setGoods_id(goods_id);
					imageFileVO.setReg_id(reg_id);
				}
				
			    adminGoodsService.addNewGoodsImage(imageFileList);
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					File destDir = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id);
					FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
			}
		}catch(Exception e) {
			if(imageFileList!=null && imageFileList.size()!=0) {
				for(ImageFileVO  imageFileVO:imageFileList) {
					imageFileName = imageFileVO.getFileName();
					File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+"temp"+"\\"+imageFileName);
					srcFile.delete();
				}
			}
			e.printStackTrace();
		}
	}

	@Override
	@RequestMapping(value="/removeGoodsImage.do" ,method={RequestMethod.POST})
	public void  removeGoodsImage(@RequestParam("goods_id") int goods_id,
			                      @RequestParam("image_id") int image_id,
			                      @RequestParam("imageFileName") String imageFileName,
			                      HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		adminGoodsService.removeGoodsImage(image_id);
		try{
			File srcFile = new File(CURR_IMAGE_REPO_PATH+"\\"+goods_id+"\\"+imageFileName);
			srcFile.delete();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}




}
