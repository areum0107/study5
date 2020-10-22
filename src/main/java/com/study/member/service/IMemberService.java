package com.study.member.service;

import java.util.List;

import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

public interface IMemberService {
	
//	public void registMember(MemberVO member) throws BizDuplicateKeyException ;
//	public void modifyMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException ;
//	public void removeMember(MemberVO member) throws BizNotEffectedException, BizNotFoundException ;
//	
//	public MemberVO getMember(String memId) throws BizNotFoundException  ;
	public List<MemberVO> getMemberList(MemberSearchVO searchVO);
	
//	public void checkedMemberDelete(String[] checkedMemIds);	// 체크된 멤버 한번에 삭제
}
