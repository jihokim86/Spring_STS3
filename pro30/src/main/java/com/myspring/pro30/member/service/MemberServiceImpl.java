package com.myspring.pro30.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30.member.dao.MemberDAO;
import com.myspring.pro30.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberDAO memberDAO;

	//1.회원리스트 메소드(관리자페이지)
	@Override
	public List listMembers() throws DataAccessException {
		List membersList = null;
		membersList = memberDAO.selectAllMemberList();
		return membersList;
	}

	//2.로그인 메소드
	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		MemberVO mVO = memberDAO.loginById(memberVO);
		return mVO;
	}

	//3.회원가입 메소드
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
			int result = memberDAO.insertMember(memberVO);
		return result;
	}

	//4.회원수정 메소드
	@Override
	public void modMember(MemberVO memberVO) throws DataAccessException {
		memberDAO.updateMember(memberVO);
		
	}

	//5.회원삭제 메소드
	@Override
	public int removeMember(String id) throws DataAccessException {
		int result = memberDAO.deleteMember(id);
		return result;
	}
	
	
	
	
}
