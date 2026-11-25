<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page
	import="com.project.daoimpl.MenuDaoImpl,com.project.model.Menu,java.util.List"%>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu Card</title>
<style>
* {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
	font-family: 'Segoe UI', sans-serif;
}

body {
	background-color: #121212;
	color: #fff;
}

.container {
	width: 90%;
	max-width: 1200px;
	margin: 30px auto;
}

.header {
	margin-bottom: 30px;
}

.header h1 {
	font-size: 32px;
	font-weight: 600;
}

.menu-grid {
	display: grid;
	grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
	gap: 20px;
}

.card {
	background: #1e1e1e;
	border-radius: 12px;
	overflow: hidden;
	transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.card:hover {
	transform: translateY(-5px);
	box-shadow: 0 6px 20px rgba(0, 0, 0, 0.5);
}

.card img {
	width: 100%;
	height: 180px;
	object-fit: cover;
}

.card-content {
	padding: 15px;
}

.card-content h3 {
	font-size: 18px;
	margin-bottom: 8px;
}

.card-content p {
	font-size: 14px;
	color: #bbb;
	margin-bottom: 10px;
}

.price {
	font-size: 16px;
	font-weight: 600;
	color: #00e676;
}

.tag {
	display: inline-block;
	font-size: 12px;
	padding: 4px 8px;
	border-radius: 6px;
	margin-top: 8px;
}

.available {
	background-color: #1b5e20;
	color: #a5d6a7;
}

.not-available {
	background-color: #b71c1c;
	color: #ef9a9a;
	}
	







/* --- BULLETPROOF GRID LAYOUT --- */
.card-content {
    padding: 15px;
    display: grid;
    grid-template-columns: 1fr auto; /* Creates two columns: left side takes remaining space, right side hugs the button */
    align-items: center; /* Keeps the bottom row vertically level */
}

/* Force the top three elements to span across both columns */
.card-content h3,
.card-content p,
.card-content .price {
    grid-column: 1 / -1; 
}

/* Force the tag into the bottom-left slot */
.tag {
    grid-column: 1 / 2;
    justify-self: start;
    margin-top: 12px;
}

/* Force the form/button into the bottom-right slot */
.card-content form {
    grid-column: 2 / 3;
    justify-self: end;
    margin-top: 12px;
}












/* Swiggy Button Styling applied directly to the submit input */
.card-content form input[type="submit"] {
	background-color: #fff;
	color: #60b246;
	border: none;
	border-radius: 6px;
	padding: 6px 24px;
	font-size: 14px;
	font-weight: 650; 
	text-transform: uppercase;
	cursor: pointer;
	box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
	transition: transform 0.1s ease, box-shadow 0.1s ease;
}

/* Hover & Click effects for an interactive feel */
.card-content form input[type="submit"]:hover {
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.4);
}

.card-content form input[type="submit"]:active {
	transform: scale(0.95);
	/* Slight "press down" animation when clicked */
}

</style>
</head>
<body>

	<div class="container">
		<div class="header">
			<h1>Restaurant Menu</h1>
		</div>

		<div class="menu-grid">

			<%
			List<Menu> allMenuByRestaurant = (List<Menu>) request.getAttribute("allMenuByRestaurant");
			if (!allMenuByRestaurant.isEmpty()) {
				for (Menu menu : allMenuByRestaurant) {
			%>
			<div class="card">
				<img src="<%=menu.getImageUrl()%>" alt="Candy">
				<div class="card-content">
					<h3><%=menu.getItemName()%></h3>
					<p><%=menu.getDescription()%></p>
					<div class="price">
						₹<%=menu.getPrice()%></div>
					<span class="tag available">Available</span>

					<form action="cart">

						<input type="hidden" name="menuId" value="<%=menu.getMenuId()%>">
						<input type="hidden" name="quantity" value="1"> <input
							type="hidden" name="restaurantId"
							value="<%=menu.getRestaurantId()%>"> <input
							type="hidden" name="action" value="add"> <input
							type="submit" value="Add">

					</form>



				</div>
			</div>

			<%
			}
			} else {
			%>
			<h1>Menu Empty</h1>

			<%
			}
			%>



		</div>
	</div>

</body>
</html>
