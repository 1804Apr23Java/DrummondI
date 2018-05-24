<!DOCTYPE html>
<html>
	<head>
		<meta charset="ISO-8859-1">
		<title>Employee Home Page</title>
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
			        <a class="nav-link" href="ehome">Home <span class="sr-only">(current)</span></a>
			      </li>
			      <li class="nav-item active">
			        <a class="nav-link" href="ecreate">Create a Request</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="eview">View Requests</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link " href="logout">Logout</a>
			      </li>
			    </ul>
			  </div>
			</nav>
		</div>
		
		<div class="container">
		<form class="col-md-4" method="post" action= <%= "/project1/img/" + request.getAttribute("r_id") %> enctype="multipart/form-data">
		  
		  <div class="form-group">
		    <label for="requestImage">Upload a request image</label>
		    <input type="file" class="form-control" id="signupLastname"  name="image">
		  </div>
		  
		  <button type="submit" class="btn btn-primary">Submit</button>
		</form>
	</div>
	</body>
</html>