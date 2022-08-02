package com.myspring.pro30b.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30b.member.vo.MemberVO;

public interface MemberDAO {
	
	//List에 저장하기 위함
	public List selectAllMemberList() throws DataAccessException;
	
	//public void ~~ 를 사용 하지않는 이유는 memberVO에서 받은 값이 있을수도 있기 떄문에 
	public int insertMember(MemberVO memberVO) throws DataAccessException;
	
	
	public int deleteMember(String id) throws DataAccessException;
	
	
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException;	

	public int updateMember(MemberVO memberVO) throws DataAccessException;
}
