package com.myspring.pro30.news.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.news.vo.NewsVO;

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
	

	@Override
	public NewsVO selectViewNews(int newsNo) throws DataAccessException {
		NewsVO viewNews = sqlSession.selectOne("mapper.news.selectViewNews",newsNo);
		return viewNews;
	}

	@Override
	public List selectNewsName(String name) throws DataAccessException {
		List selectNewsName = sqlSession.selectList("mapper.news.selectNewsName", name);
		return selectNewsName;
	}

	
}
