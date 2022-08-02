package com.myspring.pro30.board.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BoardService {
	
	public List listArticles() throws DataAccessException; 
}
