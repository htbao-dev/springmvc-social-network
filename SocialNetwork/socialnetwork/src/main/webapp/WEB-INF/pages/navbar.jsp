<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
</head>
<body>
	<nav class="navbar fixed-top navbar-expand-lg navbar-light bg-light justify-content-between">
	  	<a class="navbar-brand" href="home">
			<img src="img/b-logo.jpg" width="30" height="30" class="d-inline-block align-top" alt="">
    		FaceBảo
		</a>
	  	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	    	<span class="navbar-toggler-icon"></span>
	  	</button>
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
	          	<a class="dropdown-item" href="#">Action</a>
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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"
	integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS"
	crossorigin="anonymous"></script>
<script src="js/Navbar.js"></script>
</body>
</html>