package com.jspiders.studentsapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class StudentDAO_BKP2 
{
	public HashMap<String,String> authenticate(String regno, String password)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try 
		{
			//1. Load the Driver
			//Driver Class : com.mysql.jdbc.Driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			//2. Get the DB Connection via Driver 
			String dbUrl = "jdbc:mysql://localhost:3306/BECM6_DB?user=j2ee&password=j2ee";
			con = DriverManager.getConnection(dbUrl);
			
			//3. Issue SQL Queries via Connection 
			String query = " select * from "+
							" students_info si, "+
							" guardian_info gi, "+
							" students_otherinfo soi "+
							" where si.regno = gi.regno "+
							" and si.regno = soi.regno "+
							" and soi.regno = ? "+
							" and soi.password = ? ";
			
			System.out.println("Query : "+query);
			
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(regno) );
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			
			//4. Process the Results returned by SQL Queries
			if(rs.next())
			{
				HashMap<String,String> data = new HashMap<String,String>();
				data.put("regno", rs.getInt("si.regno")+"");
				data.put("fnm", rs.getString("si.firstname"));
				return data;
			}else{
				return null;	
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally{
			//5. Close All JDBC Objects
			try 
			{
				if(con!=null){
					con.close();
				}
				if(pstmt!=null){
					pstmt.close();
				}
				if(rs!=null){
					rs.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//End of outer try-catch block
	}//End of authenticate()
}//End of Class