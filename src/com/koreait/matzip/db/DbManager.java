package com.koreait.matzip.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbManager {

	public static Connection getCon() throws Exception{
		String url = "jdbc:mysql://localhost:3306/matzip?userUnicode=true&characterEncoding=UTF-8";
													//mySQL에서 가끔 한글깨지는게 나올수도 있으니 여기서 쿼리스트링으로 인코딩 해줌
				//					주소:3306/계정이름 <--전부 db에 나와있음
		String user = "root";
		String pw = "koreait2020";
		String className = "com.mysql.cj.jdbc.Driver";
		
		Class.forName(className);
		Connection con = DriverManager.getConnection(url,user,pw);
		System.out.println("DB 연결 완료!");
		return con;
	}
	
	public static void close(Connection con, PreparedStatement ps, ResultSet rs) {
		if(rs != null){try {rs.close();}catch(Exception e) {}}
		if(ps != null){try {ps.close();}catch(Exception e) {}}
		if(con != null){try {con.close();}catch(Exception e) {}}
	}
	
	public static void close(Connection con, PreparedStatement ps) {
		close(con, ps, null);
	}

}
