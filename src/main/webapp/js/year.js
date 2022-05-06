year = (function () {
    "use strict";
    let form = $('#yearFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/year/';
    }

    /**
     * method to add year
     */
    function addYear() {
        $('.btnSaveYear').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                let data = new FormData($('.yearFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addYear',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.yearFormId')[0].reset();
                            $("#yearModal").modal('hide');
                            $('.btnSaveYear').attr('disabled', false);
                            getYears();
                            isSubmitted = false;
                        } else {
                            warningMsg(res.text);
                            isSubmitted = false;
                            $('.btnSaveYear').attr('disabled', false);
                        }
                    }, complete: function (res) {
                        isSubmitted = false;
                        $('.btnSaveYear').attr('disabled', false);
                    }
                });
            }
        });
    }

    /**
     * method to edit year
     */
    function updateYear() {
        $('.btnUpdateYear').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                $('.btnUpdateYear').attr('disabled', true);
                isSubmitted = true;
                let data = new FormData($('.yearFormId')[0]);
                data.append('year', $('#yearId').val());
                $.ajax({
                    url: _baseURL() + 'updateYear',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.yearFormId')[0].reset();
                            $("#yearModal").modal('hide');
                            getYears();
                            $('.btnUpdateYear').attr('disabled', false);
                            isSubmitted = false;
                        } else {
                            $('.btnUpdateYear').attr('disabled', false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnUpdateYear').attr('disabled', false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function getYears() {
        $('#yearListTable').dataTable().fnDestroy();
        $('#yearListTable tbody').empty();
        let hasEditRole = $('#hasEditRole').val();
        // let btnEdit = '';//TODO: to be used this one after incorporation security
        let btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#yearModal"><i class="fa fa-edit"></i> Edit</button>';
        if (hasEditRole.toString() === 'true') {
            btnEdit = '<button id="btnEdit" class="btn btn-s btn-info square btnEdit" data-bs-toggle="modal" data-bs-target="#yearModal"><i class="fa fa-edit"></i> Edit</button>';
        }
        $.ajax({
            url: _baseURL() + 'getYears',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {
                        class: 'align-middle',
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },

                    {data: 'status', class: "status align-middle hidden"},
                    {
                        data: {year: 'year', status: 'status'}, class: "year align-middle"
                        , "mRender": function (data) {
                            let active = '<i class="status-icon bg-success"></i>';
                            let inactive = '<i class="status-icon bg-danger"></i>';
                            return data.status === 'A' ? active + data.year : inactive + data.year
                        }
                    },
                    {
                        "data": "null", class: 'align-middle',
                        "mRender": function () {
                            return btnEdit;
                        }
                    }
                ];
                $('#yearListTable').DataTable({
                    data: res
                    , columns: columnDef
                    , pageLength: 100
                });
            }
        });
    }

    function btnEdit() {
        $('#yearListTable tbody').on('click', 'tr .btnEdit', function () {
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            let row = $(this).closest('tr');

            let year = row.find(".year").text();
            let status = row.find(".status").text();

            // $('#yearId').val(year);
            // $('#status').val(status);


            $('.yearFormId')[0].reset();
            $("#yearModal").modal('show');
            $('.modal-title').empty().append('<i class="fa fa-edit"></i> Edit Year');
            $(".btnUpdateYear").removeClass("hidden");
            $(".btnSaveYear").addClass("hidden");

            $('#year').val(year).prop('disabled', true);
            $('#yearId').val(year);
            $('#status').val(status);
        });
    }


    function btnNewYear() {
        $(".btnNewYear").on("click", function () {
            $('#year').prop('disabled', false);
            $('.modal-title').empty().append('<i class="fa fa-plus"></i> Add Year');
            $(".btnUpdateYear").addClass("hidden");
            $(".btnSaveYear").removeClass("hidden");
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            $('.userFormId')[0].reset();
        })
    }

    return {
        addYear: addYear,
        getYears: getYears,
        btnEdit: btnEdit,
        btnNewYear: btnNewYear,
        updateYear: updateYear,
    }
})();

$(document).ready(function () {

    $('#yearModal').on('shown.bs.modal', function () {
        $('.chosen-select', this).chosen('destroy').chosen();
    });
    year.addYear();
    year.getYears();
    year.btnEdit();
    year.btnNewYear();
    year.updateYear();
    let minOffset = 0,
        maxOffset = 15;
    let thisYear = new Date().getFullYear() + 2;
    let select = $('#year');
    for (let i = minOffset; i <= maxOffset; i++) {
        let year = thisYear - i;
        $('<option>', {value: year, text: year}).appendTo(select);
    }
});