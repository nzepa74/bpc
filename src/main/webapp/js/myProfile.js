myProfile = (function () {
    "use strict";
    let pwChangeFormId = $('#pwChangeFormId');
    let fullNameForm = $('#fullNameForm');
    let usernameForm = $('#usernameForm');
    let emailForm = $('#emailForm');
    let mobileNoForm = $('#mobileNoForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/myProfile/';
    }

    function confirmPassword() {
        $('#confirmPassword').on('blur', function () {
            let confirmPassword = $(this).val();
            let password = $('#newPassword').val();
            if (password !== confirmPassword) {
                warningMsg("Your password doesn't match. Please type again.");
                $(this).val('');
            }
        });
    }

    function changePassword() {
        $('.btnChangePassword').on('click', function () {
            globalLib.isFormValid(pwChangeFormId);
            if (pwChangeFormId.valid()) {
                isSubmitted = true;
                $('.btnChangePassword').attr('disabled', true);
                let data = new FormData($('.pwChangeFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'changePassword',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.pwChangeFormId')[0].reset();
                            isSubmitted = false;
                            $('.btnChangePassword').attr('disabled', false);
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnChangePassword').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnChangePassword').attr('disabled', false);
                    }
                });
            }
        });
    }

    function getMyDetail() {
        $.ajax({
            url: _baseURL() + 'getMyDetail',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    populate(data);
                    let fullName = data.fullName;
                    let username = data.username;
                    let email = data.email;
                    let mobileNo = data.mobileNo;
                    $('.fullNameDisplay').empty().text(fullName);
                    $('.usernameDisplay').empty().text(username);
                    $('.emailDisplay').empty().text(email);
                    $('.mobileNoDisplay').empty().text(mobileNo);
                }
            }
        });
    }

    function getMyCompany() {
        $.ajax({
            url: _baseURL() + 'getMyCompany',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    $('.myCompany').text(res.dto.companyName);
                }
            }
        });
    }

    function getMappedCompanies() {
        $.ajax({
            url: _baseURL() + 'getMappedCompanies',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    for (let i = 1; i < data.length; i++) {
                        let companyName = data[i].companyName;
                        companyName = '<i class="fa fa-check-circle-o successMsg"></i> ' + companyName;
                        let companyLi = '<li>' + companyName + '</li>';
                        $('.companyUl').append(companyLi);
                    }
                }
            }
        });
    }

    function updateDetail() {
        $('.btnEditFullName').on('click', function () {
            globalLib.isFormValid(fullNameForm);
            if (fullNameForm.valid()) {
                isSubmitted = true;
                $('.btnEditFullName').attr('disabled', true);
                let data = new FormData($('.fullNameForm')[0]);
                $.ajax({
                    url: _baseURL() + 'editFullName',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.btnEditFullName').attr('disabled', false);
                            getMyDetail();
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnEditFullName').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnEditFullName').attr('disabled', false);
                    }
                });
            }
        });

        $('.btnEditUsername').on('click', function () {
            globalLib.isFormValid(usernameForm);
            if (usernameForm.valid()) {
                isSubmitted = true;
                $('.btnEditUsername').attr('disabled', true);
                let data = new FormData($('.usernameForm')[0]);
                $.ajax({
                    url: _baseURL() + 'editUsername',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.btnEditUsername').attr('disabled', false);
                            getMyDetail();
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnEditUsername').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnEditUsername').attr('disabled', false);
                    }
                });
            }
        });

        $('.btnEditEmail').on('click', function () {
            globalLib.isFormValid(emailForm);
            if (emailForm.valid()) {
                isSubmitted = true;
                $('.btnEditEmail').attr('disabled', true);
                let data = new FormData($('.emailForm')[0]);
                $.ajax({
                    url: _baseURL() + 'editEmail',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.btnEditEmail').attr('disabled', false);
                            getMyDetail();
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnEditEmail').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnEditEmail').attr('disabled', false);
                    }
                });
            }
        });

        $('.btnEditMobileNo').on('click', function () {
            globalLib.isFormValid(mobileNoForm);
            if (mobileNoForm.valid()) {
                isSubmitted = true;
                $('.btnEditMobileNo').attr('disabled', true);
                let data = new FormData($('.mobileNoForm')[0]);
                $.ajax({
                    url: _baseURL() + 'editMobileNo',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.btnEditMobileNo').attr('disabled', false);
                            getMyDetail();
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnEditMobileNo').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnEditMobileNo').attr('disabled', false);
                    }
                });
            }
        });
    }

    return {
        confirmPassword: confirmPassword
        , changePassword: changePassword
        , getMyDetail: getMyDetail
        , getMyCompany: getMyCompany
        , getMappedCompanies: getMappedCompanies
        , updateDetail: updateDetail
    }
})
();
$(document).ready(
    function () {
        myProfile.confirmPassword();
        myProfile.changePassword();
        myProfile.getMyDetail();
        myProfile.getMyCompany();
        myProfile.getMappedCompanies();
        myProfile.updateDetail();
    });
