user = (function () {
    "use strict";
    let form = $('#userFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/user/';
    }

    function addUser() {
        $('.btnSaveUser').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveUser').attr('disabled', true);
                let data = new FormData($('.userFormId')[0]);
                let domainName = window.location.origin;
                data.append('domainName', domainName);
                $.ajax({
                    url: _baseURL() + 'addUser',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.userFormId')[0].reset();
                            $("#userModal").modal('hide');
                            getUsers();
                            $('.btnSaveUser').attr("disabled", false);
                            isSubmitted = false;
                        } else {
                            $('.btnSaveUser').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveUser').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function updateUser() {
        $('.btnUpdateUser').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnUpdateUser').attr('disabled', true);
                let data = new FormData($('.userFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'updateUser',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.userFormId')[0].reset();
                            $("#userModal").modal('hide');
                            getUsers();
                            isSubmitted = false;
                            $('.btnUpdateUser').attr("disabled", false);
                        } else {
                            $('.btnUpdateUser').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnUpdateUser').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function getUsers() {
        $('#userListTable').dataTable().fnDestroy();
        $('#userListTable tbody').empty();
        let hasEditRole = $('#hasEditRole').val();
        let btnEdit = '';//TODO: to be used this one after incorporation security
        // let btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#userModal"><i class="fa fa-edit"></i> Edit</button>';
        if (hasEditRole.toString() === 'true') {
            btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#userModal"><i class="fa fa-edit"></i> Edit</button>';
        }
        $.ajax({
            url: _baseURL() + 'getUsers',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {
                        class: 'align-middle',
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },
                    {data: 'userId', class: "userId hidden align-middle"},
                    {
                        data: {status: 'status', fullName: 'fullName'}, class: "fullName align-middle",
                        "mRender": function (data) {
                            let activeUser = '<i class="status-icon bg-success"></i>';
                            return data.status === 'A' ? activeUser + data.fullName
                                : '<i class="status-icon bg-danger"></i>' + data.fullName;
                        }
                    },
                    {data: 'roleName', class: "roleName align-middle"},
                    {data: 'email', class: "email align-middle"},
                    {data: 'mobileNo', class: "mobileNo align-middle"},
                    {
                        "data": "null", class: 'align-middle',
                        "mRender": function () {
                            return btnEdit;
                        }
                    }
                ];
                $('#userListTable').DataTable({
                    data: res.dto
                    , columns: columnDef
                    , pageLength: 100
                });
            }
        });
    }

    function btnEdit() {
        $('#userListTable tbody').on('click', 'tr .btnEdit', function () {
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            let row = $(this).closest('tr');
            let userId = row.find(".userId").text();
            $.ajax({
                url: _baseURL() + 'getUserByUserId',
                type: 'GET',
                data: {userId: userId},
                success: function (res) {
                    let data = res.dto;
                    $('.userFormId')[0].reset();
                    $("#userModal").modal('show');
                    $('.modal-title').empty().append('<i class="fa fa-edit"></i> Edit User');
                    $(".btnUpdateUser").removeClass("hidden");
                    $(".btnSaveUser").addClass("hidden");
                    populate(data);
                    $('#companyId').val(data.companyId).trigger("chosen:updated");
                    $("#companyMappingId").val(data.companyMappingId).trigger("chosen:updated");
                }
            });
        });
    }

    function btnNewUser() {
        $('.btnNewUser').on('click', function () {
            $('.modal-title').empty().append('<i class="fa fa-plus"></i> Add User');
            $(".btnUpdateUser").addClass("hidden");
            $(".btnSaveUser").removeClass("hidden");
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            $('.userFormId')[0].reset();
        });
    }

    function confirmPassword() {
        $('#confirmPassword').on('blur', function () {
            let confirmPassword = $(this).val();
            let password = $('#password').val();

            if (password !== confirmPassword) {
                warningMsg("Your password doesn't match. Please type again.");
                $(this).val('');
            }
        });
    }

    return {
        getUsers: getUsers,
        confirmPassword: confirmPassword,
        btnNewUser: btnNewUser,
        addUser: addUser,
        updateUser: updateUser,
        btnEdit: btnEdit
    }
})();

$(document).ready(function () {
    $('#userModal').on('shown.bs.modal', function () {
        $('.chosen-select', this).chosen('destroy').chosen();
    });
    user.getUsers();
    user.btnNewUser();
    user.confirmPassword();
    user.addUser();
    user.updateUser();
    user.btnEdit();
});