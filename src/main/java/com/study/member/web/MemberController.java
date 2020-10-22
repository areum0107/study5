package com.study.member.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.member.service.IMemberService;
import com.study.member.vo.MemberSearchVO;
import com.study.member.vo.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {

	
	@Autowired
	private IMemberService memberService ;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());	// 앞으로 controller에는 계속 들어가야
	
//	// @ModelAttribute를 통해 공통으로 사용되는 모델객체 (일반적으로 공통 코드목록)를 지정
//	// 요청메서드에 진입하기 전에 호출됨
//	@ModelAttribute("jobList")
//	public List<CodeVO> getJobList() {
//		System.out.println("getJobList Call");
//		List<CodeVO> jobList = codeService.getCodeListByParent("JB00");
//		return jobList;
//	}
//	
//	@ModelAttribute("hobbyList")
//	public List<CodeVO> getHobbyList() {
//		System.out.println("getHobbyList Call");
//		List<CodeVO> hobbyList = codeService.getCodeListByParent("HB00");
//		return hobbyList;
//	}
	
	@RequestMapping("/list.wow")
	public String memberList(@ModelAttribute("searchVO") MemberSearchVO memberVO, ModelMap model) throws Exception {
		logger.debug("memberVO={}",memberVO);
		List<MemberVO> members = memberService.getMemberList(memberVO);
		model.addAttribute("members", members);
		return "list";
	 	
//		return "member/memberList";
	}
	
	
}
