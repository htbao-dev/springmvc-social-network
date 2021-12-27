<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Quản lý mạng xã hội</title>
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
<link rel="stylesheet" href="../css/style.css">
<link rel="stylesheet" href="../css/cmsHome.css">
</head>
<body class="bg-dark text-white">
		<div>
			<c:import url="navbar_cms.jsp"></c:import>
		</div>
		
	<div class="page-content-container">
		<div class="d-flex justify-content-end">
			<button id="delete-btn" class="btn btn-danger" disabled>Xoá</button>
		</div>
		<div id="home-container" class="d-flex">
			<div class="col-4 list-group" id="list-post-container" data-new="-1" data-old="-1">
				<!-- <a class="d-flex" href="#">
					<div> 
						<img width="50px" height="50px" src="../img/default-avatar.png" alt="avatar" class="avatar">
					</div>
					<div class="ml-3">
						Huỳnh Bảo
						<div>17-12-2021</div>
					</div>
	
				</a> -->
				<div id="read-more-post" class="d-flex justify-content-center">
					<a href="#"> Xem thêm </a>
				</div>
			</div>
			<div class="col" id="detail-post-container">
			</div>
		</div>

	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
		integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
		crossorigin="anonymous"></script>
	<script src="../js/cmsHome.js"></script>
</body>
</html>