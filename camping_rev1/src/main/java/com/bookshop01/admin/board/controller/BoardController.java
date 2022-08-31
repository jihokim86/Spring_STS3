package com.bookshop01.admin.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bookshop01.admin.board.vo.BoardVO;

public interface BoardController {
	
	public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView textView(@RequestParam("bno") int bno,HttpServletRequest request, HttpServletResponse response) throws Exception;
	 
	public ModelAndView removeTextView(@RequestParam("bno") int bno,HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView addTextView(@ModelAttribute("boardVO") BoardVO boarVO, HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView modTextView(@ModelAttribute("boardVO") BoardVO boarVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
}
