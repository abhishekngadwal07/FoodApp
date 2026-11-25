package com.project.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.project.model.User;

import Util.DBConnection;

public class UserDAOImpl  {

	private static final String INSERT = "INSERT into user (userid,name,password,email,address,role,createdDate,lastLoginDate)"
			+ "values(?,?,?,?,?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT * FROM user WHERE userid =?";
	private static final String UPDATE_USER = "UPDATE user SET name=? , password=? , email=? , address=? , lastLoginDate=? WHERE userid=?";
	private static final String DELETE_USER = "DELETE FROM user WHERE userid = ?";
	private static final String GET_ALL_USERS = "SELECT * FROM user";
	private static final String GET_USER_BY_NAME = "SELECT * FROM user WHERE name =?";

	
	public int addUser(User user) {
		
		int res = 0;
		
		try(Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(INSERT);) {
			
			pstmt.setInt(1,user.getUserId());
			pstmt.setString(2, user.getName());
			pstmt.setString(3, user.getPassword());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getAddress());
			pstmt.setString(6, user.getRole());
			pstmt.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			pstmt.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			
			res = pstmt.executeUpdate();
			System.out.println(res);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
		
		
	}

	
	public User getUser(int userId) {
		
		User user = null;
		
		try (
				Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_ID);
			){
			
			pstmt.setInt(1, userId);
			
			ResultSet resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				userId = resultSet.getInt("userId");
				String username = resultSet.getString("name");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String address = resultSet.getString("address");
				String role = resultSet.getString("role");
				Timestamp createddate = resultSet.getTimestamp("createddate");
				Timestamp lastlogindate = resultSet.getTimestamp("lastlogindate");
				
				user = new User(userId, username, email, password, address, role, createddate, lastlogindate);
				
			}
			
			return user;

			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	
	public User getUser(String name) {
		
		User user = null;
		
		try (
				Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_USER_BY_NAME);
			){
			
			pstmt.setString(1, name);
			
			ResultSet resultSet = pstmt.executeQuery();
			
			while(resultSet.next()) {
				int userId = resultSet.getInt("userId");
				String username = resultSet.getString("name");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String address = resultSet.getString("address");
				String role = resultSet.getString("role");
				Timestamp createddate = resultSet.getTimestamp("createddate");
				Timestamp lastlogindate = resultSet.getTimestamp("lastlogindate");
				
				user = new User(userId, username, email, password, address, role, createddate, lastlogindate);
				
			}
			
			return user;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return user;
	}
	
	
	
	public void updateUser(User user) {
		
		try (
				Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(UPDATE_USER);
			){
			
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.setString(4, user.getAddress());
			pstmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			pstmt.setInt(6, user.getUserId());
			
			
			int res = pstmt.executeUpdate();
			
			System.out.println(res);
			
			
			
			
			
			
			
			
			
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public void deleteUser(int userId) {
		
		try (
				Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(DELETE_USER);){
			
			pstmt.setInt(1, userId);
			
			int res = pstmt.executeUpdate();
			
			System.out.println(res);
			
			
			
			
			
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	
	public List<User> getAllUsers() {
		
		List<User> listOfUsers = new ArrayList<>();
		
		try (
				Connection connection = DBConnection.getConnection();
				PreparedStatement pstmt = connection.prepareStatement(GET_ALL_USERS);){
			
			ResultSet resultSet = pstmt.executeQuery();
			
			
			while(resultSet.next()) {
				int userId= resultSet.getInt("userId");
				String username = resultSet.getString("username");
				String password = resultSet.getString("password");
				String email = resultSet.getString("email");
				String address = resultSet.getString("address");
				String role = resultSet.getString("role");
				Timestamp createddate = resultSet.getTimestamp("createddate");
				Timestamp lastlogindate = resultSet.getTimestamp("lastlogindate");
				
				User user = new User(userId, username, email, password, address, role, createddate, lastlogindate);
				
				listOfUsers.add(user);
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOfUsers;
		
	}

}
