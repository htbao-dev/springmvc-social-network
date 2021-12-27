$("#btn-confirm").click(function () {
    let username = $("#username").val();
    $.ajax({
        mimeType: "multipart/form-data",
        type: "POST",
        url: "/socialnetwork/handleForgetPassword",
        datatype: "json",
        timeout: 100000,
        enctype: 'multipart/form-data',
        data: {
            username: username
        },
        success: function (data) {
            console.log(data);
            const obj = JSON.parse(data);
            if (obj.status == -1) {
                alert("Không tìm thấy username");
            } else {
                let html = `<div class="d-flex justify-content-center">Đã gửi email đến: ${obj.email} </div>`;
                $("#response-container").html(html);
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
});
