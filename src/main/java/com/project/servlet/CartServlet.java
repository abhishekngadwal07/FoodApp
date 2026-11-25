package com.project.servlet;

import java.io.IOException;

import com.project.daoimpl.MenuDaoImpl;
import com.project.model.Cart;
import com.project.model.CartItem;
import com.project.model.Menu;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		Integer restaurantId = (Integer) session.getAttribute("restaurantId");

		int newRestId = Integer.parseInt(req.getParameter("restaurantId"));

		if (cart == null || restaurantId != newRestId) {
			cart = new Cart();

			session.setAttribute("cart", cart);
			session.setAttribute("restaurantId", newRestId);
		}

		String action = req.getParameter("action");

		if (action.equals("add")) {
			addItemToCart(req, cart);
		} else if (action.equals("update")) {
			updateItemToCart(req, cart);
		} else if (action.equals("delete")) {
			deleteItemFromCart(req, cart);
		}
		
		resp.sendRedirect("cart.jsp");

	}

	private void deleteItemFromCart(HttpServletRequest req, Cart cart) {

		int menuId = Integer.parseInt(req.getParameter("itemId"));
		cart.deleteItem(menuId);
		
		
	}

	private void updateItemToCart(HttpServletRequest req, Cart cart) {

		int menuId = Integer.parseInt(req.getParameter("itemId"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		
		cart.updateItem(menuId, quantity);
		
		
		
	}

	private void addItemToCart(HttpServletRequest req, Cart cart) {

		int menuId = Integer.parseInt(req.getParameter("menuId"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));

		MenuDaoImpl menuDaoImpl = new MenuDaoImpl();

		Menu menu = menuDaoImpl.getMenuById(menuId);

		CartItem cartItem = new CartItem(menuId, menu.getItemName(), quantity, menu.getPrice());

		cart.addItem(cartItem);
		
	}

}
