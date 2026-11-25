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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		
		String name = req.getParameter("name");
		String password = req.getParameter("password");
		
		UserDAOImpl userDAOImpl = new UserDAOImpl();
		User user = userDAOImpl.getUser(name);

		if (user == null) {
		    System.out.println("DEBUG: User not found for name = " + name);
		    resp.sendRedirect("login.html");
		    return; 
		}

		String dbPassword = user.getPassword();

		if (BCrypt.checkpw(password, dbPassword)) {
		    session.setAttribute("user", user);
		    resp.sendRedirect("callingRestaurant");
		} else {
		    resp.sendRedirect("login.html");
		}
		
		
		
		
	}
	
}
