<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Social Network</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
</head>
<body>
	<div class="d-flex justify-content-center align-items-center mt-5">
		<img src="../img/b-logo.jpg" width="150" height="150">
	</div>
	<div class="d-flex justify-content-center align-items-center mt-3">
		<form class="w-50" method="post" action="login">
		  <div class="d-flex justify-content-center w-100">
	  		<div class="form-group w-50">
			    <label for="inputUsername">Tên đăng nhập</label>
			    <input type="text" class="form-control" name="inputUsername" id="inputUsername" aria-describedby="emailHelp" placeholder="Tên đăng nhập của bạn" value="${username }" required>
			    
		  	</div>
		  </div>
		  <div class="d-flex justify-content-center w-100">
		  		<div class="form-group w-50">
				    <label for="inputPassword">Mật khẩu</label>
				    <input type="password" class="form-control" name="inputPassword" id="inputPassword" aria-describedby="passwordlHelp" placeholder="Mật khẩu của bạn" required>
				    <c:if test="${status == -1 }">
			    	<small id="passwordlHelp" class="form-text text-muted "><div class="text-danger">Tên đăng nhập hoặc mật khẩu không chính xác. </div></small>
			    </c:if>
			  	</div>
		  </div>
		  <div class="d-flex justify-content-center">
		  	<button type="submit" class="btn btn-primary w-50">Đăng nhập</button>
		  </div>
		</form>
	</div>
</body>
</html>