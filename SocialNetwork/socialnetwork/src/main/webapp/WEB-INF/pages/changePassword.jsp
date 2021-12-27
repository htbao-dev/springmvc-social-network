<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Quên mật khẩu</title>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
	integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
	crossorigin="anonymous"></script>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
	integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
	integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
	crossorigin="anonymous"></script>
</head>
<body>
	<div class="d-flex justify-content-center">
		<div class="mt-5">
			<div class="d-flex justify-content-center my-2">
				Xin chào ${userInfo.getFirstName() } ${userInfo.getLastName() }
			</div>
			<div class="d-flex justify-content-center my-2">
				<div>
					<form id="form-change-password" action="handleChangePassword" method="post">
						<div class="form-group">
							<label for="#password"> Mật khẩu mới</label>
							<input name="password" id="password" class="input-control d-block" type="password" required="required">
						</div>
						<div class="form-group">
							<label for="#confirmPassword">Nhập lại mật khẩu</label>
							<input name="confirmPassword" id="confirmPassword" class="input-control d-block" type="password" required="required">
						</div>
						<input type="hidden" id="userID" name="userID" value="${userInfo.getUserID() }">
					</form>
				</div>
			</div>
			<div class="d-flex justify-content-center my-2">
				<button id="btn-confirm" class="btn btn-success"> Xác nhận</button>
			</div>
		</div>
	</div>
	<div id="response-container">
		<c:if test="${status == 1 }">
			<div class="d-flex justify-content-center">Đã gửi email đến: ${email} </div>
		</c:if>
	</div>
	<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<script src="js/ChangePassword.js"></script>
</body>
</html>