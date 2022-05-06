tfBcpmProdSaleAddTarget = (function () {
    "use strict";
    let form = $('#targetForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/tfBcpmProdSaleAddTarget/';
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
    let targetActivityList = [];

    function getPreData() {
        //to get financial KPI list for typeahed
        $.ajax({
            url: _baseURL() + "getTargetActivity",
            type: 'GET',
            success: function (res) {
                for (let i in res) {
                    targetActivityList.push(res[i].activity);
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

        $('#activity').typeahead({
                hint: true,
                highlight: true,
                minLength: 1,
                selectable: 'Typeahead-selectable',
                menu: true
            },
            {
                name: 'targetActivityList',
                source: substringMatcher(targetActivityList)
            });
    }

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
                // let background = new Quill('#background', editorConfig()).root.innerHTML;
                let background = $('#background').val();
                let output = $('#output').val();
                let risks = $('#risks').val();
                let evalMethod = $('#evalMethod').val();
                let evalFormulaId = $('input[name="evalFormula"]:checked').val();
                let isProratable = $('input[name="isProratable"]:checked').val();
                let data = new FormData($('.targetForm')[0]);
                data.append('background', background);
                data.append('output', output);
                data.append('risks', risks);
                data.append('evalMethod', evalMethod);
                data.append('evalFormulaId', evalFormulaId);
                data.append('isProratable', isProratable);
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
        $('.preYearTargetLbl').text(preYear);

        $('.curYearTargetLbl').text(year);
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
                    let productionWt = data.productionWt;
                    setTimeout(function () {
                        $('.headingWt').empty().text(productionWt + '%');
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

    function btnAddMore() {
        $('.btnAddMore').on('click', function () {
            let dynamicId = generateUniqueUiId();
            let curYear = $('#year').val();
            let preYear = curYear - 1;
            let subTarget = '<tr><td>' +
                '<div class="dropdown-divider"></div>' +
                '<div class="row">' +
                '<div class="col-md-2">' +
                '<label for="subTarget' + dynamicId + '" class="col-form-label required pull-right">Particular</label>' +
                '</div>' +
                '<div class="col-md-6 form-group">' +
                '<textarea id="subTarget' + dynamicId + '" class="form-control square field" name="subTargetDtos[0].subTarget" autocomplete="off" required onkeyup="textAreaAdjust(this)"></textarea>' +
                '</div>' +
                '<div class="col-md-2">' +
                '<label for="deadline' + dynamicId + '" class="col-form-label required pull-right"> Unit</label>' +
                '</div> <div class="col-md-2 form-group">' +
                '<input type="text" id="uom' + dynamicId + '"  class="form-control square field" name="subTargetDtos[0].uom" required autocomplete="off">' +
                '</div>' +
                '</div>' +
                '<div class="row">' +
                '<div class="col-md-2">' +
                '<label for="preYearActual' + dynamicId + '" class="col-form-label required pull-right">' +
                '<span class="preYearActualLbl">' + preYear + '</span> Actual</label>' +
                '</div>' +
                '<div class="col-md-2 form-group">' +
                '<input type="number" id="preYearActual' + dynamicId + '" class="form-control square field" name="subTargetDtos[0].preYearActual" required autocomplete="off">' +
                '</div>' +
                '<div class="col-md-2">' +
                '<label for="preYearTarget' + dynamicId + '" class="col-form-label required pull-right">' +
                '<span class="preYearTargetLbl">' + preYear + '</span> Target</label>' +
                '</div>' +
                '<div class="col-md-2 form-group">' +
                '<input type="number" id="preYearTarget' + dynamicId + '" class="form-control square field"' +
                'name="subTargetDtos[0].preYearTarget" required autocomplete="off">' +
                '</div>' +
                '<div class="col-md-2">' +
                '<label for="curYearTarget' + dynamicId + '" class="col-form-label required pull-right">' +
                '<span class="curYearTargetLbl">' + curYear + '</span> Target </label>' +
                '</div>' +
                '<div class="col-md-2 form-group">' +
                '<input type="number" id="curYearTarget' + dynamicId + '" class="form-control square field"' +
                'name="subTargetDtos[0].curYearTarget" required autocomplete="off">' +
                '</div>' +
                '</div>' +
                '<div class="row">' +
                '<div class="col-md-2"> ' +
                '<label for="weightage' + dynamicId + '" class="col-form-label required pull-right"> Weightage(%)</label> ' +
                '</div>' +
                '<div class="col-md-2 form-group">' +
                '<input type="number" id="weightage' + dynamicId + '" class="form-control square field" name="subTargetDtos[0].weightage" required autocomplete="off">' +
                '</div>' +
                '<div class="col-md-2">' +
                '<label for="attachedFiles' + dynamicId + '" class="col-form-label pull-right logoLabel">Attachment</label>' +
                '</div>' +
                '<div class="col-md-6 form-group">' +
                '<input type="file" id="attachedFiles' + dynamicId + '" name="subTargetDtos[0].attachedFiles" ' +
                'accept="image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls"  multiple  class="form-control square field cursor-pointer"/>' +
                '<small><i class="fa fa-info-circle infoMsg"></i> Note: Accept PDF/DOCX/XLXS/PNG/JPG only</small>' +
                '</div>' +
                '</div>' +
                '<div class="row">' +
                '<div class="col-md-2">' +
                '<label for="explanation' + dynamicId + '" class="col-form-label pull-right"> Explanation</label>' +
                '</div>' +
                '<div class="col-md-8 form-group">' +
                '<textarea id="explanation' + dynamicId + '" class="form-control square field" ' +
                'name="subTargetDtos[0].explanation" autocomplete="off" onkeyup="textAreaAdjust(this)"></textarea>' +
                '</div>' +
                '<div class="col-md-2 form-group">' +
                '<button type="button" class="btn btn-danger btn-s square btnDeleteSubTarget"><i class="fa fa-trash"></i> Delete this target</button>' +
                '</div>' +
                '</div>' +
                '</td>' +
                '</tr>';
            $('#subTargetTable tbody').append(subTarget);
            targetFormIndexing($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
            targetFormIndexing2($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
        });
    }

    function deleteSubTarget() {
        $('#subTargetTable tbody').on('click', 'tr .btnDeleteSubTarget', function () {
            let row = $(this).closest('tr');
            let selectedRow = row.addClass('selected');
            selectedRow.remove();
            selectedRow.removeClass('selected');
            targetFormIndexing($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
            targetFormIndexing2($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
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

    function btnGoBack() {
        $('.btnGoBack').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmProdSaleList?yId=' + year + '&&cId=' + companyId
        });
    }

    return {
        getPreData: getPreData
        , autoSuggest: autoSuggest
        , initEditor: initEditor
        , initialSetup: initialSetup
        , onChangeYearAndCompany: onChangeYearAndCompany
        , addTarget: addTarget
        , toggleLabel: toggleLabel
        , getAllocatedWt: getAllocatedWt
        , btnAddMore: btnAddMore
        , deleteSubTarget: deleteSubTarget
        , btnGoBack: btnGoBack
    }
})
();
$(document).ready(
    function () {
        let year = $('#yearHidden').val();
        let companyId = $('#companyIdHidden').val();
        tfBcpmProdSaleAddTarget.getPreData();
        tfBcpmProdSaleAddTarget.autoSuggest();
        tfBcpmProdSaleAddTarget.initEditor();
        tfBcpmProdSaleAddTarget.initialSetup();
        tfBcpmProdSaleAddTarget.onChangeYearAndCompany();
        tfBcpmProdSaleAddTarget.toggleLabel(year);
        tfBcpmProdSaleAddTarget.addTarget();
        tfBcpmProdSaleAddTarget.getAllocatedWt(year, companyId);
        tfBcpmProdSaleAddTarget.btnAddMore();
        tfBcpmProdSaleAddTarget.deleteSubTarget();
        tfBcpmProdSaleAddTarget.btnGoBack();
    });
