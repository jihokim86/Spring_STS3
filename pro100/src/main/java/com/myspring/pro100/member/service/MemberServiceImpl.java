package com.myspring.pro100.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.myspring.pro100.member.dao.MemberDAO;

@Service
public class MemberServiceImpl implements MemberService{

	
	@Autowired
	private MemberDAO memberDAO;
	
	@Override
	public List listMembers() throws DataAccessException {
		List memberList = memberDAO.selectAllMemberList();
		return memberList;
	}
}





