<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Social Network</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
<link rel="stylesheet" href="css/style.css">
</head>
<body class="background text-white">
<!-- Modal -->
	<div class="modal fade" id="newPostModal" tabindex="-1"
		role="dialog" aria-labelledby="newPostModalLabel" 
		aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content bg-dark">
				<div class="modal-header border-0">
					<div class="d-flex justify-content-center w-100">
						<h5 class="modal-title text-white" id="newPostModalLabel">Bài
							viết mới</h5>
					</div>
					<button type="button" class="close text-white"
						data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-content border-0 bg-dark">
					<div>
						<textarea id="new-post-content" rows="7" class="border-0 w-100 bg-dark text-white"
							placeholder="Viết gì đó ở đây ..." style="resize: none;"> </textarea>
						<label for=new-post-image>Đính kèm ảnh 
						<input id="new-post-image" class="btn btn-file bg-dark text-white" 
								type="file" accept="image/*" multiple="multiple" />
						</label>
					</div>
				</div>
				<div class="modal-footer border-0">
					<button id="btn-close-modal" type="button" class="btn btn-secondary"
						data-dismiss="modal">Thôi</button>
					<button id="btn-new-post" class="btn btn-primary">Đăng</button>
				</div>
			</div>
		</div>
	</div>
	<div>
		<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light justify-content-between">
			<a class="navbar-brand" href="home">
			  <img src="img/b-logo.jpg" width="30" height="30" class="d-inline-block align-top" alt="">
			  FaceBảo
		  	</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
			  <span class="navbar-toggler-icon"></span>
			</button>
			<div class="d-flex w-50">
				<input id="key-search" class="form-control mr-sm-2 w-75" type="search" placeholder="Tìm kiếm" aria-label="Search">
				<button id="btn-search" class="btn btn-warning my-2 my-sm-0">Tìm kiếm</button>
			</div>
		<div>
		  <ul class="navbar-nav mr-auto">
			<li class="nav-item active mr-4">
			  <a class="nav-link" href="#">
				  <img alt="Avatar" class="avatar " src="${sessionScope.userInfo.getAvatarPath() }" width="30" height="30">
				  ${sessionScope.userInfo.getLastName() }
			  </a>
			</li>
			<li class="nav-item dropdown">
			  <a class="nav-link" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				  <img alt="" src="img/dropdown-icon.svg" width="15">
			  </a>
			  <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
					<a class="dropdown-item" href="#" data-toggle="modal" data-target="#changeAvatarModal">Thay đổi ảnh đại diện</a>
					<div class="dropdown-divider"></div>
				<a class="dropdown-item" href="logout">Đăng xuất</a>
			  </div>
			</li>
		  </ul>
		</div>
	  </nav>
	  
  
  
	  <!-- Modal -->
	  <div class="modal fade" id="changeAvatarModal" tabindex="-1" role="dialog" aria-labelledby="changeAvatarModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
			  <div class="modal-content">
			  <div class="modal-header">
				  <h5 class="modal-title text-dark" id="changeAvatarModalLabel">Modal title</h5>
				  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
				  <span aria-hidden="true">&times;</span>
				  </button>
			  </div>
			  <div class="modal-body">
				  <form id="change-avatar-form" action="/socialnetwork/changeAvatar" method="post" enctype="multipart/form-data">
					  <div class=" d-flex justify-content-center">
						  <img id="preview-avatar" src="img/default-avatar.png" alt="your image" width="300px" height="300px"/>
					  </div>
					  <input name="file" accept="image/*" class="btn" type='file' id="input-avatar" required="required"/>
				  </form>
			  </div>
			  <div class="modal-footer">
				  <button type="button" class="btn btn-secondary" data-dismiss="modal">Thôi</button>
				  <button form="change-avatar-form" type="submit" class="btn btn-primary">Lưu</button>
			  </div>
			  </div>
		  </div>
	  </div>
  
	</div>
	<div class="d-flex justify-content-center mt-5">
		<div class="w-50 mt-5 d-flex justify-content-center">
			<div class="w-75">
				<div class="d-flex" id="#show-modal-new-post-container">
					<img alt="" src="${sessionScope.userInfo.getAvatarPath() }"
						width="40px" class="avatar" height="40px">
					<!-- Button trigger modal -->
					<button id="btn-show-modal-new-post" type="button" class="w-100 ml-3" data-toggle="modal"
						data-target="#newPostModal">Bài viết mới nào...</button>
				</div>
				<div id="list-post-container" data-new="-1" data-old="-1">
					
				</div>
				<div class="d-flex justify-content-center">
					<span id="read-more-post"> Xem thêm tin</span>
				</div>
					
			</div>
		</div>
	</div>
	

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
		integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
		crossorigin="anonymous"></script>
	<script src="js/App.js"></script>
	<script src="js/Navbar.js"></script>
</body>
</html>