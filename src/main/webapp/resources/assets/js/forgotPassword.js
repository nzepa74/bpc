forgotPassword = (function () {
    "use strict";
    let form = $('#forgotPasswordForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/resetPassword/';
    }

    function requestPasswordChange() {
        $('.btnRequestPwReset').on('click', function () {
            if (form.valid()) {
                isSubmitted = true;
                $('.btnRequestPwReset').attr('disabled', true);
                let data = new FormData($('.forgotPasswordForm')[0]);
                let domainName = window.location.origin;
                data.append('domainName', domainName);
                $.ajax({
                    url: _baseURL() + 'requestPasswordChange',
                    type: 'POST',
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            $('.btnRequestPwReset').attr('disabled', false);
                            $('.successMsgDiv').removeClass('hidden');
                            $('.errorMsgDiv').addClass('hidden');
                            $('.displaySucessMsg').text(res.text);
                        } else {
                            $('.btnRequestPwReset').attr('disabled', false);
                            $('.successMsgDiv').addClass('hidden');
                            $('.errorMsgDiv').removeClass('hidden');
                            $('.displayErrorMsg').text(res.text);
                        }
                    }
                });
            }
        });
    }


    return {
        requestPasswordChange: requestPasswordChange
    }

})();

$(document).ready(function () {
    forgotPassword.requestPasswordChange();
});