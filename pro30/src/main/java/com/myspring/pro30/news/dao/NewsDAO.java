package com.myspring.pro30.news.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface NewsDAO {
	
	//뉴스리스트 메소드 필요사항
	//1. List 타입
	//2. 매개변수는 필요 없다.
	public List selectNewsList() throws DataAccessException;
	
	//뉴스 삽입 메소드 필요사항
	//1. 파라미터를 전달해줘야한다 Map타입으로
	//2. 타입은~ int~ sqlSession은 insert함수는 int타입이다.
	public int insertNews(Map newsMap) throws DataAccessException;
}
