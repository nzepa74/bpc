help = (function () {
    "use strict";
    let filesForm = $('#filesFormId');
    let generalSupportFormId = $('#generalSupportFormId');
    let technicalSupportFormId = $('#technicalSupportFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/help/';
    }

    let configEditor = {
        fontNames: ['Garamond'],
        fontNamesIgnoreCheck: ['Merriweather'],
        placeholder: 'Type  something here...',
        toolbar: [
            ['undo', ['undo',]],
            ['redo', ['redo',]],
            ['fontsize', ['fontsize']],
            ['style', ['style']],
            ['font', ['bold', 'italic', 'underline', 'superscript', 'subscript', 'strikethrough', 'clear']],
            ['fontname', ['fontname']],
            ['color', ['color']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['table', ['table']],
            ['insert', ['hr']],
        ],
    };

    function getFiles() {
        let hasDeleteRole = $('#hasDeleteRole').val();
        // let btnDelete = '';
        let btnDelete = '&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trash cursor-pointer deleteFile errorMsg"></i>';
        let btnView = '';
        btnView = '&nbsp;&nbsp;<i class="fa fa-eye cursor-pointer viewFile infoMsg"></i>';
        if (hasDeleteRole.toString() === 'true') {
            btnDelete = '&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-trash cursor-pointer deleteFile errorMsg"></i>';
        }

        $.ajax({
            url: _baseURL() + 'getFiles',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    $('.fileUl').empty();
                    for (let i = 0; i < data.length; i++) {
                        let fileId = data[i].fileId;
                        let fileUrl = data[i].fileUrl;
                        let fileName = data[i].fileName;
                        fileName = '<span class="fileName cursor-pointer">&nbsp;&nbsp;' + fileName + '</span>'
                        let fileExtension = data[i].fileExtension;
                        let fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + btnDelete + '</a></li>';
                        if (fileExtension.toUpperCase() === 'DOCX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-word-o infoMsg"></i>' + fileName + btnDelete + '</a></li>';
                        }
                        if (fileExtension.toUpperCase() === 'JPG' || fileExtension.toUpperCase() === 'JPEG'
                            || fileExtension.toUpperCase() === 'PNG') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-image-o compactTarget"></i>' + fileName + btnView + btnDelete + '</a></li>';
                        }

                        if (fileExtension.toUpperCase() === 'PDF') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + btnView + btnDelete + '</a></li>';
                        }
                        if (fileExtension.toUpperCase() === 'PPTX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-powerpoint-o errorMsg"></i>' + fileName + btnDelete + '</a></li>';
                        }
                        if (fileExtension.toUpperCase() === 'XLSX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-excel-o successMsg"></i>' + fileName + btnDelete + '</a></li>';
                        }
                        if (fileExtension.toUpperCase() === 'TXT') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><a href="#"><i class="fa fa-file-text-o"></i>' + fileName + btnDelete + '</a></li>';
                        }
                        $('.fileUl').append(fileList);
                    }
                }
            }
        });
    }

    function deleteFile() {
        $('.fileListDiv ul').on('click', 'li a i.deleteFile', function () {
            let fileId = $(this).closest('.fileUl .fileLi').find('.fileId').val();
            let audio = new Audio('assets/sounds/info/1.mp3');
            audio.play();
            $.confirm({
                title: 'Confirmation',
                content: 'Are you sure you want to delete?  <center class="small">You cannot recover once deleted.</center>',
                type: 'orange',
                typeAnimated: true,
                icon: 'fa fa-question',
                buttons: {
                    close: {
                        text: 'No, Cancel',
                    },
                    confirm: {
                        text: 'Yes, Sure',
                        btnClass: 'btn-red',
                        action: function () {
                            $.ajax({
                                url: _baseURL() + 'deleteFile',
                                type: "POST",
                                data: {fileId: fileId},
                                success: function (res) {
                                    if (res.status === 1) {
                                        successMsg(res.text);
                                        $('.fileUl').empty();
                                        getFiles();
                                    } else {
                                        warningMsg(res.text);
                                    }
                                }
                            });
                        }
                    },
                }
            });
        });
    }

    function viewFile() {
        $('.fileListDiv ul').on('click', 'li .viewFile', function () {
            let fileId = $(this).closest('.fileUl .fileLi').find('.fileId').val();

            let url = _baseURL() + 'viewFile/' + fileId;
            $.ajax({
                url: url,
                type: 'GET',
                data: {fileId: fileId},
                success: function () {
                    // window.location.href = url;
                    // window.open(url, '_blank');
                    window.open(globalLib.baseUrl() + url, '_blank'
                    );
                }
            });
        });
    }

    function downloadFile() {
        $('.fileListDiv ul').on('click', 'li a span.fileName', function () {
            let fileId = $(this).closest('.fileUl .fileLi').find('.fileId').val();
            let url = _baseURL() + 'downloadFile/' + fileId;
            $.ajax({
                url: url,
                type: 'GET',
                data: {fileId: fileId},
                success: function () {
                    window.location.href = url;
                    // window.open(url, '_blank');
                }
            });
        });
    }

    function btnAdd() {
        $('.btnAdd').on('click', function () {
            $(".field").removeClass("parsley-error");
            $(".error").empty();
        });
    }

    function addFiles() {
        $('.btnSaveFiles').on('click', function () {
            globalLib.isFormValid(filesForm);
            if (filesForm.valid()) {
                isSubmitted = true;
                $('.btnSaveFiles').attr('disabled', true);
                let data = new FormData($('.filesFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addFiles',
                    type: "POST",
                    data: data,
                    enctype: 'multipart/form-data',
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.fileUl').empty();
                            getFiles();
                            $('.field').val('');
                            $('#filesModal').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnSaveFiles').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveFiles').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function saveGeneralSupport() {
        $('.btnSaveGeneralSupport').on('click', function () {
            globalLib.isFormValid(generalSupportFormId);
            if (generalSupportFormId.valid()) {
                isSubmitted = true;
                $('.btnSaveGeneralSupport').attr('disabled', true);
                isSubmitted = true;
                $('.btnSaveAbout').attr('disabled', true);
                let data = new FormData($('.generalSupportFormId')[0]);
                // let contents = document.querySelector('#generalSupportEditor').children[0].innerHTML;
                // data.append('contents', contents)
                $.ajax({
                    url: _baseURL() + 'addGeneralSupport',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayGeneralSupport();
                            $('.field').val('');
                            $('#generalSupportModal').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnSaveGeneralSupport').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveGeneralSupport').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function displayGeneralSupport() {
        $.ajax({
            url: _baseURL() + 'getGeneralSupport',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;
                    $('.generalSupportDisplay').empty().append('<span>' + contents + '</span>');
                }
            }
        });
    }

    function getGeneralSupport() {
        $.ajax({
            url: _baseURL() + 'getGeneralSupport',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;
                    $("#generalSupportEditor").summernote('code', contents, configEditor);
                }
            }
        });
    }

    function getTechnicalSupport() {
        $.ajax({
            url: _baseURL() + 'getTechnicalSupport',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;
                    $("#technicalSupportEditor").summernote('code', contents, configEditor);
                }
            }
        });
    }

    function saveTechnicalSupport() {
        $('.btnSaveTechnicalSupport').on('click', function () {
            globalLib.isFormValid(technicalSupportFormId);
            if (technicalSupportFormId.valid()) {
                isSubmitted = true;
                $('.btnSaveTechnicalSupport').attr('disabled', true);
                let data = new FormData($('.technicalSupportFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addTechnicalSupport',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayTechnicalSupport();
                            $('#technicalSupportModal').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnSaveTechnicalSupport').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveTechnicalSupport').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function displayTechnicalSupport() {
        $.ajax({
            url: _baseURL() + 'getTechnicalSupport',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;
                    $('.technicalSupportDisplay').empty().append('<span>' + contents + '</span>');
                }
            }
        });
    }

    function addMoreAttachment() {
        $('#fileAttachmentTableId tbody').on('click', '#btnAddMore', function () {
            let row = "<tr style='border-bottom: hidden'>" +
                "<td>" + "<input type='file' id ='attachedFile' class='form-control attachedFile' name ='fileAttachmentDTOs[0].attachedFile' required accept='image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls'/>" + "</td>" +
                "<td>" + "<button class='btn btn-s square btn-danger' type='button' id='btnRemove'><i class='fa fa-trash'></i> Remove</button> &nbsp;&nbsp;&nbsp;&nbsp;" +
                "<button class='btn btn-s square btn-success' type='button' id='btnAddMore'><i class='fa fa-plus'></i> Add More</button>" + "</td>" +
                "</tr>";
            $('#fileAttachmentTableId tbody').append(row);
            $(this).addClass('hidden');
            $('#btnRemove').removeClass('hidden');
            formIndexing($('#fileAttachmentTableId tbody'), $('#fileAttachmentTableId tbody').find('tr'));
        });
    }

    function deleteAttachment() {
        $('#fileAttachmentTableId tbody').on('click', 'tr #btnRemove', function () {
            let rowLen = $('#fileAttachmentTableId tbody tr').length;
            $(this).closest('tr').remove();
            if (rowLen === 2) {
                $('#fileAttachmentTableId tr').last().find('#btnRemove').addClass('hidden');
            }
            $('#fileAttachmentTableId tr').last().find('#btnAddMore').removeClass('hidden');
            formIndexing($('#fileAttachmentTableId tbody'), $('#fileAttachmentTableId tbody').find('tr'));
        });
    }

    function actionBtn() {
        $('.btnGeneralSupportModal').on('click', function () {
            getGeneralSupport();
        });
        $('.btnTechnicalSupportModal').on('click', function () {
            getTechnicalSupport();
        });
    }

    function initEditor() {
        $('#generalSupportEditor').summernote(configEditor);
        $('#technicalSupportEditor').summernote(configEditor);
    }

    return {
        initEditor: initEditor,
        getFiles: getFiles,
        deleteFile: deleteFile,
        btnAdd: btnAdd,
        addFiles: addFiles,
        viewFile: viewFile,
        downloadFile: downloadFile,
        displayGeneralSupport: displayGeneralSupport,
        saveGeneralSupport: saveGeneralSupport,
        displayTechnicalSupport: displayTechnicalSupport,
        saveTechnicalSupport: saveTechnicalSupport,
        addMoreAttachment: addMoreAttachment,
        deleteAttachment: deleteAttachment,
        actionBtn: actionBtn,
    }
})();

$(document).ready(function () {
    help.initEditor();
    help.getFiles();
    help.deleteFile();
    help.addFiles();
    help.btnAdd();
    help.addFiles();
    help.viewFile();
    help.downloadFile();
    help.displayGeneralSupport();
    help.saveGeneralSupport();
    help.displayTechnicalSupport();
    help.saveTechnicalSupport();
    help.addMoreAttachment();
    help.deleteAttachment();
    help.actionBtn();
});