package member.model.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import member.model.dao.MemberDao;
import member.model.vo.Member;

//생성한 jdbc의 메소드 전부 호출
import static common.JDBCTemplate.*;

/**
 * Service
 * 1. DriverClass 등록(최초 1회)
 * 2. Connection 객체 생성 url, users, password
 * 2.1 자동커밋 false설정
 *  -----DAO 요청 ------
 * 
 * 6. 트랜잭션 처리 commit(DML)
 * 7. 자원반납(conn) close처리
 * 
 * DAO가 담당 (3 ~ 4)
 * 3. PreparedStatement 객체 생성(미완성쿼리)
 * 3.1 ?에 값대입
 * 4. 실행 DML(executUpdate)-> 정수형은 리턴, DQL(executeQuery)은 ResultSet으로 리턴
 * 4.1 ResultSet -> 자바객체 옮겨담기
 * 5. 자원반납(생성순서의 역순으로 반납 rser - pstmt) close처리
 * 
 * 성격에 따라 서비스, Dao 나눠서 사용 3~4는 dao, 나버지는 service에서 처리
 */
public class MemberService  {
	
	private MemberDao memberDao = new MemberDao();
	
	
	/**
	 * 1. DriverClass 등록(최초 1회)
	 * 2. Connection 객체 생성 url, users, password
	 * 2.1 자동커밋 false설정
	 * -----DAO 요청 ------
	 * 
	 * 6. 트랜잭션 처리 commit(DML)
	 * 7. 자원반납(conn) close처리
	 * 
	 */
	
	public List<Member> selectAll(){
		
		//jdbctemplate를 임포트 하여 클래스명 생략, 이탤릭체
		Connection conn = getConnection();
		List<Member> list = memberDao.selectAll(conn);
		close(conn);
		
		return list;
		
	}
	
	
	
	
//	public List<Member> selectAll__() {
//		
//		
//		Connection conn = null;
//		List<Member> list = null;
//		
//		try {
//			//1. DriverClass 등록(최초 1회)
//			Class.forName(driverClass);
////			  2. Connection 객체 생성 url, users, password
//			conn = DriverManager.getConnection(url, user, password);
////			  2.1 자동커밋 false설정
//			//오토커밋 트루이면 모든 sql문을  커밋함
//			conn.setAutoCommit(false);
////			  -----DAO 요청 ------
//			//Connection객체 전달
//			list = memberDao.selectAll(conn);
////			  6. 트랜잭션 처리 commit(DML)
//			
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			
//			try {
////		  7. 자원반납(conn) close처리
//				if (conn != null)
//					conn.close();
//			} catch (SQLException e) {
//
//				e.printStackTrace();
//			}
//		}
//
//		return list;
//	}


	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn, member);
		//dml이므로 트랜잭션 필요
		if(result>0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		
		return result;
	}


	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateMember(conn, member);
		
		if(result>0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		
		return result;
	}




	public int updatePassword(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updatePassword(conn, member);
		
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}


	public int updateEmail(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateEmail(conn, member);
		if(result>0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		
		return result;
	}




	public int updateAddress(Member member) {
		Connection conn = getConnection();
		int result = memberDao.updateAddress(conn, member);
		
		if(result>0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		
		return 0;
	}




	public List<Member> selectOne(Member member) {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectOne(conn, member);

		close(conn);

		return list;
	}



	public int deleteMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn, member);
		
		close(conn);
		return result;
	}




	public List<Member> selectDeletedMember() {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectDeletedMember(conn);
		
		close(conn);
		
		return list;
	}




	public List<Member> selectName(Member member) {
		Connection conn = getConnection();
		List<Member> list = memberDao.selectName(conn, member);
		
		close(conn);
		return list;
	}

}
