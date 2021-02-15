package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import member.model.vo.Member;

/**
 * DAO
 * Data Access Object
 * DB에 접근하는 클래스
 *
 *1. 드라이버클래스 등록 (최초1회)
 *2. Connection객체 생성(url, user, password)
 *3. 자동커밋여부 설정 : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
 *4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
 *5. Statement 객체 실행. DB에 쿼리 요청
 *6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 -> 자바객체(ex:list)로 전환
 *7. 트랜잭션처리(DML)
 *8. 자원반납(생성의 역순) close
 *
 */
public class MemberDao {
	
	String driverClass = "oracle.jdbc.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String user = "student";
	String password = "student";
	
	public int insertMember(Member member) {
		
		Connection conn = null;
		String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
		PreparedStatement pstmt = null;
		int result = 0;
		
		//1. 드라이버클래스 등록 (최초1회)
		
		try {
			Class.forName(driverClass);
		//2. Connection객체 생성(url, user, password) xe는 오라클 버전
			//멤버변수로 올림
			
			conn = DriverManager.getConnection(url, user, password);
		//3. 자동커밋여부 설정(DML) : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
			conn.setAutoCommit(false);
		//4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
			//sql문안에 ;찍지 않기
			//String sql = "insert into member values(?, ?, ?, ?, ?, ?, ?, ?, ?, default)";
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
			
		//5. Statement 객체 실행. DB에 쿼리 요청
		//6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 ->자바객체로 전환
			//dql은 executequery
			result = pstmt.executeUpdate(); //1이 리턴
			
		//7. 트랜잭션처리(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		
			//sql익셉션이 sql의 최상위 예외처리
		} catch (ClassNotFoundException | SQLException e) {
			//ojdbc6.jar 프로젝트 연동실패!
			e.printStackTrace();
			
		} finally {
			//8. 자원반납(생성의 역순) close
			//예외 상관없이 자원 반납
			//따로 해줘야 한다.
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	public List<Member> selectAll() {
		Connection conn = null;
		String sql = "select * from member order by enroll_date desc";
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Member> list = null;
		
		try {
			//1. 드라이버클래스 등록 (최초1회)
			Class.forName(driverClass);
			//2. Connection객체 생성(url, user, password)
			//3. 자동커밋여부 설정 : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password); 
			//4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
			pstmt = conn.prepareStatement(sql);
			//dql이라 값대입없음
			//5. Statement 객체 실행. DB에 쿼리 요청
			//while문을 통해 한행씩 접근하는 것 -> Member 객체로 대입
			rset = pstmt.executeQuery();
			//다음행 존재여부리턴. 커서가 다음행에 접근.
			//커서와 같은 역할, 차례대로 행을 가져오는 것
			//행이 없을 때까지 계속 가져옴
			
			list = new ArrayList<>();
			
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
				//각 행의 컬럼을 자바 변수에 입력
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
			//6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 ->자바객체로 전환
			//7. 트랜잭션처리(DML)
						
		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {
			// 8. 자원반납(생성의 역순) close
			// 안전성을 위해 각 따로
			try {
				if (rset != null)
					rset.close();
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
			try {
				if (pstmt != null)
					pstmt.close();
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
			try {
				if (conn != null)
					conn.close();
				
			} catch (SQLException e) {

				e.printStackTrace();
			}
			
		}
		
		return list;
		
	}
	
	public Member selectOne(String memberId) {
		Connection conn = null;
		String sql = "select * from member where member_id = ?";
		PreparedStatement pstmt = null;
		//한행이라도 resultset
		ResultSet rset = null;
		Member member = null;
		
		try {
			//1. 드라이버클래스 등록 (최초1회)
			
			Class.forName(driverClass);
			//2. Connection객체 생성(url, user, password)
			//3. 자동커밋여부 설정 : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password); 
			
			//4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
			pstmt = conn.prepareStatement(sql);
		
			//dql이라 값대입없음
			//5. Statement 객체 실행. DB에 쿼리 요청
			pstmt.setString(1, memberId);
			
			//0행이어도 resultset
			rset = pstmt.executeQuery();
						
			
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
				//각 행의 컬럼을 자바 변수에 입력
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				
			}
			//6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 ->자바객체로 전환
			//7. 트랜잭션처리(DML)
						
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
		//8. 자원반납(생성의 역순) close
			//안전성을 위해 각 따로
			try {
				if(rset != null)
				rset.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
				pstmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return member;
		
	}
	public Member selectName(String memberName) {
		Connection conn = null;
		String sql = "select * from member where member_name like ?";
		PreparedStatement pstmt = null;
		//한행이라도 resultset
		ResultSet rset = null;
		Member member = null;
		
		try {
			//1. 드라이버클래스 등록 (최초1회)
			Class.forName(driverClass);
			//2. Connection객체 생성(url, user, password)
			//3. 자동커밋여부 설정 : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
			conn = DriverManager.getConnection(url, user, password); 
			//4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
			pstmt = conn.prepareStatement(sql);
			//dql이라 값대입없음
			//5. Statement 객체 실행. DB에 쿼리 요청
			pstmt.setString(1, "%"+memberName+"%");
			
			//0행이어도 resultset
			rset = pstmt.executeQuery();
			
			
			while(rset.next()) {
				//컬럼명은 대소문자를 구분하지 않는다.
				//각 행의 컬럼을 자바 변수에 입력
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("adress");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				
			}
			//6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 ->자바객체로 전환
			//7. 트랜잭션처리(DML)
						
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
		//8. 자원반납(생성의 역순) close
			//안전성을 위해 각 따로
			try {
				if(rset != null)
				rset.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				if(pstmt != null)
				pstmt.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			try {
				if(conn != null)
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return member;
	}
	
	public int changeInfo(Member member) {

//		Scanner sc = new Scanner(System.in);
//		System.out.print("변경할 암호를 입력하세요 >>>");
//		String pwd = sc.next();
		
		Connection conn = null;
		String sql = "update member set password = ?, email = ?, phone = ?, adress = ?, hobby = ? where member_id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		//암호, 이메일, 전화번호, 주소, 취미
		//1. 드라이버클래스 등록 (최초1회)
		
		try {
			Class.forName(driverClass);
		//2. Connection객체 생성(url, user, password) xe는 오라클 버전
			//멤버변수로 올림
			
			conn = DriverManager.getConnection(url, user, password);
		//3. 자동커밋여부 설정(DML) : 설정없으면 자동으로 커밋 true(기본값) / false ->app에서 직접 트랜잭션 제어
			conn.setAutoCommit(false);
		//4. preparedStatement객체 생성(미완성쿼리) 및 갑대입
			//sql문안에 ;찍지 않기
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getEmail());
			pstmt.setString(3, member.getPhone());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getHobby());
			pstmt.setString(6, member.getMemberId());
			
			
		//5. Statement 객체 실행. DB에 쿼리 요청
		//6. 응답처리 DML : int 리턴, DQL : ResultSet 리턴 ->자바객체로 전환
			//dql은 executequery
			result = pstmt.executeUpdate(); //1이 리턴
			
		//7. 트랜잭션처리(DML)
			if(result > 0)
				conn.commit();
			else
				conn.rollback();
		
			//sql익셉션이 sql의 최상위 예외처리
		} catch (ClassNotFoundException | SQLException e) {
			//ojdbc6.jar 프로젝트 연동실패!
			e.printStackTrace();
			
		} finally {
			//8. 자원반납(생성의 역순) close
			//예외 상관없이 자원 반납
			//따로 해줘야 한다.
			try {
				if(pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return result;
	}
	
	public int deleteMember(String memberId) {
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		String sql = "delete from member where member_id = ?";
		PreparedStatement pstmt = null;
		int result = 0;
		
		try {
			Class.forName(driverClass);

			conn = DriverManager.getConnection(url, user, password);

			conn.setAutoCommit(false);

			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, memberId);
			
			result = pstmt.executeUpdate();

			System.out.print("정말로 삭제하시겠습니까(Y/N) : ");
			char yn = sc.next().toUpperCase().charAt(0);
			
			if(yn == 'Y' && result > 0) {
				//if (result > 0)
				conn.commit();
			}else {
				conn.rollback();
			}

		} catch (ClassNotFoundException e) {

			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			try {
				if (pstmt != null)
					pstmt.close();

			} catch (SQLException e) {

				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return result;

	}

	public List<Member> foundAgeMember(int memberAge) {
		
		Connection conn = null;
		String sql = "select * from member where age >= ?";
		PreparedStatement pstmt = null;
		List<Member> list = null;
		ResultSet rset = null;
		
		try {
			Class.forName(driverClass);
			
			conn = DriverManager.getConnection(url, user, password);
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, memberAge);
			
			rset = pstmt.executeQuery();
			
			list = new ArrayList<>();
			
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
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {

			try {
				if (rset != null)
					rset.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}

		}

		return list;
	}

}
