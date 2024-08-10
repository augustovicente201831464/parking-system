$(document).ready(function () {
    if (errorMessage) {
        toastr.error(errorMessage, 'Error', {
            timeOut: 5000, // 5 seconds
            extendedTimeOut: 5000,
            closeButton: true,
            progressBar: true,
            positionClass: 'toast-top-center'
        });
    }
});

$(document).ready(function () {
    if (successMessage) {
        toastr.success(successMessage, 'Éxito', {
            timeOut: 5000, // 5 seconds
            extendedTimeOut: 5000,
            closeButton: true,
            progressBar: true,
            positionClass: 'toast-top-center'
        });
    }
});
