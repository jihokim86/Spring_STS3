package com.myspring.pro30.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.web.servlet.ModelAndView;

public interface BoardController {
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) 
			throws Exception;
}
