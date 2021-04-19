package net.ucm.usermanagement.dao;


import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.*;
import java.sql.Connection;
import java.sql.Date;

import java.sql.Statement;
import java.sql.Timestamp;

import com.mysql.cj.protocol.Resultset;
//import com.sun.tools.javac.util.List;

import net.ucm.usermanagement.model.User;


public class UserDAO {

	
	private String jdbcURL  = "jdbc:mysql://localhost:3306/demo?useSSL=false";
	private String jdbcusername = "root";
	private String jdbcpassword = "";
	
	private static final String INSERT_USERS_SQL = "INSERT into users " + " (name,email , country ) values " + "(?,?,?);";
	
	private static final String SELECT_ALL_USERS = " select * from users";
	
	private static final String SELECT_USER_BY_ID = " select id,name,email,country from users where id = ?";
	
	private static final String DELETE_USERS_SQL = " delete from users where id = ? ";
	
	
	private static final String UPDATE_USERS_SQL = " update users set name = ?, email = ? , country =? where id = ? ;";
	
	
	protected Connection getConnection() {
		
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//con = DriverManager.getConnection("jdbc:mysql://localhost/superspeed","root","");
			connection = DriverManager.getConnection(jdbcURL,jdbcusername,jdbcusername);
			//Statement stmt = con.createStatement();
			//ResultSet rs;
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
		
		
	}
	
	public void insertUser(User user )  throws SQLException 
	{
		try {
			
			Connection connection = getConnection();
			PreparedStatement  preparedstatement = connection.prepareStatement("INSERT_USERS_SQL");
			preparedstatement.setString(1, user.getName());
			preparedstatement.setString(2, user.getEmail());
			preparedstatement.setString(3, user.getCountry());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean updateUser(User user )  throws SQLException 
	{
			boolean rowUpdated = false;
		try {
			
			Connection connection = getConnection();
			PreparedStatement  statement = connection.prepareStatement("UPDATE_USERS_SQL");
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getCountry());
			statement.setInt(4, user.getId());
			
			rowUpdated = statement.executeUpdate() >0 ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rowUpdated;
	}
	
	
	public User selectUser(int id )  throws SQLException 
	{
		 User user = null;
		try {
			
			Connection connection = getConnection();
			PreparedStatement  statement = connection.prepareStatement("SELECT_USER_BY_ID");
			statement.setInt(1, id);
			
			System.out.println(statement);
			
			ResultSet rs = statement.executeQuery();
			
			
			while (rs.next())
			{
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				user = new User (id, name, email, country);
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	
	public List<User> selectAllUsers()  throws SQLException 
	{
		List<User> users = new ArrayList();
		try {
			
			Connection connection = getConnection();
			PreparedStatement  statement = connection.prepareStatement("SELECT_USER_BY_ID");
			
			
			System.out.println(statement);
			
			ResultSet rs = statement.executeQuery();
			
			
			while (rs.next())
			{
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				String country = rs.getString("country");
				users.add (new User (id, name, email, country));
			}
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean deleteUser(int id )  throws SQLException 
	{
			boolean rowDeleted= false;
		try {
			
			Connection connection = getConnection();
			PreparedStatement  statement = connection.prepareStatement("DELETE_USERS_SQL");
	
			statement.setInt(1, id);
			
			rowDeleted = statement.executeUpdate() >0 ;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return rowDeleted;
	}
	
	
}
