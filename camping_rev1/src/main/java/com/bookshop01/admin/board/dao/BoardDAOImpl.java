package com.bookshop01.admin.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.bookshop01.admin.board.vo.BoardVO;


@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO{
	
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List selectBoardList() throws DataAccessException {
		List selectBoardList = sqlSession.selectList("mapper.admin.board.boardList");
		return selectBoardList;
	}

	@Override
	public BoardVO textView(int bno) throws DataAccessException {
		BoardVO textView = (BoardVO)sqlSession.selectOne("mapper.admin.board.textview",bno);
		return textView;
	}

	@Override
	public int deleteTextView(int bno) throws DataAccessException {
		int result = sqlSession.delete("mapper.admin.board.deleteTextView", bno);
		return result;
	}

	@Override
	public int inerstTextView(BoardVO boardVO) throws DataAccessException {
		int result = sqlSession.insert("mapper.admin.board.insertTextView",boardVO);
		return result;
	}

	@Override
	public int updateTextView(BoardVO boardVO) throws DataAccessException {
		int result = sqlSession.update("mapper.admin.board.updateTextView", boardVO);
		return result;
	}

}
