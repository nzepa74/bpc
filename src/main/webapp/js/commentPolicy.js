commentPolicy = (function () {
    "use strict";
    let form = $('#commentPolicyFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/commentPolicy/';
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

    function addCommentPolicy() {
        $('.btnSaveCommentPolicy').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveCommentPolicy').attr('disabled', true);
                let data = new FormData($('.commentPolicyFormId')[0]);
                // let contents = document.querySelector('#editorCommentPolicy').children[0].innerHTML;
                // data.append('contents', contents)
                $.ajax({
                    url: _baseURL() + 'addCommentPolicy',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayCommentPolicy();
                            $('#commentPolicyModal').modal('hide');
                            isSubmitted = false;
                            $('.btnSaveCommentPolicy').attr("disabled", false);
                        } else {
                            $('.btnSaveCommentPolicy').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveCommentPolicy').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function editCommentPolicy() {
        $('.btnEditCommentPolicy').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnEditCommentPolicy').attr('disabled', true);
                let data = new FormData($('.commentPolicyFormId')[0]);
                // let contents = document.querySelector('#editorCommentPolicy').children[0].innerHTML;
                // data.append('contents', contents)
                $.ajax({
                    url: _baseURL() + 'editCommentPolicy',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayCommentPolicy();
                            $('#commentPolicyModal').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnEditCommentPolicy').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnEditCommentPolicy').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function displayCommentPolicy() {
        $.ajax({
            url: _baseURL() + 'getCommentPolicy',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    $('.btnEditCommentPolicyModal').removeClass('hidden');
                    $('.btnAddCommentPolicyModal').addClass('hidden');
                    let contents = data.contents;
                    $('.contentDisplay').empty().html(contents);
                } else {
                    $('.btnEditCommentPolicyModal').addClass('hidden');
                    $('.btnAddCommentPolicyModal').removeClass('hidden');
                }
            }
        });
    }

    function getCommentPolicy() {
        $.ajax({
            url: _baseURL() + 'getCommentPolicy',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;

                    $("#editorCommentPolicy").summernote('code', contents, configEditor);
                    // let editorCommentPolicy = new Quill('#editorCommentPolicy', editorConfig());
                    // editorCommentPolicy.root.innerHTML = contents;
                    /*document.querySelector('#editorCommentPolicy').children[0].innerHTML = contents;*/
                } else {
                    // new Quill("#editorCommentPolicy", editorConfig('Type your something here...'));
                }
            }
        });
    }

    function btnAddEdit() {
        $('.btnAddCommentPolicyModal').on('click', function () {
            $(".modal-title").html('<i class="fa fa-plus"></i> Add CommentPolicy');
            $('.btnEditCommentPolicy').addClass('hidden');
            $('.btnSaveCommentPolicy').removeClass('hidden');
            getCommentPolicy();
        });
        $('.btnEditCommentPolicyModal').on('click', function () {
            $(".modal-title").html('<i class="fa fa-edit"></i> Edit CommentPolicy');
            $('.btnEditCommentPolicy').removeClass('hidden');
            $('.btnSaveCommentPolicy').addClass('hidden');
            getCommentPolicy();
        });
    }

    function initEditor() {
        $('#editorCommentPolicy').summernote(configEditor);
    }

    return {
        initEditor: initEditor,
        displayCommentPolicy: displayCommentPolicy,
        btnAddEdit: btnAddEdit,
        addCommentPolicy: addCommentPolicy,
        editCommentPolicy: editCommentPolicy,
    }
})();

$(document).ready(function () {
    commentPolicy.initEditor();
    commentPolicy.displayCommentPolicy();
    commentPolicy.btnAddEdit();
    commentPolicy.addCommentPolicy();
    commentPolicy.editCommentPolicy();
});