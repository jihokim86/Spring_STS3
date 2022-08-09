package com.myspring.pro30.news.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository("newsDAO")
public class NewsDAOImpl implements NewsDAO{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectNewsList() throws DataAccessException {
		List newsList = sqlSession.selectList("mapper.news.selectNewsList");
		return newsList;
	}

}
