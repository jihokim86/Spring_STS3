package com.myspring.pro30b.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.myspring.pro30b.member.dao.MemberDAO;
import com.myspring.pro30b.member.vo.MemberVO;

@Service("memberService")
@Transactional(propagation = Propagation.REQUIRED)
public class MemberServiceImpl implements MemberService{
	  @Autowired 
	  private MemberDAO memberDAO;
	 


	@Override
	public List listMembers() throws DataAccessException {
		
		List memberList = memberDAO.selectAllMemberList();
		return memberList;
	}

	@Override
	public int addMember(MemberVO member) throws DataAccessException {
		int result = memberDAO.insertMember(member);
		return result;
	}

	@Override
	public int removeMember(String id) throws DataAccessException {
		return memberDAO.deleteMember(id);
	}

	@Override
	public MemberVO login(MemberVO memberVO) throws Exception {
		return memberDAO.loginById(memberVO);
	}

	
		
}
