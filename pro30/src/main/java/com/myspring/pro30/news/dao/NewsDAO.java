package com.myspring.pro30.news.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface NewsDAO {
	
	//뉴스리스트 메소드 필요사항
	//1. List 타입
	//2. 매개변수는 필요 없다.
	public List selectNewsList() throws DataAccessException;
}
