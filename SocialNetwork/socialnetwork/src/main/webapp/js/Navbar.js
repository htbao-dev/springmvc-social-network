$('#input-avatar').change(function(e) {
    const [file] = $('#input-avatar')[0].files
    if (file) {
        $('#preview-avatar').prop('src', URL.createObjectURL(file))
    }
})