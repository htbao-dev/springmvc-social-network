function loadPost() {
	let listPostContainer = $("#list-post-container");
	let newTime = listPostContainer.data("new");
	let oldTime = listPostContainer.data("old");
	let readMore = $("#read-more-post");
	console.log(newTime)
	console.log(oldTime)
	$.ajax({

		type: "Get",
		url: "/socialnetwork/cms/loadMorePost",
		dataType: 'json',
		data: {
			"postTimeNew": newTime,
			"postTimeOld": oldTime
		},
		timeout: 100000,
		success: function (data) {
			console.log("SUCCESS: ", data);
			data.forEach((item) => {
				let html = toHtmlPostItem(item);

				readMore.before(html);
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

function toHtmlPostItem(item) {
    let content = item.postBEAN.content;
    content = content.substring(0, 20);
    content = content + "...";
    let html = `<a class="d-flex post-item list-group-item list-group-item-action" href="#" data-id="${item.postBEAN.postID}">
        <div> 
            <img width="50px" height="50px" src="../${item.author.avatarPath}" alt="avatar" class="avatar">
        </div>
        <div class="ml-3">
            <div class="post-author-name"> ${item.author.firstName} ${item.author.lastName} </div>
            <div class="post-time">${item.postBEAN.postTime}</div>
        </div>
        <div class="post-content">
            ${content}
        </div>

    </a>`;
    return html;
}

$(document).ready(function () {
    loadPost();
});

$(document).on("click", ".post-item", function () {
	$("#delete-btn").prop("disabled", false);
    let postID = $(this).data("id");
    let detailPost = $("#detail-post-container");
    console.log(postID);
    $.ajax({
        type: "Get",
        url: "/socialnetwork/cms/getPostDetail",
        dataType: 'json',
        data: {
            "postID": postID
        },
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            let html = toHtmlPostDetail(data);
            detailPost.empty();
            detailPost.append(html);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        }
    });
});

$(document).on("click", "#delete-btn", function () {
	let postID = $(".post-container").data("id");
	console.log(postID);
	$.ajax({
		type: "Post",
		url: "/socialnetwork/cms/deletePost",
		dataType: 'json',
		data: {
			"postID": postID
		},
		timeout: 100000,
		success: function (data) {
			console.log("SUCCESS: ", data);
			$("#detail-post-container").empty();
			$("#delete-btn").prop("disabled", true);

			let post = $("[data-id='" + postID + "']");
			post.remove();

			
		},
		error: function (e) {
			console.log("ERROR: ", e);
		}
	});
});

$(document).on("click", ".read-more-comment", function () {
	let postID = $(".post-container").data("id");
	let page = $(this).data("page");
	console.log(page);
	let displayComment = $(".display-comment-content");
	$.ajax({
		type: "Get",
		url: "/socialnetwork/cms/loadMoreComment",
		dataType: 'json',
		data: {
			"postID": postID,
			"pageNumber": page +1
		},
		timeout: 100000,
		success: function (data) {
			console.log("SUCCESS: ", data);
			if(data.canGet == "false"){
				$(".read-more-comment").remove();
			}
			else{
				$(".read-more-comment").data("page", page + 1);
			}
			let html = toHtmlComment(data.listComment[0]);
			displayComment.append(html);
		},
		error: function (e) {
			console.log("ERROR: ", e);
		}
	});
});

$(document).on('click', '.btn-xoa-cmt', function () {
	let comment = $(this).parent().parent();
	let commentID = comment.data("cmtid");
	let postID = $(".post-container ").data("id");
	console.log(commentID);
	$.ajax({
		type: "Post",
		url: "/socialnetwork/cms/deleteComment",
		dataType: 'json',
		data: {
			"commentID": commentID,
			"postID": postID
		},
		timeout: 100000,
		success: function (data) {
			console.log("SUCCESS: ", data);
				comment.remove();
		},
		error: function (e) {
			console.log("ERROR: ", e);

		}
	});
});

function toHtmlPostDetail(item) {
	let html = '<div class="post-container  m-2"' +
		'data-id="' + item.postBEAN.postID + '">' +
		'<div class="d-flex">' +
		'<img class="avatar postUserAvatar " alt=""' +
		'src="../' + item.author.avatarPath + '">' +
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
		'<div class="d-flex post-button-container">'+ 
		'</div>' +
		'<div class="comment-container">' +
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

function toHtmlImg(item) {
	let html = '';
	let htmlImg = '';
	if (item.postBEAN.postImg.length > 0) {
		let carousel = '';
		let indicators = '';
		for (let i = 0; i < item.postBEAN.postImg.length; i++) {
			if (i == 0) {
				carousel += '<div class="carousel-item active w-100">' +
					'<img class="d-block w-100 h-100" src="../' + item.postBEAN.postImg[i] + '" alt="">' +
					'</div>';
			}
			else {
			carousel += '<div class="carousel-item w-100">' +
				'<img class="d-block w-100 h-100" src="../' + item.postBEAN.postImg[i] + '" alt="">' +
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


function toHtmlComment(listDemoComment) {
	let html = '';
	listDemoComment.forEach((item) => {
		html += `<div class="comment d-flex py-3" data-cmtID="${item.commentID}">`;
		html += '<img class="avatar comment-avatar" src="../' + item.author.avatarPath + '">' +
			'<div class="comment-name-content w-100 ml-2">' +
			'<div class="comment-name">' +
			item.author.firstName + ' ' + item.author.lastName +
			'</div>' +
			'<div class="comment-content">' +
			item.content +
			'</div>' +
			'</div>' +
			`<div class="d-flex align-items-center ml-2">
				<button class="btn btn-danger btn-xoa-cmt">
					Xoá
				</button> 
			</div>` +
			'</div>';

	})
	return html;
}

$(document).on('click', '#read-more-post', function () {
	loadPost();
});

