<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>View Request</title>
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	</head>
	<body>
		<div class="container">
			<h1>Employee Reimbursement System</h1>
			<nav class="navbar navbar-expand-lg navbar-light bg-light">
			  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
			    <span class="navbar-toggler-icon"></span>
			  </button>
			  <div class="collapse navbar-collapse" id="navbarNav">
			    <ul class="navbar-nav">
			      <li class="nav-item">
			        <a class="nav-link" href="../front/ehome">Home <span class="sr-only">(current)</span></a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="../front/ecreate">Create a Request</a>
			      </li>
			      <li class="nav-item active">
			        <a class="nav-link" href="../front/eview">View Requests</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link " href="../front/logout">Logout</a>
			      </li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<div class="requestView" id=<%= request.getParameter("id") %>>
			<p><%= "Request # " + request.getParameter("id") %></p>
			<div class="card" style="width: 18rem;">
			  <img class="card-img-top" id="imgView" alt= <%= new String(request.getParameter("id")) %>>
			  <div class="card-body">
			    <h5 class="card-title"><%= "$ " + request.getAttribute("amt") %></h5>
			    <p class="card-text"><%= "Submitted: " + request.getAttribute("date") %></p>
			    <p class="card-text"><%= "Status: " + request.getAttribute("stat") %></p>
			    <a href= <%= "../req/?id=" + request.getParameter("id") %> class="btn btn-primary">Update Request</a>
			    <br>
			    <br>
			    <button id="deleteButton" class="btn btn-danger">Delete Request</button>
			  </div>
			</div>
		</div>
	</body>
	<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	<script src="../scripts/requestAction.js"></script>
</html>