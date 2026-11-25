<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.project.daoimpl.RestaurantDaoImpl,com.project.model.Restaurant,java.util.List" %>

<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>FoodApp</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

<style>

html {
  scroll-behavior: smooth;
}

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:'Poppins',sans-serif;
}

body{
background:#f6f6f6;
color:#222;
}

/* NAVBAR */

nav{
background:white;
display:flex;
justify-content:space-between;
align-items:center;
padding:16px 7%;
box-shadow:0 3px 12px rgba(0,0,0,0.08);
position:sticky;
top:0;
z-index:100;
}

.logo{
font-size:24px;
font-weight:700;
color:#ff6b35;
}

.nav-links a{
margin-left:22px;
text-decoration:none;
color:#333;
font-weight:500;
}

a {
  text-decoration: none;
  color: black; /* or any color you want */
}

a:visited {
  color: black;
}

a:hover {
  text-decoration: none;
  color: black;
}

a:active {
  color: black;
}

.nav-btn{
background:#ff6b35;
border:none;
padding:8px 16px;
border-radius:6px;
color:white;
cursor:pointer;
margin-left:12px;
}

/* HERO */

.hero{
height:420px;
background:linear-gradient(135deg,#ff6b35,#ff914d);
display:flex;
flex-direction:column;
justify-content:center;
align-items:center;
color:white;
text-align:center;
}

.hero h1{
font-size:42px;
margin-bottom:10px;
}

.hero p{
margin-bottom:24px;
opacity:0.9;
}

.search-box{
background:white;
padding:10px;
border-radius:40px;
display:flex;
width:420px;
box-shadow:0 4px 14px rgba(0,0,0,0.15);
}

.search-box input{
border:none;
flex:1;
padding:10px;
outline:none;
}

.search-box button{
background:#ff6b35;
border:none;
color:white;
padding:10px 20px;
border-radius:30px;
cursor:pointer;
}

/* CATEGORIES */

.categories{
padding:50px 7%;
}

.categories h2{
margin-bottom:24px;
}

.cat-grid{
display:grid;
grid-template-columns:repeat(auto-fill,minmax(140px,1fr));
gap:20px;
}

.cat{
background:white;
padding:24px;
text-align:center;
border-radius:12px;
box-shadow:0 4px 12px rgba(0,0,0,0.08);
font-weight:600;
cursor:pointer;
transition:0.2s;
}

.cat:hover{
transform:translateY(-4px);
}

/* RESTAURANTS */

.restaurants{
padding:20px 7% 60px;
}

.restaurants h2{
margin-bottom:24px;
}

.rest-card{
display:flex;
background:white;
border-radius:14px;
overflow:hidden;
margin-bottom:20px;
box-shadow:0 4px 12px rgba(0,0,0,0.08);
transition:0.2s;
}

.rest-card:hover{
transform:translateY(-4px);
}

.rest-card img{
width:220px;
height:160px;
object-fit:cover;
}

.rest-info{
padding:16px;
flex:1;
}

.rest-name{
font-size:18px;
font-weight:600;
}

.rest-meta{
color:#777;
font-size:14px;
margin-top:4px;
}

.rating{
margin-top:10px;
color:#f5b50a;
font-weight:600;
}

/* FOOTER */

footer{
background:#111;
color:#bbb;
padding:40px 7%;
text-align:center;
}

</style>

</head>

<body>

<!-- NAVBAR -->

<nav>

<div class="logo">FoodApp</div>

<div class="nav-links">
<a href="#home">Home</a>
<a href="#restau">Restaurants</a>
<a href="#">Offers</a>
<a href="#">Help</a>

<a href="login.html"><button class="nav-btn">Login</button></a>
<a href="register.html"><button class="nav-btn">Sign Up</button></a>

</div>

</nav>


<!-- HERO -->

<section id="home" class="hero">

<h1>Order Food From Best Restaurants</h1>
<p>Discover great food around you in Bangalore</p>

<!-- <div class="search-box">
<input type="text" placeholder="Search restaurants or cuisines">
<button>Search</button>
</div> -->

</section>


<!-- CATEGORIES -->

<section class="categories">

<h2>Popular Cuisines</h2>

<div class="cat-grid">

<div class="cat">🍛 Biryani</div>
<div class="cat">🍕 Pizza</div>
<div class="cat">🍔 Burgers</div>
<div class="cat">🥘 South Indian</div>
<div class="cat">🍣 Asian</div>
<div class="cat">🍰 Desserts</div>

</div>

</section>


<!-- RESTAURANTS -->

<section id="restau" class="restaurants">

<h2>Restaurants Near You</h2>

<%
List<Restaurant> list = (List<Restaurant>) request.getAttribute("allRestaurants");

if(list!=null && !list.isEmpty()){
for(Restaurant r : list){
%>

<a href="menu?restaurantId=<%= r.getId() %>">
<div class="rest-card">

<img src="<%= r.getImageUrl() %>" alt="<%= r.getName() %>">

<div class="rest-info">

<div class="rest-name"><%= r.getName() %></div>

<div class="rest-meta">
<%= r.getCategory() %> • <%= r.getLocation() %>
</div>

<div class="rest-meta">
₹<%= r.getPriceForTwo() %> for two
</div>

<div class="rest-meta">
Distance : <%= r.getDistanceKm() %> km
</div>

<div class="rating">
★ <%= r.getRating() %>
</div>

<div class="rest-meta">
Offer : <%= r.getOffer() %>
</div>

</div>

</div>
</a>

<%
}
}
%>

</section>


<!-- FOOTER -->

<footer>

<p>© 2025 FoodApp Pvt Ltd. All Rights Reserved</p>

</footer>

</body>

</html>

