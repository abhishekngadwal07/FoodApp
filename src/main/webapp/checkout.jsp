<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="com.project.model.User, java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>

<style>
body {
    margin: 0;
    font-family: 'Segoe UI', sans-serif;
    background-color: #121212;
    color: #ffffff;
}

/* Container */
.container {
    width: 50%;
    margin: 40px auto;
    background: #1e1e1e;
    padding: 30px;
    border-radius: 12px;
    box-shadow: 0px 4px 20px rgba(0,0,0,0.6);
}

/* Headings */
h2 {
    margin-bottom: 20px;
    color: #fc8019;
}

h3 {
    margin-bottom: 10px;
}

/* Sections */
.section {
    margin-bottom: 25px;
}

/* Address Box */
textarea {
    width: 100%;
    height: 100px;
    padding: 12px;
    border-radius: 8px;
    border: 1px solid #333;
    background-color: #2a2a2a;
    color: #fff;
    resize: none;
}

/* Payment Options */
.payment-option {
    display: flex;
    align-items: center;
    background: #2a2a2a;
    padding: 12px;
    border-radius: 8px;
    margin-bottom: 10px;
    cursor: pointer;
    transition: 0.3s;
}

.payment-option:hover {
    background: #333;
}

.payment-option input {
    margin-right: 10px;
}

/* Button */
button {
    width: 100%;
    padding: 15px;
    background-color: #fc8019;
    border: none;
    color: white;
    font-size: 16px;
    border-radius: 8px;
    cursor: pointer;
    transition: 0.3s;
}

button:hover {
    background-color: #e56f14;
}
</style>

</head>

<body>

<div class="container">

    <h2>Checkout</h2>
    
    <%
    
   		User user = (User)session.getAttribute("user");
    		if(user != null){
    			%>
    			<form action="checkout" method="post">

    	        <!-- Address -->
    	        <div class="section">
    	            <h3>Delivery Address</h3>
    	            <textarea name="address" placeholder="Enter your delivery address..." required></textarea>
    	        </div>

    	        <!-- Payment -->
    	        <div class="section">
    	            <h3>Payment Method</h3>

    	            <label class="payment-option">
    	                <input type="radio" name="paymentMethod" value="COD" required>
    	                Cash on Delivery
    	            </label>

    	            <label class="payment-option">
    	                <input type="radio" name="paymentMethod" value="UPI">
    	                UPI (GPay / PhonePe)
    	            </label>

    	            <label class="payment-option">
    	                <input type="radio" name="paymentMethod" value="CARD">
    	                Credit / Debit Card
    	            </label>

    	            <label class="payment-option">
    	                <input type="radio" name="paymentMethod" value="NETBANKING">
    	                Net Banking
    	            </label>
    	        </div>

    	        <!-- Place Order -->
    	        <button type="submit">Place Order</button>

    	    </form>
    	    <% 
    		}else{
    			response.sendRedirect("login.html"); 
    		}
    %>

    

</div>

</body>
</html>