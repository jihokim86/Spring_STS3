package com.myspring.pro30.news.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.myspring.pro30.news.service.NewsService;
import com.myspring.pro30.news.vo.NewsVO;

@Controller("boardController")
public class NewsControllerImpl implements NewsController {

	@Autowired
	private NewsService newsService;
	
	
	//뉴스 목록 메인 컨트롤러
	
	@Override
	@RequestMapping(value="/news/main.do", method=RequestMethod.GET)
	public ModelAndView boardMain(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName); 
		List listNews = newsService.listNews();
		mav.addObject("listNews", listNews); 
		
		return mav;
	}
	
	//쿼리스트링 숨기기 위한 컨트롤러
	@RequestMapping(value="/getMainView.do")
	public String getMainView() {
		return "redirect:/news/main.do";
	}

	//뉴스 글쓰기 폼 컨트롤러
	@RequestMapping(value="/news/addNewsForm.do", method=RequestMethod.GET)
	public ModelAndView addNewsForm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		return mav;
	}
	
	//뉴스 삽입 컨트롤러
	@Override
	@RequestMapping(value="/news/addNews.do", method=RequestMethod.POST)
	public ModelAndView addNews(MultipartHttpServletRequest multipartRequest, HttpServletResponse response)
			throws Exception {
		
		multipartRequest.setCharacterEncoding("utf-8");
		
		//multipart를 통해 전송되어진 값들을 출력하여~ Map 저장해야한다!
		// key는 태그의 name을 추출하고 name을 통해 값을 추출한다.
		// 여기에서 사용되는 Map은 최종적으로 쿼리문의 parametertype으로 전달해줘야함
		// Map에 담겨질 데이터들은 #{newsNO},#{newsTitle},#{newsContent},#{imageFileName} 이다.
		
		//1. 글쓰기 폼에서 입력 데이터들을 추출하여 Map 에 저장하기(newsNO,newsTitle,newsContent,imageFileName)
		//newsNO는 마지막 NO+1 을 해줘야 하므로 Max(newsNO)+1 쿼리문을 리턴받는 함수(DAO에서 함수생성하고  map에 put)
		Map newsMap = new HashMap();
		
		Enumeration newsKey = multipartRequest.getParameterNames(); //태그의 name을 추출, Iterator와 같은 기능
		while(newsKey.hasMoreElements()) { // 읽어올 요소가 남아 있을경우 true. Iterator의 hasNext()와 같은기능
			String name = (String)newsKey.nextElement(); //요소를 name에 저장
			String value = multipartRequest.getParameter(name);//name를 매개변수로 value값을 추출하여 저장
			newsMap.put(name, value);
		}
		

	
		
		//2. 추가한 이미지의 (imageFileName)을 추출하여 데이터를 map저장하여 쿼리문에 전달해줘야한다.
		// upload메소드를 사용하여 imageFileName를 추출한다.
		
		List<String> fileList = upload(multipartRequest);
		
		
		System.out.println("add_name="+newsMap.get("name"));
		System.out.println("add_newsNo="+newsMap.get("newsNo"));
		System.out.println("add_newsTitle="+newsMap.get("newsTitle"));
		System.out.println("add_newsContent="+newsMap.get("newsContent"));
		System.out.println("add_fileList="+newsMap.get("fileList"));
		
		//이미지 파일 경로 설정
		String imageFileName=null;
		for(String FileName : fileList) {
			NewsVO nVO = new NewsVO();
			nVO.setImageFileName(FileName);
			imageFileName = nVO.getImageFileName();
		}
		
		System.out.println("imageFileName:"+imageFileName);
		
		newsMap.put("imageFileName", imageFileName); 
		
		//insert쿼리 실행
		int newsNo = newsService.addNews(newsMap);
		
		File srcFile = new File("C:\\article\\news_image\\"+"temp"+"\\"+imageFileName);
		
		// imageFileName=duke.png
		// 디렉토리 경로
		//ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
		
		File destDir = new File("C:\\article\\news_image\\"+newsNo);
		
		//destDir.mkdirs();
		FileUtils.moveFileToDirectory(srcFile, destDir,true); //경로 변경
		
		
		ModelAndView mav = new ModelAndView();
		mav.addAllObjects(newsMap);
		mav.setViewName("redirect:/getMainView.do");
		return mav;
	}

	
	//이미지파일들의 이름 추출하는 메소드
	private List upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		
		//파일 이름들을 저장하기 위해 List 객체 생성
		List fileList = new ArrayList();
		
		// 폼의 태그 중 file타입의 태그의 name을 추출하여 저장.
		Iterator<String> fileNames = multipartRequest.getFileNames(); 			//<input type="file" name="imageFileName" onchange="readURL(this)"/>//
		
		while(fileNames.hasNext()) {
			String fileName = fileNames.next(); 				//각 요소의 값(name)을 저장
			MultipartFile mFile = multipartRequest.getFile(fileName);	//추출된 태그의 이름을 가지고 파일을 객체에 저장해야한다.
			String originalFilename= mFile.getOriginalFilename();		//mFile객체에 저장된 파일의 실체이름을 추출
			fileList.add(originalFilename); 							//파일이름들을 리스트에 저장
			
			
			//객체에 저장된 파일을 실제 저장할 경로를 지정
			File file = new File("C:\\article\\news_image\\"+"temp"+"\\"+fileName);
			
			if(mFile.getSize()!=0){ //File Null Check
				if(!file.exists()){ //경로상에 파일이 존재하지 않을 경우
					file.getParentFile().mkdirs();  //경로에 해당하는 디렉토리들을 생성
					mFile.transferTo(new File("C:\\article\\news_image\\"+"temp"+"\\"+originalFilename)); //임시로 저장된 multipartFile을 실제 파일로 전송
				}
			}
		}
		return fileList;
	}

	//뉴스 상세보기 컨트롤러
	@Override
	@RequestMapping(value="/news/viewNews.do", method=RequestMethod.GET)
	public ModelAndView viewNews(@RequestParam("newsNo") int newsNo, @RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String) request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView(viewName);
		NewsVO listNews = newsService.viewNews(newsNo);
		List listNewsName = newsService.viewNewsName(name);
		mav.addObject("listNews", listNews);
		mav.addObject("listNewsName",listNewsName);
		return mav;
	}
	
}
	
