
$(document).on("keyup", ".cmt-input",
	function (event) {
		if (event.which === 13) {
			let postID = $(this).parent().parent().parent().data("id");
			let cmtContent = $(this).val();
			$(this).val("");
			console.log(cmtContent);
			console.log(postID);
			var form = new FormData();

			form.append("postID", postID);
			form.append("cmtContent", cmtContent);
			var displayComment = $(this).parent().parent().children(".display-comment").children(".display-comment-content");
			var postComment = $(this).parent().parent().parent().children(".post-like-comment").children(".post-comment");
			$.ajax({
				mimeType: "multipart/form-data",
				data: form,
				type: "POST",
				url: "/socialnetwork/addNewComment",
				dataType: 'json',
				timeout: 100000,
				enctype: 'multipart/form-data',
				processData: false,
				contentType: false,
				success: function (data) {
					console.log("SUCCESS: ", data);
					var result = '<div class="comment d-flex py-3">' +
						'<img class="avatar comment-avatar" alt="" src="' + data.avatarPath + '">' +
						'<div class="comment-name-content w-100 ml-2">' +
						'<div class="comment-name">' +
						data.firstName + ' ' + data.lastName +
						'</div>' +
						'<div class="comment-content">' +
						cmtContent +
						'</div>' +
						'</div>' +
						'</div>';
					displayComment.prepend(result);
					postComment.html(data.countComment + " Bình luận");
				},
				error: function (e) {
					console.log("ERROR: ", e);
				},

			});
		}
	});
$(document).on("click", ".btn-comment",
	function () {
		$(this).parent().parent().children(".comment-container").toggle();
	})
$(document).on("click", ".btn-unlike",
	function () {
		let postID = $(this).parent().parent().data("id");
		var form = new FormData();

		form.append("postID", postID);
		var res = '<button class="btn-like col"><div>Thích</div></button>';
		var btn = $(this);
		var postLike = $(this).parent().parent().children(".post-like-comment").children(".post-like");
		$.ajax({
			mimeType: "multipart/form-data",
			data: form,
			type: "POST",
			url: "/socialnetwork/deleteReaction",
			dataType: 'json',
			timeout: 100000,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			success: function (data) {
				console.log("SUCCESS: ", data);
				if (data.status == 0) {
					btn.replaceWith(res);
					postLike.html(data.countReaction + ' like');
				}

			},
			error: function (e) {
				console.log("ERROR: ", e);
			},

		});
	})
$(document).on("click", ".btn-like",
	function () {
		let postID = $(this).parent().parent().data("id");
		let reactionID = 1;
		console.log(postID);
		var form = new FormData();

		form.append("postID", postID);
		form.append("reactionID", reactionID);
		var btn = $(this);
		var res = '<button class="btn-unlike col"><div>Thích</div></button>';
		var postLike = $(this).parent().parent().children(".post-like-comment").children(".post-like");
		$.ajax({
			mimeType: "multipart/form-data",
			data: form,
			type: "POST",
			url: "/socialnetwork/addReaction",
			dataType: 'json',
			timeout: 100000,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			success: function (data) {
				console.log("SUCCESS: ", data.status);
				if (data.status == 0) {
					btn.replaceWith(res);
					postLike.html(data.countReaction + ' like');
				}

			},
			error: function (e) {
				console.log("ERROR: ", e);
			},

		});
	})


$(document).on("click", ".read-more-comment",
	function () {
		let postID = $(this).parent().parent().parent().parent().data("id");
		let page = $(this).data("page");
		let displayCommentContent = $(this).parent().parent().children(".display-comment-content");
		let readMore = $(this);
		console.log(postID)
		console.log(page)
		$.ajax({
			type: "Get",
			url: "/socialnetwork/loadComment",
			dataType: 'json',
			data: {
				"postID": postID,
				"pageNumber": page + 1
			},
			timeout: 100000,
			success: function (data) {
				console.log("SUCCESS: ", data);
				if (data.canGet == "false") {
					readMore.hide();
				}
				else {
					readMore.data("page", page + 1)
					//readMore.prop("data-page", page +1)
				}
				data.listComment[0].forEach((item, index) => {
					var result = '<div class="comment d-flex py-3">' +
						'<img class="avatar comment-avatar" alt="" src="' + item.author.avatarPath + '">' +
						'<div class="comment-name-content w-100 ml-2">' +
						'<div class="comment-name">' +
						item.author.firstName + ' ' + item.author.lastName +
						'</div>' +
						'<div class="comment-content">' +
						item.content +
						'</div>' +
						'</div>' +
						'</div>';
					console.log(displayCommentContent);
					displayCommentContent.append(result);
				})


			},
			error: function (e) {
				console.log("ERROR: ", e);
			},

		});
	})


$(document).on("click", "#read-more-post", loadPost);
$(document).on("click", "#btn-search", function () {
		let listPostContainer = $("#list-post-container");
		listPostContainer.html("");
		listPostContainer.data("new", -1);
		listPostContainer.data("old", -1);
		loadPost();
	})

function loadPost() {
	let listPostContainer = $("#list-post-container");
	let newTime = listPostContainer.data("new");
	let oldTime = listPostContainer.data("old");
	let keySearch = $("#key-search").val();

	keySearch = (keySearch ==null) ? "" : keySearch;

	console.log(newTime)
	console.log(oldTime)
	console.log(keySearch)
	$.ajax({

		type: "Get",
		url: "/socialnetwork/loadMorePost",
		dataType: 'json',
		data: {
			"postTimeNew": newTime,
			"postTimeOld": oldTime,
			"keySearch": keySearch
		},
		timeout: 100000,
		success: function (data) {
			console.log("SUCCESS: ", data);
			data.forEach((item) => {
				let html = toHtmlPostContainer(item);

				listPostContainer.append(html);
			})
			if (newTime < data[0].postBEAN.postID || newTime == -1) {
				listPostContainer.data("new", data[0].postBEAN.postID);
			}
			if (oldTime > data[data.length - 1].postBEAN.postID || oldTime == -1) {
				listPostContainer.data("old", data[data.length - 1].postBEAN.postID)
			}
		},
		error: function (e) {
			console.log("ERROR: ", e);
		},

	});
}

function toHtmlComment(listDemoComment) {
	let html = '';
	listDemoComment.forEach((item) => {
		html += '<div class="comment d-flex py-3">';
		html += '<img class="avatar comment-avatar" src="' + item.author.avatarPath + '">' +
			'<div class="comment-name-content w-100 ml-2">' +
			'<div class="comment-name">' +
			item.author.firstName + ' ' + item.author.lastName +
			'</div>' +
			'<div class="comment-content">' +
			item.content +
			'</div>' +
			'</div>' +
			'</div>';

	})
	return html;
}

function toHtmlImg(item) {
	let html = '';
	let htmlImg = '';
	if (item.postBEAN.postImg.length > 0) {
		let carousel = '';
		let indicators = '';
		for (let i = 0; i < item.postBEAN.postImg.length; i++) {
			if (i == 0) {
				carousel += '<div class="carousel-item active w-100">' +
					'<img class="d-block w-100 h-100" src="' + item.postBEAN.postImg[i] + '" alt="">' +
					'</div>';
			}
			else {
			carousel += '<div class="carousel-item w-100">' +
				'<img class="d-block w-100 h-100" src="' + item.postBEAN.postImg[i] + '" alt="">' +
				'</div>';
			}

			indicators += '<li data-target="#carouselImage' + item.postBEAN.postID + '" data-slide-to="' + i + '" class="' + (i == 0 ? 'active' : '') + '"></li>';
		}

		htmlImg = 
		`<div class="d-flex justify-content-center">
			<div id="carouselImage${item.postBEAN.postID}" class="carousel slide" data-ride="carousel">
				<ol class="carousel-indicators">
					${indicators}
				</ol>
				<div class="carousel-inner">
					${carousel}
				</div>
				<a class="carousel-control-prev" href="#carouselImage${item.postBEAN.postID}" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
				</a>
				<a class="carousel-control-next" href="#carouselImage${item.postBEAN.postID}" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
				</a>
			</div>
		</div>`;
	}
	return htmlImg;
}

function toHtmlButtonReaction(reaction) {
	let html = '';
	if (reaction == true) {
		html += '<button class=" btn-unlike col"><div>Thích</div></button>';
	}
	else {
		html += '<button class=" btn-like col"><div>Thích</div></button>';
	}
	html += '<button class=" btn-comment col">Bình luận</button>';
	/*			(item.reaction)?
						'<button class="btn btn-primary btn-unlike col"><div>Thích</div></button>':
							'<button class="btn btn-light btn-like col"><div>Thích</div></button>' + */
	return html;
}


function toHtmlPostContainer(item) {
	let html = '<div class="post-container my-5 "' +
		'data-id="' + item.postBEAN.postID + '">' +
		'<div class="d-flex">' +
		'<img class="avatar postUserAvatar" alt=""' +
		'src="' + item.author.avatarPath + '">' +
		'<div class="pl-2">' +
		'<div class="postUserName">' +
		item.author.firstName + ' ' +
		item.author.lastName + '</div>' +
		'<div class="text-secondary post-time">' +
		item.postBEAN.postTime + '</div>' +
		'</div>' +
		'</div>' +
		'<div class="text-white mt-3">' +
		item.postBEAN.content + '</div>' +
		toHtmlImg(item) +

		'<div class="post-like-comment d-flex justify-content-between">' +
		'<div class="post-like">' + item.countReaction + ' Like</div>' +
		'<div class="post-comment">' + item.countComment + ' Bình luận</div>' +
		'</div>' +
		'<div class="d-flex post-button-container">' +
		toHtmlButtonReaction(item.reaction) +
		'</div>' +
		'<div class="comment-container">' +
		'<div class="d-flex my-3">' +
		'<input type="text" class="w-100 btn-cmt cmt-input" placeholder="Hãy viết gì đó">' +
		'</div>' +
		'<div class="display-comment">' +
		'<div class="display-comment-content">' +
		toHtmlComment(item.listDemoComment) +
		'</div>' +
		'<div>' +
		'<span class="read-more-comment" data-page="1">Xem thêm bình luận</span>' +
		'</div>' +
		'</div>' +
		'</div>' +
		'</div>';

	return html;
}

$(document).on("click", "#btn-new-post",
	function (e) {
		e.preventDefault();
		let formData = new FormData();
		let images = $('#new-post-image')[0].files;
		for (let i = 0; i < images.length; i++) {
			formData.append("images", images[i]);
		}
		formData.append("content", $("#new-post-content").val());
		$.ajax({
			url: '/socialnetwork/newPost',
			mimeType: "multipart/form-data",
			data: formData,
			type: "POST",
			dataType: 'json',
			timeout: 100000,
			enctype: 'multipart/form-data',
			processData: false,
			contentType: false,
			success: function (data) {
				console.log("SUCCESS: ", data);
				let listPostContainer = $('#list-post-container');
				let html = toHtmlPostContainer(data);
				listPostContainer.prepend(html);
				$("#new-post-content").val('');
				$('#new-post-image').val('')
				$('#btn-close-modal').click()
				$('#btn-close-modal').click()
				$('#list-post-container').data('new', data.postBEAN.postID);
			},
			error: function (e) {
				console.log("ERROR: ", e);
			},

		});
	})

$(document).ready(function () {
		$(".comment-container").toggle();
		loadPost();
	})
