<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Register</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
</head>
<body>
	<div class="d-flex justify-content-center align-items-center mt-5">
		<div class="w-25">
			<form class="w-100" method="post" action="handleResigter">
				<div class="d-flex justify-content-center w-100">
					<div class="form-group">
				  		<label for="inputFirstName">Họ</label>
				  		<input type="text" id="inputFirstName" class="form-control" name="firstName" value="${firstName }" required="required">
				  	</div>
				  	<div class="form-group ml-3">
				  		<label for="inputLastName">Tên</label>
				  		<input type="text" id="inputLastName" class="form-control" name="lastName" value="${lastName }" required="required">
				  	</div>
				</div>
			  <div class="d-flex justify-content-begin w-100">
		  		<div class="form-group w-100">
				    <label for="inputUsername">Tên đăng nhập</label>
				    <input type="text" class="form-control w-100" name="username" id="inputUsername" aria-describedby="emailHelp" placeholder="Tên đăng nhập của bạn" value="${username }" required>
			    	<small id="emailHelp" class="form-text text-muted text-danger"><div class="">Tên đăng nhập gồm các ký tự A-z, ký tự số, không chưa ký tự đặc biệt ngoại trừ "_" và ".", 6 ký tự trở lên. </div></small>
			  	</div>
			  </div>
			  <div class="d-flex justify-content-begin w-100">
				<div class="form-group w-100">
				  <label for="inputUsername">Email</label>
				  <input type="email" class="form-control w-100" name="email" id="inputEmail" aria-describedby="emailHelp" placeholder="Email" value="${email }" required>
				</div>
			</div>
			  <div class="d-flex justify-content-begin w-100">
			  		<div class="form-group w-100">
					    <label for="inputPassword">Mật khẩu</label>
					    <input type="password" class="form-control" name="password" id="inputPassword" aria-describedby="passwordlHelp" placeholder="Mật khẩu của bạn" required>
				    	<small id="passwordlHelp" class="form-text text-muted "><div class="">Mật khẩu gồm các ký tự A-z, ký tự số, không chưa ký tự đặc biệt ngoại trừ "_" và ".", 6 ký tự trở lên. </div></small>
				  	</div>
			  </div>
			  <div class="d-flex justify-content-begin w-100">
			  		<div class="form-group w-100">
					    <label for="inputConfirmPassword">Nhập lại mật khẩu</label>
					    <input type="password" class="form-control" name="confirmPassword" id="inputConfirmPassword" placeholder="Mật khẩu của bạn" required>
				  	</div>
			  </div>
			  <div class="d-flex justify-content-center">
		  		<c:choose>
				  	<c:when test="${registerStatus == 0 }">
				  		<div class="text-success">Đăng ký thành công!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -1 }">
				  		<div class="text-danger">Tên không hợp lệ!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -2 }">
				  		<div class="text-danger">Tên đăng nhập không hợp lệ!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -3 }">
				  		<div class="text-danger">Mật khẩu không hợp lệ!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -4 }">
				  		<div class="text-danger">Mật khẩu nhập lại không đúng!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -5 }">
				  		<div class="text-danger">Tên đăng nhập đã tồn tại!</div>
				  	</c:when>
				  	<c:when test="${registerStatus == -99 }">
				  		<div class="text-danger">Hệ thống lỗi, vui lòng thử lại sau!</div>
				  	</c:when>
				  </c:choose>
			  </div>
			  <div class="d-flex justify-content-center mt-2">
			  	<button type="submit" class="btn btn-success w-50">Đăng ký</button>
			  </div>
			  <c:if test="${registerStatus == 0 }">
				  <div class="d-flex justify-content-center mt-2">
				  	<a href="login" class="btn btn-primary w-50">Đăng nhập</a>
				  </div>
		  	  </c:if>
			   <!--0: ok 
					 -1: invalid name 
					 -2: invalid username 
					 -3: invalid password 
					 -4: invalid confirm password 
					 -5: exists username 
					 -99: unkown error  
					 -->
			  
			</form>
		</div>
	</div>
</body>
</html>