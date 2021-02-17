package member.controller;

import java.util.List;

import member.model.exception.MemberException;
import member.model.service.MemberService;
import member.model.vo.Member;
import member.view.MemberMenu;

public class MemberController {
	
	private MemberService memberService = new MemberService();
	
	public List<Member> selectAll() {
		
		List<Member> list = null;
		try {
			list = memberService.selectAll();
		} catch (MemberException e) {
			//서버로깅
			//관리자 이메일 알림
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	}
	
	public int insertMember(Member member) {
		
		int result = 0;
		try {
			result = memberService.insertMember(member);
		}catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMember(Member member) {
		
		int result = 0;
		try {
			result = memberService.updateMember(member);
		} catch (MemberException e){
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		
		return result;
	}


	public List<Member> selectOne(Member member) {
		
		List<Member> list = null;
		
		try {
			list = memberService.selectOne(member);
		}catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return list;
	
	}

	public int deleteMember(Member member) {
	
		int result = 0;

		try {
			result = memberService.deleteMember(member);

		} catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
		}
		return result;
	}

	public List<Member> selectDeletedMember() {

		List<Member> list = null;

		try {
			list = memberService.selectDeletedMember();

		} catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");

		}
		return list;
	}

	public List<Member> selectName(Member member) {
		
		List<Member> list = null;
		
		try {
			list = memberService.selectName(member);
		} catch (MemberException e) {
			new MemberMenu().displayError(e.getMessage() + " : 관리자에게 문의하세요.");
			
		}
		
		return list;
	}


}
