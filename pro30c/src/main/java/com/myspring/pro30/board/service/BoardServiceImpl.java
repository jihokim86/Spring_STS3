package com.myspring.pro30.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.myspring.pro30.board.dao.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	private BoardDAO boardDAO;

	@Override
	public List listArticles() throws DataAccessException {
			List articlesList  = boardDAO.selectAllArticlesList();
		return articlesList ;
	}
	
	
}
