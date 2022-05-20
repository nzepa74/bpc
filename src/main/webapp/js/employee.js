employee = (function () {
    "use strict";
    let form = $('#employeeFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/employee/';
    }

    function addEmployee() {
        $('.btnSaveEmployee').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveEmployee').attr('disabled', true);

                let userInputData = new FormData($('.employeeFormId')[0]);

                $.ajax({
                    url: _baseURL() + 'addEmployee',
                    type: "POST",
                    data: userInputData,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('#employeeModal').modal('hide');
                            isSubmitted = false;
                            $('.btnSaveEmployee').attr("disabled", false);
                            getAllEmployees();
                        } else {
                            warningMsg(res.text);
                            $('.btnSaveEmployee').attr("disabled", false);
                            isSubmitted = false;
                        }
                    },
                    complete: function (res) {
                        $('.btnSaveEmployee').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function editEmployee() {
        $('.btnUpdateEmployee').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnUpdateEmployee').attr('disabled', true);

                let userInputData = new FormData($('.employeeFormId')[0]);

                $.ajax({
                    url: _baseURL() + 'editEmployee',
                    type: "POST",
                    data: userInputData,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('#employeeModal').modal('hide');
                            isSubmitted = false;
                            $('.btnUpdateEmployee').attr("disabled", false);
                            getAllEmployees();
                        } else {
                            warningMsg(res.text);
                            $('.btnUpdateEmployee').attr("disabled", false);
                            isSubmitted = false;
                        }
                    },
                    complete: function (res) {
                        $('.btnUpdateEmployee').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function getAllEmployees() {
        $('#employeeTable').dataTable().fnDestroy();
        $('#employeeTable tbody').empty();
        // let hasEditRole = $('#hasEditRole').val();
        // let btnEdit = '';//TODO: to be used this one after incorporation security
        let btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#employeeModal"><i class="fa fa-edit"></i> Edit</button>';
        // if (hasEditRole.toString() === 'true') {
        //     btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#employeeModal"><i class="fa fa-edit"></i> Edit</button>';
        // }

        $.ajax({
            url: 'api/employee/getAllEmployees', type: 'GET', success: function (res) {

                let columnDef = [{
                    class: 'align-middle', "mRender": function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                }, {data: 'employeeId', class: "employeeId align-middle"}, {
                    data: 'fullName', class: "fullName align-middle"
                }, {
                    data: 'gender', class: "gender align-middle", "mRender": function (data) {
                        let genderName = 'Female';
                        if (data === 'M') {
                            genderName = 'Male';
                        } else if (data === 'O') {
                            genderName = 'Other';
                        }
                        return genderName;
                    }
                }, {data: 'contactNo', class: "contactNo align-middle"}, {
                    "data": "null", class: 'align-middle', "mRender": function () {
                        return btnEdit;
                    }
                }];
                $('#employeeTable').DataTable({
                    data: res.dto, columns: columnDef, pageLength: 100
                });
            }
        });
    }

    function btnEdit() {
        $('#employeeTable tbody').on('click', 'tr .btnEdit', function () {

            $('.btnSaveEmployee').addClass('hidden');
            $('.btnUpdateEmployee').removeClass('hidden');
            // $(".field").removeClass("is-invalid");
            // $(".error").empty();
            let row = $(this).closest('tr');
            let employeeId = row.find(".employeeId").text();
            $.ajax({
                url: _baseURL() + 'getEmployeeInfo',
                type: 'GET',
                data: {employeeId: employeeId},
                success: function (res) {
                    let data = res.dto;
                    // $('.userFormId')[0].reset();
                    // $("#userModal").modal('show');
                    // $('.modal-title').empty().append('<i class="fa fa-edit"></i> Edit User');
                    // $(".btnUpdateUser").removeClass("hidden");
                    // $(".btnSaveUser").addClass("hidden");
                    populate(data);
                    let gender = data.gender;
                    if (gender === 'M') {
                        $('#male').prop('checked', true);
                    }
                    if (gender === 'F') {
                        $('#female').prop('checked', true);
                    }
                    if (gender === 'O') {
                        $('#other').prop('checked', true);
                    }
                    // $('#companyId').val(data.companyId).trigger("chosen:updated");
                    // $("#companyMappingId").val(data.companyMappingId).trigger("chosen:updated");
                }
            });
        });
    }

    function toggle() {
        $('.btnNewEmployee').on('click', function () {
            $('.btnSaveEmployee').removeClass('hidden');
            $('.btnUpdateEmployee').addClass('hidden');
        });
    }

    function onChangeDzongkhag() {
        $('#dzongkhagId').on('change', function () {
            let dzongkhagId = $(this).val();
            $.ajax({
                url: _baseURL() + 'getGeogListByDzoId',
                type: 'GET',
                data: {dzoId: dzongkhagId},
                success: function (res) {
                    globalLib.loadDropDown($('#geogId'), res, 'integer');
                }
            });
        });
    }

    return {
        addEmployee: addEmployee,
        getAllEmployees: getAllEmployees,
        btnEdit: btnEdit,
        toggle: toggle,
        editEmployee: editEmployee,
        onChangeDzongkhag: onChangeDzongkhag
    }
})();

$(document).ready(function () {
    $('#employeeModal').on('shown.bs.modal', function () {
        $('.chosen-select', this).chosen('destroy').chosen();
    });
    employee.addEmployee();
    employee.getAllEmployees();
    employee.btnEdit();
    employee.toggle();
    employee.editEmployee();
    employee.onChangeDzongkhag();
});