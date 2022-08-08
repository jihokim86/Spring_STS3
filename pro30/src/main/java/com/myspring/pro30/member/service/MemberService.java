package com.myspring.pro30.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberService {
	
	//1.회원리스트 메소드(관리자페이지)
	public List listMembers() throws DataAccessException;
	
	//2.로그인 메소드
	public MemberVO login(MemberVO memberVO) throws Exception;
	
	//회원가입 메소드
	public int addMember(MemberVO memberVO) throws DataAccessException;
}
