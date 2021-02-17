package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static common.JDBCTemplate.*;
import member.model.vo.Member;

public class MemberDao {

//	  DAO가 담당 (3 ~ 4)
//	  3. PreparedStatement 객체 생성(미완성쿼리)
//	  3.1 ?에 값대입
//	  4. 실행 DML(executUpdate)-> 정수형은 리턴, DQL(executeQuery)은 ResultSet으로 리턴
//	  4.1 ResultSet -> 자바객체 옮겨담기
//	  5. 자원반납(생성순서의 역순으로 반납 rser - pstmt) close처리
	
	
	public List<Member> selectAll(Connection conn) {
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = "select * from member order by enroll_date desc";
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

			e.printStackTrace();
			
		} finally {

			// 5. 자원반납(생성순서의 역순으로 반납 rser - pstmt) close처리
			close(rset);
			close(pstmt);

		}

		return list;
	}

	public int insertMember(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
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

			e.printStackTrace();
		} finally {
			
			close(pstmt);
			
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "update member set password = ?, email = ?, phone = ?, adress = ?, hobby = ? where member_id = ?";
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId());
			
			result = pstmt.executeUpdate();
			
			
		} catch (SQLException e) {

			e.printStackTrace();
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
		
			e.printStackTrace();
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
		
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int deleteMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "delete from memeber where member_id = ?";
		int result = 0;
		
		try {

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, member.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		} finally {

			close(pstmt);
		}

		return result;
	}

	public List<Member> selectName(Connection conn, Member member) {
		
		PreparedStatement pstmt = null;
		String sql = "select * from member where member_name like ?";
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "%" + member.getMemberName() + "%");
			
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
			
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
			
		}
		
		
		return list;
	}

	
	
	
}
