package com.myspring.pro30.news.dao;

import java.util.List;
import java.util.Map;

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

	@Override
	public int insertNews(Map newsMap) throws DataAccessException {
		int newsNo = selectMaxNewsNo();
		newsMap.put("newsNo", newsNo);
		int result = sqlSession.insert("mapper.news.insertNews",newsMap);
		return newsNo;
	}
	
	private int selectMaxNewsNo() throws DataAccessException{
		return sqlSession.selectOne("mapper.news.selectMaxNewsNo");
	}

	
}
