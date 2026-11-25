package com.project.servlet;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import com.project.daoimpl.UserDAOImpl;
import com.project.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		
		String username = req.getParameter("username");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String address = req.getParameter("address");
		String role = req.getParameter("role");
		
		
		String hashpw = BCrypt.hashpw(password, BCrypt.gensalt(12));
		System.out.println(hashpw);
		
		User user = new User(username, email, hashpw, address, role);
		
		UserDAOImpl userDAOImpl = new UserDAOImpl();
		
		int result = userDAOImpl.addUser(user);
		
		if(result == 1) {
			resp.sendRedirect("login.html");
		}
		else {
			resp.sendRedirect("register.html");
		}
		
		
		
	}
	
}
