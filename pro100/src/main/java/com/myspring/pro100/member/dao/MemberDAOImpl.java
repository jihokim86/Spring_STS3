package com.myspring.pro100.member.dao;


import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOImpl implements MemberDAO{

	@Autowired
	private SqlSession sqlSession;

	@Override // selectList 란 xml에 있는 id에서 select문을 실행한 후 레코드(한개의 행)를 List로 변환
	public List selectAllMemberList() throws DataAccessException {
		List membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		return membersList;
	}
}




