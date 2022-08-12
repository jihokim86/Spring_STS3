package com.myspring.pro30.news.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

public interface NewsController {
	
	//글목록 리스트 메소드
	public ModelAndView boardMain(HttpServletRequest request, HttpServletResponse response)  throws Exception;
	
	//뉴스 삽입 메소드
	public ModelAndView addNews(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

	//뉴스 상세 보기
	public ModelAndView viewNews(@RequestParam("newsNo") int newsNo, @RequestParam("name") String name,HttpServletRequest request, HttpServletResponse response) throws Exception;

}
