<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Social Network</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/homeStyle.css">
</head>
<body class="bg-dark text-white">
	<div>
		<c:import url="navbar.jsp"></c:import>
	</div>
	<div class="d-flex justify-content-center mt-3">
		<div class="w-50 d-flex justify-content-center">
			<div class="w-75">
				<div class="d-flex">
					<img class="avatar postUserAvatar" alt="" src="${postUserInfo.getAvatarPath() }">
					<div>
						<div class="postUserName text-white d-flex align-items-center ml-2">
							${postUserInfo.getFirstName()} ${postUserInfo.getLastName()}
						</div>
						<div>
							${postInfo.getPostTime() }
						</div>
					</div>
				</div>
				<div class="text-white mt-3">
					${postInfo.getContent() }
				</div>
				<div>
					<c:choose>
						<c:when test="${postInfo.getPostImg().size() != 0 }">
							<img alt="" src="${postInfo.getPostImg().get(0) }" width="500px">
						</c:when>
					</c:choose>
				</div>
			</div>
		</div>
	</div>
</body>
</html>