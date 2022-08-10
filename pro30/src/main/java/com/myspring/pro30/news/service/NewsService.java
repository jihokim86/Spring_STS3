package com.myspring.pro30.news.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface NewsService {
	
	//뉴스 리스트
	public List listNews() throws DataAccessException;
	
	//뉴스 삽입
	public int addNews(Map newsMap) throws DataAccessException;
}
