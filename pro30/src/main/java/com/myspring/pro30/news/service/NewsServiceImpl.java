package com.myspring.pro30.news.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.myspring.pro30.news.dao.NewsDAO;

@Service("newsService")
public class NewsServiceImpl implements NewsService{

	@Autowired
	private NewsDAO newsDAO;
	
	@Override
	public List listNews() throws DataAccessException {
		List listNews = newsDAO.selectNewsList();
		return listNews;
	}

	@Override
	public int addNews(Map newsMap) throws DataAccessException {
		int result = newsDAO.insertNews(newsMap);
		return result;
	}
	
	

}
