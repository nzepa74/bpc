company = (function () {
    "use strict";
    let form = $('#companyFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/company/';
    }

    function addCompany() {
        $('.btnSaveCompany').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveCompany').attr('disabled', true);
                let data = new FormData($('.companyFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addCompany',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.companyFormId')[0].reset();
                            $("#companyModal").modal('hide');
                            getCompanies();
                            isSubmitted = false;
                        } else {
                            $('.btnSaveCompany').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveCompany').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function updateCompany() {
        $('.btnUpdateCompany').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnUpdateCompany').attr('disabled', true);
                let data = new FormData($('.companyFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'updateCompany',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.companyFormId')[0].reset();
                            $("#companyModal").modal('hide');
                            getCompanies();
                            $('.btnUpdateCompany').attr("disabled", false);
                            isSubmitted = false;
                        } else {
                            $('.btnUpdateCompany').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnUpdateCompany').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function getCompanies() {
        $('#companyListTable').dataTable().fnDestroy();
        $('#companyListTable tbody').empty();
        let hasEditRole = $('#hasEditRole').val();
        // let btnEdit = '';//TODO: to be used this one after incorporation security
        let btnEdit = '<button id="btnEdit" class="btn btn-s square btn-info btnEdit" data-bs-toggle="modal" data-bs-target="#companyModal"><i class="fa fa-edit"></i> Edit</button>';
        if (hasEditRole.toString() === 'true') {
            btnEdit = '<button id="btnEdit" class="btn btn-s square btn-info btnEdit" data-bs-toggle="modal" data-bs-target="#companyModal"><i class="fa fa-edit"></i> Edit</button>';
        }
        $.ajax({
            url: _baseURL() + 'getCompanies',
            type: 'GET',
            success: function (res) {
                let columnDef = [
                    {
                        class: 'align-middle',
                        "mRender": function (data, type, row, meta) {
                            return meta.row + 1;
                        }
                    },
                    {data: 'companyId', class: "companyId hidden align-middle"},
                    {
                        data: {status: 'status', companyName: 'companyName', isParentCompany: 'isParentCompany'},
                        class: "align-middle",
                        "mRender": function (data) {
                            let active = '<i class="status-icon bg-success"></i>';
                            let companyName = data.companyName;
                            companyName = data.isParentCompany === 'Y' ? '<strong>' + companyName + '</strong>' : companyName
                            return data.status === 'A' ? active + companyName
                                : '<i class="status-icon bg-danger"></i>' + companyName;
                        }
                    },
                    {data: 'shortName', class: "shortName align-middle"},
                    {
                        "data": "null", class: 'align-middle',
                        "mRender": function () {
                            return btnEdit;
                        }
                    }
                ];
                $('#companyListTable').DataTable({
                    data: res.dto
                    , columns: columnDef
                    , pageLength: 100
                });
            }
        });
    }

    function btnEdit() {
        $('#companyListTable tbody').on('click', 'tr .btnEdit', function () {
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            $(".displayLogo").empty();
            let row = $(this).closest('tr');
            let companyId = row.find(".companyId").text();
            $.ajax({
                url: _baseURL() + 'getByCompanyId',
                type: 'GET',
                data: {companyId: companyId},
                success: function (res) {
                    let data = res.dto;
                    $('.companyFormId')[0].reset();
                    $("#companyModal").modal('show');
                    $('.modal-title').empty().append('<i class="fa fa-edit"></i> Edit Company');
                    $(".btnUpdateCompany").removeClass("hidden");
                    $(".btnSaveCompany").addClass("hidden");
                    $('#logo').removeAttr('required');
                    $('.logoLabel').removeClass('required');
                    populate(data);
                    if (data.isParentCompany === 'Y') {
                        $('#isParent').prop('checked', true);
                    } else {
                        $('#isParent').prop('checked', false);
                    }
                    document.getElementById('displayLogo').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:150px;height:150px" alt=""/>';
                }
            });
        });
    }

    function displayLogo() {
        $('#logo').on('change', function () {
            let file, img;
            if ((file = this.files[0])) {
                img = new Image();
                img.onload = function () {
                    if (this.width > 200 || this.height > 200) {
                        $("#logo").val(null);
                        $(".displayLogo").empty();
                        warningMsg("Logo image size exceeded, required size 200 X 200.");
                    } else {
                        $(".displayLogo").attr('id', 'displayLogo');
                    }
                };
                img.onerror = function () {
                    warningMsg("Not a valid file: " + file.type);
                };
                let _URL = window.URL || window.webkitURL;
                img.src = _URL.createObjectURL(file);
            }
            readAndShow(this);
        });

        function readAndShow(input) {
            if (input.files && input.files[0]) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    document.getElementById('displayLogo').innerHTML =
                        '<img type="file"  src="' + e.target.result + '" style="width:200px;height:200px" alt=""/>';
                };
                reader.readAsDataURL(input.files[0]);
            }
        }
    }


    function btnNewCompany() {
        $('.btnNewCompany').on('click', function () {
            $('.modal-title').empty().append('<i class="fa fa-plus"></i> Add Company');
            $(".btnUpdateCompany").addClass("hidden");
            $(".btnSaveCompany").removeClass("hidden");
            $(".field").removeClass("is-invalid");
            $(".error").empty();
            $('.companyFormId')[0].reset();
            $('#logo').prop('required', true);
            $('.logoLabel').addClass('required');
            $('.displayLogo').empty();
        });
    }

    return {
        getCompanies: getCompanies,
        btnNewCompany: btnNewCompany,
        addCompany: addCompany,
        updateCompany: updateCompany,
        btnEdit: btnEdit,
        displayLogo: displayLogo
    }
})();

$(document).ready(function () {
    $('#companyModal').on('shown.bs.modal', function () {
        $('.chosen-select', this).chosen('destroy').chosen();
    });
    company.getCompanies();
    company.btnNewCompany();
    company.addCompany();
    company.updateCompany();
    company.btnEdit();
    company.displayLogo();
});