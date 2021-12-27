$(document).on('click', '#btn-confirm', function () {
    let password = $("#password").val();
    let confirmPassword = $("#confirmPassword").val();
    let userID = $("#userID").val();
    $.ajax({
        mimeType: "multipart/form-data",
        type: "POST",
        url: "/socialnetwork/handleChangePassword",
        datatype: "json",
        timeout: 100000,
        enctype: 'multipart/form-data',
        data: {
            password: password,
            confirmPassword: confirmPassword,
            userID: userID
        },
        success: function (data) {
            console.log(data);
            const obj = JSON.parse(data);
            if (obj.status == -1) {
                alert("Mật khẩu không hợp lệ");
            }else if(obj.status == -2){
                alert("Mật khẩu không trùng khớp");
            } else {
                let html = `<div class="d-flex justify-content-center">Đã thay đổi mật khẩu thành công</div>`;
                $("#response-container").html(html);
                $("#password").val('');
                $("#confirmPassword").val('');
            }
        },
        error: function (data) {
            console.log(data);
        }
    });
});