package member.controller;

import java.util.List;


import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	private MemberService memberService = new MemberService();
	
	public List<Member> selectAll() {
		
		return memberService.selectAll();
	}
	
	public int insertMember(Member member) {
		
		return memberService.insertMember(member);
	}

	public int updateMember(Member member) {
		
		return memberService.updateMember(member);
	}

	public int updatePassword(Member member) {
		
		return memberService.updatePassword(member);
	}

	public int updateEmail(Member member) {
		
		return memberService.updateEmail(member);
	}

	public int updateAddress(Member member) {
		
		return memberService.updateAddress(member);
	}

	public List<Member> selectOne(Member member) {
		
		return memberService.selectOne(member);
	
	}

	public int deleteMember(Member member) {
	
		return memberService.deleteMember(member);
	}

	public List<Member> selectName(Member member) {
		
		return memberService.selectName(member);
	}

	
	

}
