package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;

import member.model.vo.Member;

public class MemberMenu {

	private Scanner sc = new Scanner(System.in);
	private MemberController memberController = new MemberController();

	public void mainMenu() {

		String menu = "=========== 회원 관리 프로그램  ===========\n" + "1.회원 전체조회\n" + "2.회원 아이디조회\n" + "3.회원 이름조회\n"
				+ "4.회원 가입\n" + "5.회원 정보변경\n" + "6.회원 탈퇴\n"  + "0.프로그램 끝내기\n"
				+ "-------------------------------------\n" + "선택 : ";
		

		do {
			System.out.print(menu);
			// string 처리도 좋은 선택
			String choice = sc.next();
			List<Member> list = null;
			int result = 0;
			Member member = null;
			String msg = "";
			
			
			switch (choice) {

			case "1":
					list = memberController.selectAll();
					displayMemberList(list);
				break;
					
			case "2":
					member = inputMemberId();
					list = memberController.selectOne(member);
					displayMemberList(list);
				break;

			case "3":
					member = inputMemberName();
					list = memberController.selectName(member);
					displayMemberList(list);
				break;

			case "4":
					member = inputMember();
					result = memberController.insertMember(member);
					msg = result > 0 ? "입력 성공" : "입력 실패";
					System.out.println("처리결과 : " + msg);
				break;

			case "5":
					updateMenu();
				break;

			case "6":
					member = inputMemberId();
					result = memberController.deleteMember(member);
					msg = result > 0 ? "탈퇴 성공" : "탈퇴 실패";
					System.out.println("처리결과 : " + msg);
				break;
			
			case "0":
				System.out.println("정말 끝내시겠습니까?(y/n) : ");
				if (sc.next().charAt(0) == 'y')
					return;
				break;
			default:
				System.out.println("잘못 입력하셨습니다.");

			}

		} while (true);

	}

	

	private Member inputMemberName() {
		
		System.out.print("검색할 회원의 이름을 입력하세요.");
		Member member = new Member();
		member.setMemberName(sc.next()); 
		return member;
	}



	public void updateMenu() {
		
		
		String updateMenu = "=========== 회원 정보 수정  ===========\n" + "1.회원 정보 전체 수정\n" + "2.암호 변경\n" + "3.이메일 변경\n" 
				+ "4.주소 변경\n" + "9.돌아가기\n"
				+ "-------------------------------------\n" + "선택 : ";
		
		do {
			System.out.print(updateMenu);
			String choice = sc.next();
			Member member = null;
			int result = 0;
			String msg = "";
			List<Member> list = null;
			
			switch (choice) {

			case "1":
				member = updateMember();
				result = memberController.updateMember(member);
				msg = result > 0 ? "정보 수정 성공" : "정보 수정 실패";
				System.out.println("처리 결과 : " + msg);
				list = memberController.selectOne(member);
				displayMemberList(list);
				break;
				
			case "2":
				member = updatePassword();
				result = memberController.updatePassword(member);
				msg = result > 0 ? "정보 수정 성공" : "정보 수정 실패";
				System.out.println("처리 결과 : " + msg);
				if (result == 0) {
					return;
				} else {
					list = memberController.selectOne(member);
					displayMemberList(list);
				}
				break;

			case "3":
				member = updateEmail();
				result = memberController.updateEmail(member);
				msg = result > 0 ? "정보 수정 성공" : "정보 수정 실패";
				System.out.println("처리 결과 : " + msg);
				if (result == 0) {
					return;
				} else {
					list = memberController.selectOne(member);
					displayMemberList(list);
				}
				break;
				
			case "4":
				member = updateAddress();
				result = memberController.updateAddress(member);
				msg = result > 0 ? "정보 수정 성공" : "정보 수정 실패";
				System.out.println("처리 결과 : " + msg);
				if (result == 0) {
					return;
				} else {
					list = memberController.selectOne(member);
					displayMemberList(list);
				}
				break;

			case "9":
				return;
			default:
				System.out.println("잘못 입력하셨습니다.");

			}

		} while (true);

	}

	private Member updateAddress() {
		System.out.println("정보 수정할 회원의 아이디와 수정할 주소를 입력하세요 ");
		Member member = new Member();
		
		System.out.print("정보 수정할 아이디 : ");
		member.setMemberId(sc.next());
		
		sc.nextLine();
		
		System.out.print("수정할 주소를 입력하세요 : ");
		member.setAddress(sc.nextLine());
		
		return member;
	}

	private Member updateEmail() {
		System.out.println("정보 수정할 회원의 아이디와 수정할 이메일주소를 입력하세요 ");
		Member member = new Member();
		
		System.out.print("정보 수정할 아이디 : ");
		member.setMemberId(sc.next());
		
		System.out.print("수정할 이메일을 입력하세요 : ");
		member.setEmail(sc.next());
		
		return member;
	}

	private Member updatePassword() {
		System.out.println("정보 수정할 회원의 아이디와 수정할 정보를 입력하세요 ");
		Member member = new Member();
		
		System.out.print("정보 수정할 아이디 : ");
		member.setMemberId(sc.next());
				
		System.out.print("수정할 비밀번호를 입력하세요 : ");
		member.setPassword(sc.next());
		
		return member;
	}

	private Member updateMember() {
		System.out.println("정보 수정할 회원의 아이디와 수정할 정보를 입력하세요 ");
		Member member = new Member();
		
		System.out.print("정보 수정할 아이디 : ");
		member.setMemberId(sc.next());
		//암호, 이메일, 전화번호, 주소, 취미
		System.out.print("수정할 암호 : ");
		member.setPassword(sc.next());
		
		System.out.print("수정할 이메일 : ");
		member.setEmail(sc.next());
		
		System.out.print("수정할 전화번호 : ");
		member.setPhone(sc.next());
		
		sc.nextLine();
		
		System.out.print("수정할 주소 : ");
		member.setAddress(sc.nextLine());
		
		System.out.print("수정할 취미 : ");
		member.setHobby(sc.nextLine());
		
		return member;
	}
	
	private Member inputMemberId() {

		System.out.println("검색할 아이디를 입력하세요.");
		Member member = new Member();

		System.out.print("아이디 : ");
		member.setMemberId(sc.next());

		return member;
	}
	

	private Member inputMember() {
		System.out.println("새로운 회원정보를 입력하세요.");
		Member member = new Member();
		
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		
		System.out.print("비밀번호 : ");
		member.setPassword(sc.next());
		
		System.out.print("나이 : ");
		member.setAge(sc.nextInt());
		
		System.out.print("성별(M/F) : ");
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));
		
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		
		System.out.print("전화번호(-빼고) : ");
		member.setPhone(sc.next());
		
		sc.nextLine(); //버퍼에 남은 개행문자 날리기용(next계열 - nextLine)
		
		System.out.print("주소 : ");
		member.setAddress(sc.nextLine());
		
		System.out.print("취미(,로 나열할것) : ");
		member.setHobby(sc.nextLine());
		
		return member;
		
	}

	/**
	 * n행의 회원정보 출력
	 */
	private void displayMemberList(List<Member> list) {
		if(list!=null && !list.isEmpty()) {
//			for(Member m : list)
//				System.out.println(m);
			System.out.println("==================================================");
			for(int i = 0; i < list.size(); i++) {
				System.out.println((i+1) + " : " + list.get(i));
			}
			System.out.println("==================================================");
			
		} else {
			System.out.println(">>> 조회된 회원 정보가 없습니다.");
			
		}
		
		
	}

}
