package member.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static common.JDBCTemplate.*;

import member.model.exception.MemberException;
import member.model.vo.Member;

public class MemberDao {

//	  DAO가 담당 (3 ~ 4)
//	  3. PreparedStatement 객체 생성(미완성쿼리)
//	  3.1 ?에 값대입
//	  4. 실행 DML(executUpdate)-> 정수형은 리턴, DQL(executeQuery)은 ResultSet으로 리턴
//	  4.1 ResultSet -> 자바객체 옮겨담기
//	  5. 자원반납(생성순서의 역순으로 반납 rser - pstmt) close처리
	private Properties prop = new Properties();
	
	
	/**
	 * 1. MemberDao 객체 생성 시 (최초1회) 
	 * member-query.properties의 내용을 읽어다  prop에 저장한다.
	 * 
	 * 2. dao메소드 호출시마다 prop으로부터 query를 가져다 사용한다.
	 */
	public MemberDao() {
		String fileName = "resources/member-query.properties";
		try {
			prop.load(new FileReader(fileName));
			
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		
	}
	
	
	public List<Member> selectAll(Connection conn) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("selectAll");
		
		List<Member> list = null;
		
		try {
//		  3. PreparedStatement 객체 생성(미완성쿼리)
			pstmt = conn.prepareStatement(sql);
//			  3.1 ?에 값대입 (DQL일 경우 불필요)
			
//			  4. 실행 DML(executUpdate)-> 정수형은 리턴, DQL(executeQuery)은 ResultSet으로 리턴
			rset = pstmt.executeQuery();
			
//			  4.1 ResultSet -> 자바객체 옮겨담기
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				
				list.add(member);
				
			}
						
		} catch (SQLException e) {

			//메시지만 출력되고 프로그램종료는 되지 않음
			//e.printStackTrace();
			//예외를 전환 : RuntiemException, 의미분명한 커스텀 예외객체로 전환
			throw new MemberException("회원 전체 조회", e);
			
		} finally {

			// 5. 자원반납(생성순서의 역순으로 반납 rser - pstmt) close처리
			close(rset);
			close(pstmt);

		}

		return list;
	}

	public int insertMember(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		int result = 0;
		
		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			//e.printStackTrace();
			throw new MemberException("회원 가입", e);
		} finally {
			
			close(pstmt);
			
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = null;
		int result = 0;

		try {

			if (member.getPassword() != null && member.getEmail() != null && member.getAddress() != null) {
				sql = prop.getProperty("updateMember");

				pstmt = conn.prepareStatement(sql);

				pstmt.setString(1, member.getPassword());
				pstmt.setString(2, member.getEmail());
				pstmt.setString(3, member.getPhone());
				pstmt.setString(4, member.getAddress());
				pstmt.setString(5, member.getHobby());
				pstmt.setString(6, member.getMemberId());

			} else if (member.getPassword() != null && member.getEmail() == null && member.getAddress() == null) {

				sql = prop.getProperty("updatePassword");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getPassword());
				pstmt.setString(2, member.getMemberId());

			} else if (member.getPassword() == null && member.getEmail() != null && member.getAddress() == null) {

				sql = prop.getProperty("updateEmail");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getMemberId());
				
			} else if (member.getPassword() == null && member.getEmail() == null && member.getAddress() != null) {

				sql = prop.getProperty("updateEmail");
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, member.getEmail());
				pstmt.setString(2, member.getAddress());
			}

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			// e.printStackTrace();
			throw new MemberException("회원 정보 수정", e);

		} finally {

			close(pstmt);

		}

		return result;
	}

	public int updatePassword(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "update member set password = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberId());
			
			result = pstmt.executeUpdate();
						
		} catch (SQLException e) {
			
			throw new MemberException("비밀번호 수정" , e);
			
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateEmail(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		String sql = "update member set email = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getEmail());
			pstmt.setString(2, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstmt);

		}

		return result;
	}
	
	public int updateAddress(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "update member set adress = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getAddress());
			pstmt.setString(2, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<Member> selectOne(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "select * from member where member_id = ?";
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getMemberId());
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member m = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				
				list.add(m);
				
			}
			
			
		} catch (SQLException e) {
			
			throw new MemberException("회원 한명 조회", e);
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int deleteMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		
		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			throw new MemberException("회원 탈퇴", e);
		} finally {
			
			close(pstmt);
		}

		return result;
	}
	
	public List<Member> selectDeletedMember(Connection conn) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectDeletedMember");
		List<Member> list = null;
		ResultSet rset = null;
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				String memberId = rset.getString("member_Id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				Date delDate = rset.getDate("del_date");
				
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate, delDate);
				list.add(member);
			}
			
			
			
		} catch (SQLException e) {

			throw new MemberException("탈퇴 회원 조회", e);
		} finally {

			close(rset);
			close(pstmt);

		}
		
		
		return list;
	}


	public List<Member> selectName(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectName");
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + member.getMemberName() + "%");
				
			rset = pstmt.executeQuery();
			
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				
				String memberId = rset.getString("member_Id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				Member m = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(m);
			}
			
		} catch (SQLException e) {
			
			throw new MemberException("회원 이름 조회", e);
			
		} finally {
			close(rset);
			close(pstmt);
		}
		
		
		return list;
	}
}
