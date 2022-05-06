resetPassword = (function () {
    "use strict";
    let form = $('#resetPasswordForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/resetPassword/';
    }

    function checkPasswordMatchOld() {
        $('#confirmPassword').on('blur', function () {
            let confirmPassword = $(this).val();
            let newPassword = $('#newPassword').val();

            if (newPassword !== confirmPassword) {
                warningMsg("Your password doesn't match, Please type again");
                $(this).val('');
            }
        })
    }

    function resetPassword() {
        $('.btnResetNow').on('click', function () {
            if (form.valid()) {
                isSubmitted = true;
                $('.btnResetNow').attr('disabled', true);
                let data = new FormData($('.resetPasswordForm')[0]);
                $.ajax({
                    url: _baseURL() + 'resetPassword',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            $('.resetPasswordForm')[0].reset();
                            $('.resetPasswordForm').addClass('hidden');
                            $('.loginLinkDiv').removeClass('hidden');
                            $('.displaySucessMsg').text(res.text);
                            isSubmitted = false;
                        } else {
                            $('.btnResetNow').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnResetNow').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    return {
        resetPassword: resetPassword
        , checkPasswordMatchOld: checkPasswordMatchOld
    }
})();

$(document).ready(function () {
    resetPassword.resetPassword();
    resetPassword.checkPasswordMatchOld();
});