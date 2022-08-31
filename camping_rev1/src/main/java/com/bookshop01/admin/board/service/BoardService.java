package com.bookshop01.admin.board.service;

import java.util.List;

import com.bookshop01.admin.board.vo.BoardVO;

public interface BoardService {
	
	public List boardList() throws Exception;
	
	public BoardVO textView(int bno) throws Exception;
	
	public int removeTextView(int bno) throws Exception;
	
	public int addTextView(BoardVO boardVO) throws Exception;
	
	public int modTextView(BoardVO boardVO) throws Exception;
}
