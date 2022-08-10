package com.myspring.pro30.news.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NewsController {
	
	//글목록 리스트 메소드
	public ModelAndView boardMain(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//뉴스 삽입 메소드
	public ModelAndView addNews(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;
}
