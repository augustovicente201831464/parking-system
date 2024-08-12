function loadDynamicJSP(url) {
    $.ajax({
        url: url,
        method: 'GET',
        success: function (data) {
            $('#dynamic-content').html(data);
        },
        error: function (xhr, status, error) {
            console.error('Error loading JSP content:', error);
        }
    });
}