tfFinAddTarget = (function () {
    "use strict";
    let form = $('#targetForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/tfDhiFinAddTarget/';
    }

    let finKpiList = [];

    function getPreData() {
        //to get financial KPI list for typeahed
        $.ajax({
            url: _baseURL() + "getFinKpi",
            type: 'GET',
            success: function (res) {
                for (let i in res) {
                    finKpiList.push(res[i].finKpi);
                }
            }
        });
    }

    function autoSuggest() {
        let substringMatcher = function (strs) {
            return function findMatches(q, cb) {
                let matches, substrRegex;
                matches = [];
                substrRegex = new RegExp(q, 'i');
                $.each(strs, function (i, str) {
                    if (substrRegex.test(str)) {
                        matches.push(str);
                    }
                });
                cb(matches);
            };
        };

        $('#finKpi').typeahead({
                hint: true,
                highlight: true,
                minLength: 1,
                selectable: 'Typeahead-selectable',
                menu: true
            },
            {
                name: 'finKpiList',
                source: substringMatcher(finKpiList)
            });
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

    function initEditor() {
        $('#background').summernote(configEditor);
        $('#output').summernote(configEditor);
        $('#risks').summernote(configEditor);
        $('#evalMethod').summernote(configEditor);
    }

    function addTarget() {
        $('.btnAddTarget').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnAddTarget').attr('disabled', true);
                let background = $('#background').val();
                let output = $('#output').val();
                let risks = $('#risks').val();
                let evalMethod = $('#evalMethod').val();
                let evalFormulaId = $('input[name="evalFormula"]:checked').val();
                let data = new FormData($('.targetForm')[0]);
                data.append('background', background);
                data.append('output', output);
                data.append('risks', risks);
                data.append('evalMethod', evalMethod);
                data.append('evalFormulaId', evalFormulaId);
//year and companyId is issue with chosen-select, so manually append to form
                let year = $('#year option:selected').val();
                let companyId = $('#companyId option:selected').val();

                data.append('companyId', companyId);
                data.append('year', year);

                $.ajax({
                    url: _baseURL() + 'addTarget',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.targetForm')[0].reset();
                            $('.btnAddTarget').attr("disabled", false);
                            resetEditor();
                            isSubmitted = false;
                            //issue with chosen-select, so need to trigger after save
                            $('#year').val(year).trigger("chosen:updated");
                            $('#companyId').val(companyId).trigger("chosen:updated");
                        } else {
                            $('.btnAddTarget').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnAddTarget').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function displayLogo(companyId) {
        let url = 'api/common/getCompanyInfo';
        $.ajax({
            url: url,
            type: 'GET',
            data: {companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    document.getElementById('displayLogoTargetDetail').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                    document.getElementById('displayLogoWriteup').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                }
            }
        });
    }

    function resetEditor() {
        $("#background").summernote('code', '');
        $("#output").summernote('code', '');
        $("#risks").summernote('code', '');
        $("#evalMethod").summernote('code', '');
    }

    function toggleLabel(year) {
        let preYear = year - 1;
        $('.headingYear').text(year);

        $('.preYearActualLbl').text(preYear);
        $('.preYearMidActualLbl').text(preYear);
        $('.preYearTargetLbl').text(preYear);

        $('.curYearBudgetLbl').text(year);
        $('.curYearTargetLbl').text(year);
        $('.curYearMidTargetLbl').text(year);
    }

    function onChangeYearAndCompany() {
        $('#year').on('change', function () {
            let year = $(this).val();
            let companyId = $('#companyId').val();
            toggleLabel(year);
            getAllocatedWt(year, companyId);
        });

        $('#companyId').on('change', function () {
            let companyId = $(this).val();
            let year = $('#year').val();
            getAllocatedWt(year, companyId);
            displayLogo(companyId);
        });
    }

    function getAllocatedWt(year, companyId) {
        $.ajax({
            url: _baseURL() + 'getAllocatedWt',
            type: "GET",
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let financialWt = data.financialWt;
                    setTimeout(function () {
                        $('.headingWt').empty().text(financialWt + '%');
                    }, 100);
                } else {
                    $('.headingWt').empty().text('0%');
                }
            }
        });
    }

    function initialSetup() {
        let year = $('#yearHidden').val();
        let companyId = $('#companyIdHidden').val();
        $('#year').val(year).trigger("chosen:updated");
        $('#companyId').val(companyId).trigger("chosen:updated");
        getAllocatedWt(year, companyId);
        displayLogo(companyId);
    }

    function btnGoBack() {
        $('.btnGoBack').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiFinList?yId=' + year + '&&cId=' + companyId
        });
    }

    /*('.btnAddTarget').on('click', function () {
        let backgroundEditor = new Quill('#output', editorConfig());
        // alert(backgroundEditor.root.innerHTML);
        //to get value from editor
        let background = document.querySelector('#background').children[0].innerHTML
        //to set value to editor
        let postContent = "<h2>My short post</h2><p>This is a <strong>really, really</strong> short post.</p>";
        backgroundEditor.root.innerHTML = postContent;
    });*/

    return {
        getPreData: getPreData
        , autoSuggest: autoSuggest
        , initEditor: initEditor
        , initialSetup: initialSetup
        , onChangeYearAndCompany: onChangeYearAndCompany
        , addTarget: addTarget
        , toggleLabel: toggleLabel
        , getAllocatedWt: getAllocatedWt
        , btnGoBack: btnGoBack
    }
})
();
$(document).ready(
    function () {
        let year = $('#yearHidden').val();
        let companyId = $('#companyIdHidden').val();
        tfFinAddTarget.getPreData();
        tfFinAddTarget.autoSuggest();
        tfFinAddTarget.initEditor();
        tfFinAddTarget.initialSetup();
        tfFinAddTarget.onChangeYearAndCompany();
        tfFinAddTarget.toggleLabel(year);
        tfFinAddTarget.addTarget();
        tfFinAddTarget.getAllocatedWt(year, companyId);
        tfFinAddTarget.btnGoBack();
    });
