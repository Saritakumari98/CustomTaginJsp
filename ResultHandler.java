package com.fujitsu.customTag;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ResultHandler extends TagSupport {
	
	Connection con;
	PreparedStatement stmt;
	
	public ResultHandler()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/traininfo?autoReconnect=true&useSSL=false","root","root");
			stmt=con.prepareStatement("Select * from student where name=?");
			
			
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
	}
	
	@Override
	public int doStartTag() throws JspException
	{
		ServletRequest request=pageContext.getRequest();
		String name=request.getParameter("username");
		try {
			stmt.setString(1, name);
			ResultSet rs=stmt.executeQuery();
		     JspWriter out=pageContext.getOut();
			
			
			if(rs.next())
			{
				
				out.println(" id :"+ rs.getString(1));
				out.println("name :"+ rs.getString(2));
			}
			else
			{
				out.println(" user not found ");
			}
			
		} catch (SQLException | IOException e) {
			
			e.printStackTrace();
		}
		return Tag.SKIP_BODY;
	}
	
	@Override
	public void release() {
		try {
			stmt.close();
			con.close();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		super.release();
	}

}
