package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * Service, DAO클래스의 공통부문을 static메소드 제공
 * 예외처리를 공통부분에서 작성하므로, 사용(호출)하는 쪽의 코드를 간결히 할 수 있다.
 *
 */

public class JDBCTemplate {
	
	static String driverClass = "oracle.jdbc.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
	static String user = "student";
	static String password = "student";

	static {
		// 1. DriverClass 등록(최초 1회)
		//초기화블럭 처리
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}

	}

	public static Connection getConnection() {

		Connection conn = null;

		try {

			// 2. Connection 객체 생성 url, users, password
			conn = DriverManager.getConnection(url, user, password);
			// 2.1 자동커밋 false설정
			// 오토커밋 트루이면 모든 sql문을 커밋함
			conn.setAutoCommit(false);

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return conn;

	}
	
	public static void close(Connection conn) {

		try {
//			  7. 자원반납(conn) close처리
			if (conn != null)
				conn.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void close(ResultSet rset) {

		try {
			if (rset != null)
				rset.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {

			if (pstmt != null)
				pstmt.close();
		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public static void commit(Connection conn) {
		try {
			if(conn != null)
			conn.commit();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn != null)
			conn.rollback();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

}
