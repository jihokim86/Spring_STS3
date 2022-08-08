package com.myspring.pro30.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro30.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	private SqlSession sqlSession;

	//1.회원리스트 메소드(관리자페이지)
	@Override
	public List selectAllMemberList() throws DataAccessException {
		List<MemberVO> membersList = null;
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}
	
	
	//2.로그인 메소드
	@Override
	public MemberVO loginById(MemberVO memberVO) throws DataAccessException {
		MemberVO mVO = sqlSession.selectOne("mapper.member.loginById",memberVO);
		return mVO;
	}
	
	//3.회원가입 메소드 필요사항
	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
			int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}
	
	
	
	
}
