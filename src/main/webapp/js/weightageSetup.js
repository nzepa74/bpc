weightageSetup = (function () {
    "use strict";
    let form = $('#weightageFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/weightageSetup/';
    }

    function addWeightage() {
        $('.btnSaveWeightage').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveWeightage').attr('disabled', true);
                let data = new FormData($('.weightageFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addWeightage',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.weightageFormId')[0].reset();
                            $("#weightageModal").modal('hide');
                            $('.btnSaveWeightage').attr('disabled', false);
                            let year = $('#filterYear').val();
                            getWeightage(year);
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnSaveWeightage').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnSaveWeightage').attr('disabled', false);
                    }
                });
            }
        });
    }

    function updateWeightage() {
        $('.btnUpdateWeightage').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                let data = new FormData($('.weightageFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'updateWeightage',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.weightageFormId')[0].reset();
                            $("#weightageModal").modal('hide');
                            $('.btnUpdateWeightage').attr('disabled', false);
                            let year = $('#filterYear').val();
                            getWeightage(year);
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnUpdateWeightage').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnUpdateWeightage').attr('disabled', false);
                    }
                });
            }
        });
    }

    function getWeightage(year) {
        $('#weightageTable').dataTable().fnDestroy();
        $('#weightageTable tbody').empty();
        let hasEditRole = $('#hasEditRole').val();
        // let btnEdit = '';//TODO: to be used this one after incorporation security
        let btnEdit = '<button id="btnEdit" class="btn btn-s square btn-info btnEdit" data-bs-toggle="modal" data-bs-target="#weightageModal"><i class="fa fa-edit"></i> Edit</button>';
        if (hasEditRole.toString() === 'true') {
            btnEdit = '<button id="btnEdit" class="btn btn-s square btn-info btnEdit" data-bs-toggle="modal" data-bs-target="#weightageModal"><i class="fa fa-edit"></i> Edit</button>';
        }
        let url = _baseURL() + 'getWeightage';
        $.ajax({
            url: url,
            type: 'GET',
            data: {year: year},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let dataTableDefinition = [
                        {
                            class: 'align-middle',
                            "mRender": function (data, type, row, meta) {
                                return meta.row + 1;
                            }
                        },
                        {"data": "weightageSetupId", class: "weightageSetupId hidden"}
                        , {"data": "year", class: "year align-middle"}
                        , {"data": "companyName", class: "companyName align-middle"}
                        , {"data": "financialWt", class: "financialWt align-middle"}
                        , {"data": "customerWt", class: "customerWt align-middle"}
                        , {"data": "productionWt", class: "productionWt align-middle"}
                        , {"data": "orgManagementWt", class: "orgManagementWt align-middle"}
                        , {
                            "data": "null", class: 'align-middle',
                            "mRender": function () {
                                return btnEdit;
                            }
                        }
                    ];
                    $('#weightageTable').DataTable({
                        data: data
                        , columns: dataTableDefinition
                        , pageLength: 100
                    });
                }
            }
        });
    }

    function btnEdit() {
        $('#weightageTable tbody').on('click', 'tr #btnEdit', function () {
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            let row = $(this).closest('tr');
            let weightageSetupId = row.find(".weightageSetupId").text();
            $.ajax({
                url: _baseURL() + 'getWeightByWeightageSetupId',
                type: 'GET',
                data: {weightageSetupId: weightageSetupId},
                success: function (res) {
                    let data = res.dto;
                    $('.weightageFormId')[0].reset();
                    $("#weightageModal").modal('show');
                    $('.modal-title').empty().append('<i class="fa fa-edit"></i> Edit Weightage');
                    $(".btnUpdateWeightage").removeClass("hidden");
                    $(".btnSaveWeightage").addClass("hidden");
                    populate(data);
                    $('#companyId').prop('disabled', true);
                    $('#year').prop('disabled', true);
                }
            });
        });
    }

    function onChangeFilterYear() {
        $('#filterYear').on('change', function () {
            let year = $(this).val();
            getWeightage(year);
        });
    }

    function btnAddNew() {
        $('.btnNewWeightage').on('click', function () {
            $('.modal-title').empty().append('<i class="fa fa-plus"></i> Add Weightage');
            $(".btnUpdateWeightage").addClass("hidden");
            $(".btnSaveWeightage").removeClass("hidden");
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            $('.weightageFormId')[0].reset();
            $('#companyId').prop('disabled', false);
            $('#year').prop('disabled', false);
        });
    }

    return {
        getWeightage: getWeightage
        , addWeightage: addWeightage
        , updateWeightage: updateWeightage
        , btnAddNew: btnAddNew
        , btnEdit: btnEdit
        , onChangeFilterYear: onChangeFilterYear
    }
})
();
$(document).ready(
    function () {
        $('#weightageModal').on('shown.bs.modal', function () {
            $('.chosen-select', this).chosen('destroy').chosen();
        });
        let year = $('#filterYear').val();
        weightageSetup.getWeightage(year);
        weightageSetup.addWeightage();
        weightageSetup.updateWeightage();
        weightageSetup.btnAddNew();
        weightageSetup.btnEdit();
        weightageSetup.onChangeFilterYear();
    });
