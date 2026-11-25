<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.project.model.Cart, com.project.model.CartItem, java.util.*" %>
<%
    Cart cart = (Cart) session.getAttribute("cart");
	int restaurantId = (int)session.getAttribute("restaurantId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Cart</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #121212;
        color: #fff;
        margin: 0;
        padding: 20px;
    }

    h1 {
        text-align: center;
    }

    table {
        width: 90%;
        margin: 20px auto;
        border-collapse: collapse;
        background: #1e1e1e;
    }

    th, td {
        padding: 12px;
        text-align: center;
    }

    th {
        background-color: #333;
    }

    tr {
        border-bottom: 1px solid #444;
    }

    img {
        width: 80px;
        height: 60px;
        border-radius: 8px;
        object-fit: cover;
    }

    .btn {
        padding: 6px 10px;
        border: none;
        cursor: pointer;
        border-radius: 5px;
        font-size: 14px;
    }

    .plus-btn {
        background-color: #4CAF50;
        color: white;
    }

    .minus-btn {
        background-color: #ff9800;
        color: white;
    }

    .delete-btn {
        background-color: #f44336;
        color: white;
    }

    .checkout-btn, .continue-btn {
        display: inline-block;
        padding: 12px 20px;
        margin: 20px 10px;
        border-radius: 6px;
        text-decoration: none;
        font-weight: bold;
    }

    .checkout-btn {
        background-color: #ff9800;
        color: black;
    }

    .continue-btn {
        background-color: #2196F3;
        color: white;
    }

    .total {
        text-align: right;
        width: 90%;
        margin: auto;
        font-size: 18px;
        font-weight: bold;
    }

    .actions {
        text-align: center;
    }

    .empty {
        text-align: center;
        margin-top: 50px;
        font-size: 20px;
    }
</style>
</head>

<body>

<h1>Your Cart</h1>

<%
if (cart == null || cart.isEmpty()) {
%>
    <div class="empty">🛒 Your cart is empty</div>
    <div class="actions">
      <a href="menu?restaurantId=<%= session.getAttribute("restaurantId") %>" class="continue-btn">Continue Shopping</a>
    </div>
<%
} else {
%>

<table>
<tr>
    <th>Image</th>
    <th>Item</th>
    <th>Price</th>
    <th>Quantity</th>
    <th>Total</th>
    <th>Actions</th>
</tr>

<%
for (CartItem item : cart.getAllItems()) {
%>
<tr>
    <%-- <td>
        <img src="<%= item.getImageUrl() %>" alt="item">
    </td>
 --%>
    <td><%= item.getItemName() %></td>

    <td>₹<%= item.getPrice() %></td>

    <td>
        <!-- MINUS -->
        <form action="cart" method="post" style="display:inline;">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
            <input type="hidden" name="restaurantId" value="<%= restaurantId%>">
            <input type="hidden" name="quantity" value="<%= item.getQuantity() - 1 %>">
            <button class="btn minus-btn" <% if (item.getQuantity() == 1) {%>
            			disabled <%}%>>-</button>
        </form>

        <span style="margin: 0 10px;"><%= item.getQuantity() %></span>

        <!-- PLUS -->
        <form action="cart" method="post" style="display:inline;">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
            <input type="hidden" name="restaurantId" value="<%= restaurantId%>">
            <input type="hidden" name="quantity" value="<%= item.getQuantity() + 1 %>">
            <button class="btn plus-btn">+</button>
        </form>
    </td>

    <td>₹<%= item.getTotalPrice() %></td>

    <td>
        <!-- DELETE -->
        <form action="cart" method="post">
            <input type="hidden" name="action" value="delete">
            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
            <input type="hidden" name="restaurantId" value="<%= restaurantId%>">
            <button class="btn delete-btn">Remove</button>
        </form>
    </td>
</tr>
<%
}
%>

</table>

<div class="total">
    Total: ₹<%= cart.getTotalPrice() %>
</div>

<div class="actions">
    <a href="menu?restaurantId=<%= session.getAttribute("restaurantId") %>" class="continue-btn">Add More Items</a>
    <a href="checkout.jsp" class="checkout-btn">Proceed to Checkout</a>
</div>

<%
}
%>

</body>
</html>