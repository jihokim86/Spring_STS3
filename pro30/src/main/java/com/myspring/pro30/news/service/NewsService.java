package com.myspring.pro30.news.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.news.vo.NewsVO;

public interface NewsService {
	
	//뉴스 리스트
	public List listNews() throws DataAccessException;
	
	//뉴스 삽입
	public int addNews(Map newsMap) throws DataAccessException;
	
	//상세 뉴스 보기
	public NewsVO viewNews(int newsNo) throws DataAccessException;
	
	//작성자 뉴스 선택
	public List viewNewsName(String name) throws DataAccessException;
}
