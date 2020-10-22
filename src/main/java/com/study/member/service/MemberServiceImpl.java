package com.study.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.member.dao.IMemberDao;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Service
public class MemberServiceImpl implements IMemberService {
	
	@Autowired
	private IMemberDao memberDao;

	@Override
	public List<MemberVO> getMemberList(MemberSearchVO searchVO) {
		// 건수를 구해서 searchVO에 설정하고, searchVO에 pageSetting()을 부르고 list 호출
		// int cnt = memberDao.getMemberCount(searchVO);
		searchVO.setTotalRowCount(24);
		searchVO.pageSetting();

		List<MemberVO> list = memberDao.getMemberList(searchVO);
		return list;
	}


}