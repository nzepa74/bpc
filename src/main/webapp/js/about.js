about = (function () {
    "use strict";
    let form = $('#aboutFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/about/';
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

    function addAbout() {
        $('.btnSaveAbout').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSaveAbout').attr('disabled', true);
                let data = new FormData($('.aboutFormId')[0]);
                // let contents = document.querySelector('#editorAbout').children[0].innerHTML;
                // data.append('contents', contents)
                $.ajax({
                    url: _baseURL() + 'addAbout',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayAbout();
                            $('#aboutModal').modal('hide');
                            isSubmitted = false;
                            $('.btnSaveAbout').attr("disabled", false);
                        } else {
                            $('.btnSaveAbout').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnSaveAbout').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function editAbout() {
        $('.btnEditAbout').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnEditAbout').attr('disabled', true);
                let data = new FormData($('.aboutFormId')[0]);
                // let contents = document.querySelector('#editorAbout').children[0].innerHTML;
                // data.append('contents', contents)
                $.ajax({
                    url: _baseURL() + 'editAbout',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            displayAbout();
                            $('#aboutModal').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnEditAbout').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnEditAbout').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function displayAbout() {
        $.ajax({
            url: _baseURL() + 'getAbout',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    $('.btnEditAboutModal').removeClass('hidden');
                    $('.btnAddAboutModal').addClass('hidden');
                    let contents = data.contents;
                    $('.contentDisplay').empty().html(contents);
                } else {
                    $('.btnEditAboutModal').addClass('hidden');
                    $('.btnAddAboutModal').removeClass('hidden');
                }
            }
        });
    }

    function getAbout() {
        $.ajax({
            url: _baseURL() + 'getAbout',
            type: "GET",
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let contents = data.contents;

                    $("#editorAbout").summernote('code', contents, configEditor);
                    // let editorAbout = new Quill('#editorAbout', editorConfig());
                    // editorAbout.root.innerHTML = contents;
                    /*document.querySelector('#editorAbout').children[0].innerHTML = contents;*/
                } else {
                    // new Quill("#editorAbout", editorConfig('Type your something here...'));
                }
            }
        });
    }

    function btnAddEdit() {
        $('.btnAddAboutModal').on('click', function () {
            $(".modal-title").html('<i class="fa fa-plus"></i> Add About');
            $('.btnEditAbout').addClass('hidden');
            $('.btnSaveAbout').removeClass('hidden');
            getAbout();
        });
        $('.btnEditAboutModal').on('click', function () {
            $(".modal-title").html('<i class="fa fa-edit"></i> Edit About');
            $('.btnEditAbout').removeClass('hidden');
            $('.btnSaveAbout').addClass('hidden');
            getAbout();
        });
    }

    function initEditor() {
        $('#editorAbout').summernote(configEditor);
    }

    return {
        initEditor: initEditor,
        displayAbout: displayAbout,
        btnAddEdit: btnAddEdit,
        addAbout: addAbout,
        editAbout: editAbout,
    }
})();

$(document).ready(function () {
    about.initEditor();
    about.displayAbout();
    about.btnAddEdit();
    about.addAbout();
    about.editAbout();
});