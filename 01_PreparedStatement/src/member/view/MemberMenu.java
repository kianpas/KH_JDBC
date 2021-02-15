package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private MemberController memberController = new MemberController();
	
	Scanner sc = new Scanner(System.in);
	
	public void mainMenu() {
		
		String menu = "=========== 회원 관리 프로그램  ===========\n"
				+ "1.회원 전체조회\n"
				+ "2.회원 아이디조회\n"
				+ "3.회원 이름조회\n" 
				+ "4.회원 가입\n" 
				+ "5.회원 정보변경\n" 
				+ "6.회원 탈퇴\n"
				+ "7.회원특정나이 이상검색\n" 
				+ "0.프로그램 끝내기\n"
				+ "-------------------------------------\n"
				+ "선택 : ";
		
		while(true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			Member member = null;
			int result = 0;
			String msg = null;
			List<Member> list = null;
			String memberId = null;
			String memberName = null;
			int age = 0;
			int memberAge = 0;
			
			switch(choice) {
				case 1: 
					list = memberController.selectAll();
					displayMemberList(list);
					break;
				case 2: 
					memberId = inputMemberId();
					member = memberController.selectOne(memberId);
					displayMember(member);
					break;
				case 3: 
					//이름조회
					memberName = inputMemberName();
					member = memberController.selectName(memberName);
					displayMember(member);
					break;
				case 4: 
					//1.신규회원정보 입력 -> Member객체
					member = inputMember();
					System.out.println(">>>신규회원 확인 : " + member);
					//2.controller에 회원가입 요청(메소드호출) -> int리턴(처리된 행의 개수)
					result = memberController.insertMember(member);
					//3.int에 따른 분기처리
					msg = result > 0 ? "회원 가입 성공!" : "회원 가입 실패!";
					displayMsg(msg);
					break;
				case 5: 
					//정보변경
					member = inputchangeMemberId();
					result = memberController.changeInfo(member);
					msg = result > 0 ? "회원 정보 수정 성공" : "회원 정보 수정 실패";
					displayMsg(msg);
					break;
				case 6: 
					memberId = inputdeleteMemberId();
					result = memberController.deleteMember(memberId);
					msg = result > 0 ? "회원 정보 삭제" : "회원 정보 삭제 실패";
					displayMsg(msg);
					break;
				case 7 :
					memberAge = inputMemberAgeOver(age);
					list = memberController.foundAgeMember(memberAge);
					displayMemberList(list);
					break;
				case 0:
					System.out.println("정말로 끝내시겠습니까?(y/n) : ");
					if(sc.next().charAt(0) == 'y') {
						//현재 메소드를 호출한곳으로 돌아감
						return;
					}
					break;

				default:
					System.out.println("잘못 입력하셨습니다.");

				}

			}

		}

	private int inputMemberAgeOver(int age) {
		System.out.print("검색할 최소 나이를 입력 하세요 : ");	
			age = sc.nextInt();
		return age;
	}

		/**
		 * 회원 아이디로 삭제
		 * 
		 */
	private String inputdeleteMemberId() {
		System.out.print("삭제할 회원의 아이디를 입력하세요 : ");
		
		return sc.next();
	}
	/**
	 * 정보를 수정할 아이디를 찾고
	 * 정보를 수정
	 * 
	 */
	private Member inputchangeMemberId() {
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
	/*
	 * 이름의 일부를 통해 회원정보 검색
	 */
	private String inputMemberName() {
		System.out.print("조회할 회원 이름을 입력 : ");
		
		return sc.next();
	}
	/**
	 * DB에서 조회한 1명의 회원 출력
	 */
	private void displayMember(Member member) {
		if(member == null)
			System.out.println(">>> 조회된 회원이 없습니다.");
		else 
			System.out.println("******************************");
			System.out.println(member);
			System.out.println("******************************");
	}
	/**
	 * 조회할 회원아이디 입력
	 */
	private String inputMemberId() {
		System.out.print("조회할 회원 아이디를 입력 : ");
		
		return sc.next();
	}
	/*
	 * DB에서 조회된 회원객체 n개를 출력
	 */
	private void displayMemberList(List<Member> list) {
		//0행이 리턴되는 경우도 있음
		if(list == null || list.isEmpty()) {
			System.out.println(">>> 조회된 행이 없습니다.");
		} else {
			System.out.println("************************************");
			for(Member m : list)
				System.out.println(m);
		}
		System.out.println("************************************");
	}
	/**
	 * DML 처리결과 통보용
	 * 
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
		
	}
	/**
	 * 신규회원 정보 입력
	 * 
	 */
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

}
