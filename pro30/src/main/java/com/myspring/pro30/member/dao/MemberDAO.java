package com.myspring.pro30.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.myspring.pro30.member.vo.MemberVO;

public interface MemberDAO {
	
	 //회원리스트 메소드(관리자페이지)
	 public List selectAllMemberList() throws DataAccessException;
	 
	 //로그인 메소드 필요사항
	 //1. 쿼리문에 따라 메소드가 정해짐. 쿼리문부터 작성 필요
	 //2. 입력된 id,pwd를 전달해야하므로 매개변수를 memberVO를 사용하고 파라미터타입으로 쿼리문에 전달
	 //3. 입력된 id,pwd는 loginForm.jsp -> login.do에서 @ModelAttribute를 이용해 memberVO에 저장한다.
	 public MemberVO loginById(MemberVO memberVO) throws DataAccessException;
	 
	 //회원가입 메소드 필요사항
	 //1. 쿼리문 insert into t_member values(#{id},#{pwd},#{email},...)
	 //2. 입력 파라미터들을 넘겨줘야한다~ MemberVO memberVO 형식으로
	 //3. 리턴 값은?? => sqlsession의 insert는 int 타입으로 정해져 있음.
	 public int insertMember(MemberVO memberVO) throws DataAccessException;
}
