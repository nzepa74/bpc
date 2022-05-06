tfDhiCusSerEditTarget = (function () {
    "use strict";
    let form = $('#targetForm');
    let remarkFormId = $('#remarkFormId');
    let commentFormId = $('#commentFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/tfDhiCusSerEditTarget/';
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

    function getTarget() {
        let targetAuditId = $('#targetAuditId').val();
        $.ajax({
            url: _baseURL() + 'getTarget',
            type: 'GET',
            data: {targetAuditId: targetAuditId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let myRoleId = $('#myRoleId').val();
                    let adminRoleId = $('#adminRoleId').val();
                    let reviewerRoleId = $('#reviewerRoleId').val();
                    let creatorRoleId = $('#creatorRoleId').val();

                    let statusFlag = data.statusFlag;//C=created, X= closed, R=reopen

                    if (statusFlag === 'C' || statusFlag === 'R') {
                        $('.actionBtnReopen').addClass('hidden');
                        $('.actionBtnClose').removeClass('hidden');
                    } else if (statusFlag === 'X') {
                        $('.actionBtnReopen').removeClass('hidden');
                        $('.actionBtnClose').addClass('hidden');
                    }
                    if (statusFlag === 'X' && myRoleId === creatorRoleId) {
                        $('.chooseActionBtn').addClass('hidden');
                    }

                    //hide reviewer remark button and open close button to all user except reviewer and admin
                    if ((myRoleId.toString() !== reviewerRoleId.toString()) && (myRoleId.toString() !== adminRoleId.toString())) {
                        $('.actionBtn').addClass('hidden');
                        $('.btnReviewerRemark').addClass('hidden');
                        $('#curYearDhiProposal').prop('readonly', true);
                    }
                    //display to edit mode
                    populate(data);//it also display targetId, so not necessary to pass targetId from search list screen
                    $('#subTargetTable tbody').empty();
                    $('#displayTable tbody').empty();
                    getSubTarget(data);
                    $('#year').val(data.year).prop('disabled', true).trigger("chosen:updated");
                    $('#companyId').val(data.companyId).prop('disabled', true).trigger("chosen:updated");
                    getTargetEditHistory(data.targetId);
                    getTargetStatus(data.targetId);
                    let curYear = data.year;
                    toggleLabel(curYear);
                    getAllocatedWt(data.year, data.companyId);
                    getComment();
                    displayLogo(data.companyId)
                    $('#background').summernote('code', data.background, initEditor());
                    $('#output').summernote('code', data.output, initEditor());
                    $('#risks').summernote('code', data.risks, initEditor());
                    $('#evalMethod').summernote('code', data.evalMethod, initEditor());

                    let evalFormulaId = data.evalFormulaId;
                    let isProratable = data.isProratable;
                    if (evalFormulaId === 1) {
                        $('#formulaTypeOne').prop('checked', true);
                        $('#formulaTypeOthers').prop('checked', false);
                    }
                    if (evalFormulaId === 2) {
                        $('#formulaTypeOthers').prop('checked', true);
                        $('#formulaTypeOne').prop('checked', false);
                    }
                    if (isProratable === 'Y') {
                        $('#isProratableYes').prop('checked', true);
                        $('#isProratableNo').prop('checked', false);
                    } else {
                        $('#isProratableYes').prop('checked', false);
                        $('#isProratableNo').prop('checked', true);
                    }

                    //display to read mode detail writeup
                    let detailWriteup = '<div class="row"><strong>a) Background</strong></div>' +
                        '<div class="form-group">' + data.background + '</div>' +
                        '<div class="row"><strong> b) Target Output</strong></div>' +
                        '<div class="form-group">' + data.output + '</div>' +
                        '<div class="row"><strong> c) Risks Associated</strong></div>' +
                        '<div class="form-group">' + data.risks + '</div>' +
                        '<div class="row"><strong> d) Evaluation Method</strong></div>' +
                        '<div class="form-group">' + data.evalMethod + '</div>';
                    $('.detailWriteup').empty().html(detailWriteup);
                    let evalFormula = '50% weight+(50% weight-((delayed month/target month)*50% weight))';
                    let other = 'Other';
                    if (evalFormulaId !== 1) {
                        evalFormula = other;
                    }
                    let proratable = 'Yes';
                    if (isProratable === 'N') {
                        proratable = 'No';
                    }
                    $('.evalFormulaDisplay').empty().html('<strong>Evaluation Formula: </strong>' + evalFormula);
                    $('.isProratableDisplay').empty().html('<strong>Is proratable? </strong>' + proratable);
                }
            }
        });
    }

    function getSubTarget(dayta) {
        $.ajax({
            url: _baseURL() + 'getSubTarget',
            type: "GET",
            data: {targetAuditId: dayta.targetAuditId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let displayModeSubTarget = '';
                    for (let i = 0; i < data.length; i++) {
                        let attachment = 'Attachment';
                        let attachmentCount = data[i].attachmentCount;
                        attachment = attachmentCount > 1 ? 'Attachments' : attachment;
                        let attachments = '';
                        if (attachmentCount > 0) {
                            attachments = '<small title="Click to view" class="cursor-pointer attachments" data-bs-toggle="modal" data-bs-target="#attachmentModal">' + attachmentCount + ' <i class="fa fa-paperclip"></i> ' + attachment + '</small>';
                        }
                        let isNegative = data[i].isNegative;
                        let isNegativeRow = '<div class="row">';
                        let isNegativeCheckBox = '<input type="checkbox" class="form-check-input form-check-danger cursor-pointer"  id="isNegative' + i + '" name="subTargetDtos[0].isNegativeBoolean">';
                        if (isNegative === 'Y') {
                            isNegativeCheckBox = '<input type="checkbox" checked class="form-check-input form-check-danger cursor-pointer"  id="isNegative' + i + '" name="subTargetDtos[0].isNegativeBoolean">';
                            isNegativeRow = '<div class="row" style="color: red">';
                        }
                        let readReviewerRemark = '';
                        let addEditReviewerRemark = '';
                        let myRoleId = $('#myRoleId').val();
                        let creatorRoleId = $('#creatorRoleId').val();
                        if (myRoleId !== creatorRoleId) {
                            readReviewerRemark = '<span title="Click to view" class="badge bg-info square cursor-pointer btnReadRemarkModal"' +
                                'data-bs-toggle="modal" data-bs-target="#remarkModalDisplayMode">Reviewer\'s Remark</span>';
                            addEditReviewerRemark = '<span title="Click to view" class="badge bg-info square cursor-pointer btnAddRemarkModal"' +
                                'data-bs-toggle="modal" data-bs-target="#remarkModalEditMode">Reviewer\'s Remark</span>';
                        }
                        displayModeSubTarget = isNegativeRow +
                            '<span class="subTargetId hidden">' + data[i].subTargetId + '</span> ' +
                            '<div class="col-md-12"><span>Target Output:</span> <strong class="subTarget">' + data[i].subTarget + '</strong></divdiv>' +
                            '</div>' +
                            '<div class="row">' +
                            '<div class="col-md-4"><span>Deadline:</span> <strong>' + formatAsDate(data[i].deadline) + '</strong></div>' +
                            '<div class="col-md-4"><span>Weightage:</span> <strong>' + data[i].weightage + '</strong></div>' +
                            '<div class="col-md-2">' + attachments + '</div>' +
                            '<div class="col-md-2">' + readReviewerRemark +
                            '</div>' +
                            '</div>' +
                            '<div class="dropdown-divider"></div>' +
                            '</div>';
                        $('#displayTable tbody').append('<tr><td>' + displayModeSubTarget + '</td></tr>');

                        let editModeSubTarget = '<tr><td>' +
                            '<div class="dropdown-divider"></div>' +
                            '<div class="row">' +
                            '<div class="col-md-2">' +
                            '<label for="subTarget' + i + '" class="col-form-label required pull-right">Target Output</label>' +
                            '</div>' +
                            '<div class="col-md-6 form-group">' +
                            '<input type="hidden" class="subTargetId" name="subTargetDtos[0].subTargetId" value="' + data[i].subTargetId + '" >' +
                            '<textarea id="subTarget' + i + '" class="form-control square subTarget field" name="subTargetDtos[0].subTarget" autocomplete="off" required onkeyup="textAreaAdjust(this)">' + data[i].subTarget + '</textarea>' +
                            '<div class="form-check">' +
                            '<div class="pull-left custom-control custom-checkbox">' + isNegativeCheckBox +
                            // '<input type="checkbox" class="form-check-input form-check-danger cursor-pointer"  id="isNegative' + i + '" name="subTargetDtos[0].isNegativeBoolean">' +
                            '<label for="isNegative' + i + '"  class="cursor-pointer"><small style="text-transform: none;"> Mark as negative target</small></label>' +
                            '</div>' +
                            '</div>' +
                            '</div>' +
                            '<div class="col-md-2">' +
                            '<label for="deadline' + i + '" class="col-form-label required pull-right"> Deadline</label>' +
                            '</div><div class="col-md-2 form-group">' +
                            '<input type="text" id="deadline' + i + '" value="' + formatAsDate(data[i].deadline) + '" class="form-control datepicker dynamic square field" name="subTargetDtos[0].deadline" required autocomplete="off">' +
                            '</div>' +
                            '</div>' +
                            '<div class="row">' +
                            '<div class="col-md-2"> ' +
                            '<label for="weightage' + i + '" class="col-form-label required pull-right"> Weightage(%)</label> ' +
                            '</div>' +
                            '<div class="col-md-2 form-group">' +
                            '<input type="number" id="weightage' + i + '" class="form-control square field" value="' + data[i].weightage + '" name="subTargetDtos[0].weightage" required autocomplete="off">' +
                            '</div>' +
                            '<div class="col-md-2">' +
                            '<label for="attachedFiles' + i + '" class="col-form-label pull-right logoLabel">Attachment</label>' +
                            '</div>' +
                            '<div class="col-md-6 form-group">' +
                            '<input type="file" id="attachedFiles' + i + '" name="subTargetDtos[0].attachedFiles" ' +
                            'accept="image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls" multiple class="form-control square field cursor-pointer"/>' +
                            '<small><i class="fa fa-info-circle infoMsg"></i> Note: Accept PDF/DOCX/XLXS/PNG/JPG only</small>' +
                            '<span class="pull-right">' + attachments + '</span>' +
                            '</div>' +
                            '</div>' +
                            '<div class="row">' +
                            '<div class="col-md-2 form-group"></div>' +
                            '<div class="col-md-4 form-group">' +
                            '<button type="button" class="btn btn-danger btn-s square btnDeleteSubTarget"><i class="fa fa-trash"></i> Delete this target</button>' +
                            '</div>' +
                            '<div class="col-md-2">' + addEditReviewerRemark +
                            '</div>' +
                            '</div>' +
                            '</td>' +
                            '</tr>';
                        $('#subTargetTable tbody').append(editModeSubTarget);
                        targetFormIndexing($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
                        targetFormIndexing2($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
                    }
                    let targetDetail = '<div class="row">' +
                        '<div class="col-md-4"><span>Serial No:</span> <strong>' + isNull(dayta.serialNo) + '</strong></div>' +
                        '<div class="col-md-8"><span>Activity:</span> <strong>' + dayta.activity + '</strong></div>' +
                        '</div>';
                    $('.targetDetail').empty().html(targetDetail);
                    $('.explanationDisplay').empty().html(dayta.explanation);
                    checkPermission(dayta.year, dayta.companyId, dayta.statusFlag);
                }
            }
        });
    }

    function checkPermission(year, companyId, statusFlag) {
        $('.chooseActionBtn').addClass('hidden');

        $.ajax({
            url: 'api/tfDhiCusSerList/checkPermission',
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                let myRoleId = $('#myRoleId').val();
                let creatorRoleId = $('#creatorRoleId').val();
                let adminRoleId = $('#adminRoleId').val();
                let submittedStage = $('#submittedStage').val();
                let approvedStage = $('#approvedStage').val();
                let revertedStage = $('#revertedStage').val();
                if (res.status === 1) {
                    let data = res.dto;
                    let stageRoleId = data.roleId;//3=Reviewer, 2=Creator
                    let stageStatus = data.status;//S=Submitted, R=Reverted, A=Approved
                    if (stageStatus === approvedStage) {
                        $('.chooseActionBtn').addClass('hidden');
                    }
                    if ((stageStatus === revertedStage) && (stageRoleId.toString() === myRoleId.toString())) {//Creator
                        $('.chooseActionBtn').removeClass('hidden');
                        setTimeout(function () {
                            checkPermissionByStatus(statusFlag);
                        }, 100);
                    }
                    if ((stageStatus === submittedStage) && (stageRoleId.toString() === myRoleId.toString())) {//Reviewer
                        $('.chooseActionBtn').removeClass('hidden');
                    }
                } else {
                    if (myRoleId.toString() === creatorRoleId.toString()) {
                        $('.chooseActionBtn').removeClass('hidden');
                    }
                }
                if (myRoleId.toString() === adminRoleId.toString()) {
                    $('.chooseActionBtn').removeClass('hidden');
                }
            }
        });
    }

    function checkPermissionByStatus(statusFlag) {
        if (statusFlag === 'C') {//C=Created, R= Reopen,X=Closed
            let myRoleId = $('#myRoleId').val();
            let creatorRoleId = $('#creatorRoleId').val();
            //user is creator
            if (myRoleId.toString() === creatorRoleId.toString()) {
                $('.chooseActionBtn').removeClass('hidden');
            }
        } else if (statusFlag === 'R') {//C=Created, R= Reopen,X=Closed
            let myRoleId = $('#myRoleId').val();
            let creatorRoleId = $('#creatorRoleId').val();
            //user is creator
            if (myRoleId.toString() === creatorRoleId.toString()) {
                $('.chooseActionBtn').removeClass('hidden');
            }
        } else if (statusFlag === 'X') {//C=Created, R= Reopen,X=Closed
            let myRoleId = $('#myRoleId').val();
            let creatorRoleId = $('#creatorRoleId').val();
            //user is creator
            if (myRoleId.toString() === creatorRoleId.toString()) {
                $('.chooseActionBtn').addClass('hidden');
            }
        }
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
                    document.getElementById('displayLogoComment').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                }
            }
        });
    }

    function getTargetEditHistory(targetId) {
        $.ajax({
            url: _baseURL() + 'getTargetEditHistory',
            type: "GET",
            data: {targetId: targetId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let historyCount = data.length;
                    let nofTimes = ' time';
                    if (historyCount > 1) {
                        $('.editHistoryDiv').removeClass('hidden');
                    } else {
                        $('.editHistoryDiv').addClass('hidden');
                    }
                    if (historyCount > 2) {
                        nofTimes = ' times';
                    }
                    $('.historyCount').text(parseInt(historyCount - 1) + nofTimes);
                    let editHistoryList = '';
                    for (let i = 0; i < historyCount; i++) {
                        let targetAuditId = data[i].targetAuditId;
                        let cmdFlag = data[i].cmdFlag;
                        let editedBy = data[i].editedBy;
                        let editedDate = data[i].editedDate;
                        let editedTime = data[i].editedTime;
                        let edited = 'edited';
                        if (cmdFlag === 'C') {
                            editedBy = data[i].addedBy;
                            edited = 'created';
                            editedDate = data[i].addedDate;
                            editedTime = data[i].addedTime;
                        }
                        editHistoryList = editHistoryList + '<li class="editHistoryLi cursor-pointer" title="Click to view this version">' +
                            '<input type="hidden" class="targetAuditId" value="' + targetAuditId + '">' +
                            '<small class="dropdown-item editedByDisplay" data-bs-toggle="modal" data-bs-target="#editHistoryModal">' +
                            editedBy + ' ' + edited + ' in ' + formatAsDate(editedDate) + ' at ' + editedTime +
                            '</small></li><div class="dropdown-divider"></div>';
                    }
                    $('.editHistoryUl').empty().append(editHistoryList);
                }
            }
        });
    }

    function getEditHistoryDetail() {
        $('.editHistoryDiv ul').on('click', 'li', function () {
            let targetAuditId = $(this).closest('.editHistoryUl .editHistoryLi').find('.targetAuditId').val();
            let editedBy = $(this).closest('.editHistoryUl .editHistoryLi').find('.editedByDisplay').text();
            $('.editHistoryModalTitle').empty().text(editedBy);
            $.ajax({
                url: _baseURL() + 'getEditHistoryDetail',
                type: "GET",
                data: {targetAuditId: targetAuditId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        let writeup = res.writeupDto;
                        let evalFormulaId = writeup.evalFormulaId;
                        let curYear = data.year;
                        let preYear = curYear - 1;

                        //display to read mode in distory modal
                        let targetDetail = ' <div class="row">' +
                            '<div class="col-md-4"><span>Serial No:</span> <strong>' + isNull(data.serialNo) + '</strong></div>' +
                            '<div class="col-md-8"><span>Activity:</span> <strong>' + data.activity + '</strong></div>' +
                            '</div>' +
                            '<div class="row">' +
                            '<div class="col-md-12"><span>Explanation:</span> <strong>' + data.explanation + '</strong></div>' +
                            '</div>';
                        $('.targetDetailHistory').empty().html(targetDetail);
                        getSubTargetHistory(data.targetAuditId);
                        let detailWriteup = '<br><div class="row"><strong>a) Background</strong></div>' +
                            '<div class="form-group">' + writeup.background + '</div>' +
                            '<div class="row"><strong> b) Target Output</strong></div>' +
                            '<div class="form-group">' + writeup.output + '</div>' +
                            '<div class="row"><strong> c) Risks Associated</strong></div>' +
                            '<div class="form-group">' + writeup.risks + '</div>' +
                            '<div class="row"><strong> d) Evaluation Method</strong></div>' +
                            '<div class="form-group">' + writeup.evalMethod + '</div>';
                        $('.detailWriteupHistory').empty().html(detailWriteup);
                        let evalFormula = ' 50% weight+(50% weight-((delayed month/target month)*50% weight))';
                        let other = 'Other';
                        if (evalFormulaId === 1) {
                            evalFormula = other;
                        }
                        if (evalFormulaId === 3) {
                            evalFormula = other;
                        }
                        $('.evalFormulaDisplayHistory').empty().html('<strong>Evaluation Formula: </strong>' + evalFormula);
                    }
                }
            });
        });
    }

    function getSubTargetHistory(targetAuditId) {
        $.ajax({
            url: _baseURL() + 'getSubTargetHistory',
            type: "GET",
            data: {targetAuditId: targetAuditId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let displayModeSubTarget = '';
                    for (let i = 0; i < data.length; i++) {
                        let isNegative = data[i].isNegative;
                        let isNegativeRow = '<div class="row">';
                        if (isNegative === 'Y') {
                             isNegativeRow = '<div class="row" style="color: red">';
                        }
                        displayModeSubTarget = displayModeSubTarget +
                             isNegativeRow +
                             '<div class="col-md-12"><span>Target Output:</span> <strong>' + data[i].subTarget + '</strong></divdiv>' +
                            '</div>' +
                            '<div class="row">' +
                            '<div class="col-md-4"><span>Deadline:</span> <strong>' + formatAsDate(data[i].deadline) + '</strong></div>' +
                            '<div class="col-md-4"><span>Weightage:</span> <strong>' + data[i].weightage + '</strong></div>' +
                             '<div class="col-md-2">' +
                            '</div>' +
                            '<div class="dropdown-divider"></div>' +
                            '</div>';
                    }
                    $('.subTargetDetailHistory').empty().html(displayModeSubTarget);
                }
            }
        });
    }

    function getTargetStatus(targetId) {
        $.ajax({
            url: _baseURL() + 'getTargetStatus',
            type: "GET",
            data: {targetId: targetId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let statusCount = data.length;
                    let targetStatusLi = '';
                    $('.moreStatusUl').empty();
                    let latestStatus = data[0].statusFlag;
                    let latestCreatedBy = data[0].createdBy;
                    let latestCreatedDate = data[0].createdDate;
                    let latestCreatedTime = data[0].createdTime;
                    let latestOpen = 'Created';//C=created, R= Reopen,X=Closed
                    let latestTextMsg;
                    if (latestStatus === 'C') {
                        latestTextMsg = '<span class="badge bg-warning">' + latestOpen + ' </span>' + ' by ' + latestCreatedBy + ' in ' + formatAsDate(latestCreatedDate) + ' at ' + latestCreatedTime;
                    }
                    if (latestStatus === 'X') {
                        latestOpen = 'Closed';
                        latestTextMsg = '<span class="badge bg-success">' + latestOpen + ' </span>' + ' by ' + latestCreatedBy + ' in ' + formatAsDate(latestCreatedDate) + ' at ' + latestCreatedTime;
                    }
                    if (latestStatus === 'R') {
                        latestOpen = 'Reopened';
                        latestTextMsg = '<span class="badge bg-danger">' + latestOpen + ' </span>' + ' by ' + latestCreatedBy + ' in ' + formatAsDate(latestCreatedDate) + ' at ' + latestCreatedTime;

                    }
                    $('.latestTextMsg').empty().append(latestTextMsg);

                    for (let i = 1; i < statusCount; i++) {
                        let statusFlag = data[i].statusFlag;
                        let createdBy = data[i].createdBy;
                        let createdDate = data[i].createdDate;
                        let createdTime = data[i].createdTime;
                        let statusFlagName = '<small class="badge bg-warning">Created</small>';
                        if (statusFlag === 'X') {
                            statusFlagName = '<small class="badge bg-success">Closed</small>';
                        }
                        if (statusFlag === 'R') {
                            statusFlagName = '<small class="badge bg-danger">Reopened</small>';
                        }
                        let textMsg = statusFlagName + ' by ' + createdBy + ' in ' + formatAsDate(createdDate) + ' at ' + createdTime;
                        targetStatusLi = ' <li class="dropdown-item"><small >' + textMsg + '</small></li><div class="dropdown-divider"></div>';
                        $('.moreStatusUl').append(targetStatusLi);
                        $('.moreHere').removeClass('hidden');
                    }
                    $('.countStatus').html('&nbsp;&nbsp;' + parseInt(statusCount - 1));
                }
            }
        });
    }

    function isNull(data) {
        return data === null ? '' : data;
    }

    function getAttachments() {
        //edit mode
        $('#subTargetTable tbody').on('click', 'tr>td div div .attachments', function () {
            let row = $(this).closest('tr');
            let subTargetId = row.find(".subTargetId").val();
            let targetName = row.find(".subTarget").val();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            displayFilesEditMode(subTargetId);
        });
        //display mode
        $('#displayTable tbody').on('click', 'tr>td .attachments', function () {
            let row = $(this).closest('tr');
            let subTargetId = row.find(".subTargetId").text();
            let targetName = row.find(".subTarget").text();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            displayFilesDisplayMode(subTargetId);
        });
    }

    function displayFilesEditMode(subTargetId) {
        $('.fileUl').empty();
        let hasDeleteRole = $('#hasDeleteRole').val();
        let btnView = '<span class="viewFile infoMsg" title="Click to view">&nbsp;&nbsp;<i class="fa fa-eye cursor-pointer"></i></span>';
        // let hasDeleteRole = '';//TODO: to be used this one after incorporation security
        let btnDelete = '<span class="deleteFile errorMsg" title="Click to delete">&nbsp;&nbsp;<i class="fa fa-trash cursor-"></i></span>';
        if (hasDeleteRole.toString() === 'true') {
            btnDelete = '<span class="deleteFile errorMsg" title="Click to delete">&nbsp;&nbsp;<i class="fa fa-trash cursor-"></i></span>';
        }
        $.ajax({
            url: _baseURL() + 'getAttachments',
            type: "GET",
            data: {subTargetId: subTargetId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let attachmentCount = data.length;
                    let attachment = 'Attachment'
                    if (attachmentCount === 1) {
                        $('.attachmentModalTitle').empty().html(attachmentCount + ' ' + '<i class="fa fa-paperclip"></i>' + attachment);
                    }
                    if (attachmentCount > 1) {
                        attachment = 'Attachments'
                        $('.attachmentModalTitle').empty().html(attachmentCount + ' ' + '<i class="fa fa-paperclip"></i>' + attachment);
                    }
                    for (let i = 0; i < data.length; i++) {
                        let fileId = data[i].fileId;
                        let fileName = data[i].fileName;
                        let fileSize = data[i].fileSize;
                        fileSize = '(' + parseInt(fileSize / 10000) + 'MB)';
                        fileName = '<span class="fileName cursor-pointer" title="Click to download">' + '&nbsp;&nbsp;' + fileName + fileSize + '</span>'
                        let fileExtension = data[i].fileExtension;
                        let fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + btnDelete + '</span></li>';
                        if (fileExtension.toUpperCase() === 'DOCX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-word-o infoMsg"></i>' + fileName + btnDelete + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'JPG' || fileExtension.toUpperCase() === 'JPEG'
                            || fileExtension.toUpperCase() === 'PNG') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-image-o infoMsg"></i>' + fileName + btnView + btnDelete + '</span></li>';
                        }

                        if (fileExtension.toUpperCase() === 'PDF') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + btnView + btnDelete + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'PPTX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-powerpoint-o errorMsg"></i>' + fileName + btnDelete + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'XLSX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-excel-o successMsg"></i>' + fileName + btnDelete + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'TXT') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-text-o"></i>' + fileName + btnDelete + '</span></li>';
                        }
                        $('.fileUl').append(fileList);
                    }
                }
            }
        });
    }

    function displayFilesDisplayMode(subTargetId) {
        $('.fileUl').empty();
        let btnView = '<span class="viewFile infoMsg" title="Click to view">&nbsp;&nbsp;<i class="fa fa-eye cursor-pointer"></i></span>';
        $.ajax({
            url: _baseURL() + 'getAttachments',
            type: "GET",
            data: {subTargetId: subTargetId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let attachmentCount = data.length;
                    let attachment = 'Attachment'
                    if (attachmentCount === 1) {
                        $('.attachmentModalTitle').empty().html(attachmentCount + ' ' + '<i class="fa fa-paperclip"></i>' + attachment);
                    }
                    if (attachmentCount > 1) {
                        attachment = 'Attachments'
                        $('.attachmentModalTitle').empty().html(attachmentCount + ' ' + '<i class="fa fa-paperclip"></i>' + attachment);
                    }
                    for (let i = 0; i < data.length; i++) {
                        let fileId = data[i].fileId;
                        let fileName = data[i].fileName;
                        let fileSize = data[i].fileSize;
                        fileSize = '(' + parseInt(fileSize / 10000) + 'MB)';
                        fileName = '<span class="fileName cursor-pointer" title="Click to download">' + '&nbsp;&nbsp;' + fileName + fileSize + '</span>'
                        let fileExtension = data[i].fileExtension;
                        let fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + '</span></li>';
                        if (fileExtension.toUpperCase() === 'DOCX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-word-o infoMsg"></i>' + fileName + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'JPG' || fileExtension.toUpperCase() === 'JPEG'
                            || fileExtension.toUpperCase() === 'PNG') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-image-o compactTarget"></i>' + fileName + btnView + '</span></li>';
                        }

                        if (fileExtension.toUpperCase() === 'PDF') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-pdf-o warningMsg"></i>' + fileName + btnView + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'PPTX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-powerpoint-o errorMsg"></i>' + fileName + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'XLSX') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-excel-o successMsg"></i>' + fileName + '</span></li>';
                        }
                        if (fileExtension.toUpperCase() === 'TXT') {
                            fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-text-o"></i>' + fileName + '</span></li>';
                        }
                        $('.fileUl').append(fileList);
                    }
                }
            }
        });
    }

    function deleteFile() {
        $('.fileListDiv ul').on('click', 'li .deleteFile', function () {
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
                                        successMsg(res.text, true);
                                        displayFilesEditMode();
                                        getTarget();
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
                    window.open(window.location.origin + '/' + url, '_blank');
                }
            });
        });
    }

    function downloadFile() {
        $('.fileListDiv ul').on('click', 'li span.fileName', function () {
            let fileId = $(this).closest('.fileUl .fileLi').find('.fileId').val();
            let url = _baseURL() + 'downloadFile/' + fileId;
            $.ajax({
                url: url,
                type: 'GET',
                data: {fileId: fileId},
                success: function () {
                    window.location.href = url;
                }
            });
        });
    }

    function remarkModalEditMode() {
        $('#subTargetTable tbody').on('click', 'tr>td .btnAddRemarkModal', function () {
            let row = $(this).closest('tr');
            let subTargetId = row.find(".subTargetId").val();
            let targetName = row.find(".subTarget").val();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            $("#subTargetId").val('').val(subTargetId);
            $('#remark').summernote('code', '', initEditor());
            $.ajax({
                url: _baseURL() + 'getRemark',
                type: 'GET',
                data: {subTargetId: subTargetId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        $(".btnEditRemark").removeClass('hidden');
                        $(".btnAddRemark").addClass('hidden');
                        $("#remarkId").val('').val(data.remarkId);
                        $('#remark').summernote('code', data.remark, initEditor());
                    } else {
                        $(".btnEditRemark").addClass('hidden');
                        $(".btnAddRemark").removeClass('hidden');
                    }
                }
            });
        });
    }

    function remarkModalDisplayMode() {
        $('#displayTable tbody').on('click', 'tr>td .btnReadRemarkModal', function () {
            let row = $(this).closest('tr');
            let subTargetId = row.find(".subTargetId").text();
            let targetName = row.find(".subTarget").text();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            $('#displayRemark').empty();
            $.ajax({
                url: _baseURL() + 'getRemark',
                type: 'GET',
                data: {subTargetId: subTargetId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        let remark = data.remark;
                        $('#displayRemark').html(remark);
                    }
                }
            });
        });
    }

    function addRemark() {
        $('.btnAddRemark').on('click', function () {
            globalLib.isFormValid(remarkFormId);
            if (remarkFormId.valid()) {
                isSubmitted = true;
                $('.btnAddRemark').attr('disabled', true);
                let data = new FormData($('.remarkFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'addRemark',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.remarkFormId')[0].reset();
                            $('.btnAddRemark').attr("disabled", false);
                            getTarget();
                            $('#remarkModalEditMode').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnAddRemark').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnAddRemark').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function editRemark() {
        $('.btnEditRemark').on('click', function () {
            globalLib.isFormValid(remarkFormId);
            if (remarkFormId.valid()) {
                isSubmitted = true;
                $('.btnEditRemark').attr('disabled', true);
                let data = new FormData($('.remarkFormId')[0]);
                $.ajax({
                    url: _baseURL() + 'editRemark',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.remarkFormId')[0].reset();
                            $('.btnEditRemark').attr("disabled", false);
                            getTarget();
                            $('#remarkModalEditMode').modal('hide');
                            isSubmitted = false;
                        } else {
                            $('.btnEditRemark').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnEditRemark').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    let targetActivityList = [];

    function getPreData() {
        //to get financial KPI list for typeahed
        $.ajax({
            url: "api/tfDhiCusSerAddTarget/getTargetActivity",
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

    function addComment() {
        $('.btnAddComment').on('click', function () {
            globalLib.isFormValid(commentFormId);
            if (commentFormId.valid()) {
                isSubmitted = true;
                $('.btnAddComment').attr('disabled', true);
                let targetId = $('#targetId').val();
                let data = new FormData($('.commentFormId')[0]);
                data.append('targetId', targetId);
                $.ajax({
                    url: _baseURL() + 'addComment',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.commentFormId')[0].reset();
                            $('.btnAddComment').attr("disabled", false);
                            $('#comments').summernote('code', '');
                            getComment();
                            isSubmitted = false;
                        } else {
                            $('.btnAddComment').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnAddComment').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function getComment() {
        let targetId = $('#targetId').val();
        $.ajax({
            url: _baseURL() + 'getCusSerComment',
            type: 'GET',
            data: {targetId: targetId},
            success: function (res) {
                if (res.status === 1) {
                    $('#commentUl').empty();
                    let data = res.dto;
                    let commentCount = data.length;
                    let commentTitle = commentCount + '&nbsp;&nbsp;<i class="fa fa-comment"></i>Comment';
                    if (commentCount > 1) {
                        commentTitle = commentCount + '&nbsp;&nbsp;<i class="fa fa-comments"></i>Comments';
                    }
                    $('.commentTitle').empty().html(commentTitle);
                    $('.commentCount').text(data.length);
                    for (let i = 0; i < data.length; i++) {
                        let commentId = data[i].commentId;
                        let comments = data[i].comments;
                        let commentedDate = data[i].commentedDate;
                        let commentTime = data[i].commentTime;
                        let commentBy = data[i].commentBy;
                        let commentByRoleId = data[i].commentByRoleId;
                        let reviewerRoleId = $('#reviewerRoleId').val();
                        let creatorRoleId = $('#creatorRoleId').val();
                        let avatarContent = '<div class="avatar bg-green me-3">' +
                            '<span class="avatar-content">' + getShortName(commentBy) + '</span>' +
                            '</div>';
                        if (commentByRoleId.toString() === reviewerRoleId.toString()) {
                            avatarContent = '<div class="avatar bg-warning me-3">' +
                                '<span class="avatar-content">' + getShortName(commentBy) + '</span>' +
                                '</div>';
                        }
                        if (commentByRoleId.toString() === creatorRoleId.toString()) {
                            avatarContent = '<div class="avatar bg-dark me-3">' +
                                '<span class="avatar-content">' + getShortName(commentBy) + '</span>' +
                                '</div>';
                        }
                        let commentList = '<div class="list-group list-group-item list-group-item-action">' +
                            '                                        <div class="d-flex w-100 justify-content-between">' +
                            '                                            <ul class="list-inline d-flex mb-0">' +
                            '                                                <li class="d-flex align-items-center">' + avatarContent +
                            '                                                </li>' +
                            '                                                <li class="d-flex align-items-center">' +
                            '                                                    <span class="pull-left">' + commentBy + '</span>' + "&nbsp;&nbsp;" + '<small>commented in ' + formatAsDate(commentedDate) + ' at ' + commentTime + '</small>' +
                            '                                                </li>' +
                            '                                            </ul>' +
                            '                                        </div>' +
                            '                                        <p class="mb-1">' + comments + '</p>' +
                            '                                    </div>';
                        $('#commentUl').append(commentList);
                    }
                }
            }
        });
    }

    function closeOrReopenTarget() {
        //close target
        $('.actionBtnClose').on('click', function () {
            let targetId = $('#targetId').val();
            $.confirm({
                title: 'Confirmation',
                content: 'Are you sure you want to close?  <center class="small">Creator won\'t able make changes once closed.</center>',
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
                                url: _baseURL() + 'closeTarget',
                                type: 'POST',
                                data: {targetId: targetId},
                                success: function (res) {
                                    if (res.status === 1) {
                                        successMsg(res.text);
                                        getTarget();
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
        //Reopen target
        $('.actionBtnReopen').on('click', function () {
            let targetId = $('#targetId').val();

            let audio = new Audio('assets/sounds/info/1.mp3');
            audio.play();
            $.confirm({
                title: 'Confirmation',
                content: 'Are you sure you want to reopen?  <center class="small">Creator will able make changes once reopened.</center>',
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
                                url: _baseURL() + 'reopenTarget',
                                type: 'POST',
                                data: {targetId: targetId},
                                success: function (res) {
                                    if (res.status === 1) {
                                        successMsg(res.text);
                                        getTarget();
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

    function btnAddMore() {
        $('.btnAddMore').on('click', function () {
            let dynamicId = generateUniqueUiId();
            let subTarget = '<tr><td>' +
                '<div class="dropdown-divider"></div>' +
                '<div class="row">' +
                '<div class="col-md-2">' +
                '<label for="subTarget' + dynamicId + '" class="col-form-label required pull-right">Target Output</label>' +
                '</div>' +
                '<div class="col-md-6 form-group">' +
                '<textarea id="subTarget' + dynamicId + '" class="form-control square field" name="subTargetDtos[0].subTarget" autocomplete="off" required onkeyup="textAreaAdjust(this)"></textarea>' +
                '<div class="form-check">' +
                '<div class="pull-left custom-control custom-checkbox">' +
                '<input type="checkbox" class="form-check-input form-check-danger cursor-pointer"  id="isNegative' + dynamicId + '" name="subTargetDtos[0].isNegativeBoolean">' +
                '<label for="isNegative' + dynamicId + '"  class="cursor-pointer"><small style="text-transform: none;"> Mark as negative target</small></label>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '<div class="col-md-2">' +
                '<label for="deadline' + dynamicId + '" class="col-form-label required pull-right"> Deadline</label>' +
                '</div> <div class="col-md-2 form-group">' +
                '<input type="text" id="deadline' + dynamicId + '"  class="form-control datepicker dynamic square field" name="subTargetDtos[0].deadline" required autocomplete="off">' +
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
                '<div class="col-md-4 form-group">' +
                '<input type="file" id="attachedFiles' + dynamicId + '" name="subTargetDtos[0].attachedFiles" ' +
                'accept="image/jpeg,image/png,.doc,.docx,.pdf,.xlsx,.xls"  multiple  class="form-control square field cursor-pointer"/>' +
                '<small><i class="fa fa-info-circle infoMsg"></i> Note: Accept PDF/DOCX/XLXS/PNG/JPG only</small>' +
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
            let subTargetId = selectedRow.find(".subTargetId").val();
            if (subTargetId !== '' && typeof subTargetId !== 'undefined') {
                let audio = new Audio('assets/sounds/info/1.mp3');
                audio.play();
                $.confirm({
                    title: 'Confirmation',
                    content: 'Are you sure you want to delete permanently?  <center class="small">You cannot recover once deleted.</center>',
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
                                    url: _baseURL() + 'deleteSubTarget',
                                    type: 'POST',
                                    data: {subTargetId: subTargetId},
                                    success: function (res) {
                                        if (res.status === 1) {
                                            successMsg(res.text, true);
                                            let targetAuditId = res.targetAuditId;
                                            let targetId = $('#targetId').val();
                                            let newUrlIS = window.location.origin + '/tfDhiCusSerEditTarget?taId=' + targetAuditId + '&&tId=' + targetId
                                            history.pushState({}, null, newUrlIS);
                                            $('#targetAuditId').val(targetAuditId);
                                            $('.btnCancelDiv').addClass('hidden');
                                            $('.editMode').addClass('hidden');
                                            $('.displayMode').removeClass('hidden');
                                            getTarget();
                                            selectedRow.remove();
                                        } else {
                                            warningMsg(res.text);
                                        }
                                    }
                                });
                            }
                        },
                    }
                });
            } else {
                selectedRow.remove();
            }

            selectedRow.removeClass('selected');

            targetFormIndexing($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
            targetFormIndexing2($('#subTargetTable tbody'), $('#subTargetTable tbody').find('tr'));
        });
    }

    function deleteTarget() {
        $('.actionBtnDelete').on('click', function () {
            let targetId = $('#targetId').val();
            let audio = new Audio('assets/sounds/info/1.mp3');
            audio.play();
            $.confirm({
                title: 'Confirmation',
                content: 'Are you sure you want to delete permanently?  <center class="small">You cannot recover once deleted.</center>',
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
                                url: _baseURL() + 'deleteTarget',
                                type: 'POST',
                                data: {targetId: targetId},
                                success: function (res) {
                                    if (res.status === 1) {
                                        successMsg(res.text, true);
                                        getTarget();
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

    function actionBtnEdit() {
        $('.actionBtnEdit').on('click', function () {
            $('.btnCancelDiv').removeClass('hidden');
            $('.editMode').removeClass('hidden');
            $('.displayMode').addClass('hidden');
        });
        $('.btnCancelEdit').on('click', function () {
            $('.btnCancelDiv').addClass('hidden');
            $('.editMode').addClass('hidden');
            $('.displayMode').removeClass('hidden');
        });
    }

    function editTarget() {
        $('.btnEditTarget').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnEditTarget').attr('disabled', true);
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

                $.ajax({
                    url: _baseURL() + 'editTarget',
                    type: "POST",
                    data: data,
                    contentType: false,
                    processData: false,
                    success: function (res) {
                        if (res.status === 1) {
                            successMsg(res.text);
                            $('.targetForm')[0].reset();
                            $('.btnEditTarget').attr("disabled", false);
                            let targetAuditId = res.targetAuditId;
                            let targetId = $('#targetId').val();
                            let newUrlIS = window.location.origin + '/tfDhiCusSerEditTarget?taId=' + targetAuditId + '&&tId=' + targetId
                            history.pushState({}, null, newUrlIS);
                            $('#targetAuditId').val(targetAuditId);
                            $('.btnCancelDiv').addClass('hidden');
                            $('.editMode').addClass('hidden');
                            $('.displayMode').removeClass('hidden');
                            getTarget();
                            isSubmitted = false;
                        } else {
                            $('.btnEditTarget').attr("disabled", false);
                            warningMsg(res.text);
                            isSubmitted = false;
                        }
                    }, complete: function (res) {
                        $('.btnEditTarget').attr("disabled", false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    function toggleLabel(year) {
        let preYear = year - 1;
        $('.headingYear').text(year);
    }

    function getAllocatedWt(year, companyId) {
        $.ajax({
            url: 'api/tfDhiCusSerAddTarget/getAllocatedWt',
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

    function initEditor() {
        $('#remark').summernote(configEditor);
        $('#comments').summernote(configEditor);
        $('#background').summernote(configEditor);
        $('#output').summernote(configEditor);
        $('#risks').summernote(configEditor);
        $('#evalMethod').summernote(configEditor);
    }

    function checkCommentPolicy() {
        $("#comments").on("summernote.keyup", function () {
            let comments = $('#comments').val();
            let regX = /(<([^>]+)>)/ig;
            if (comments.replace(regX, "").length === 1) {
                $.ajax({
                    url: 'api/common/isNewComment',
                    type: "GET",
                    success: function (res) {
                        if (res === 0) {
                            document.getElementById("commentPopupId").style.display = "block";
                        }
                    }
                });
            }
        });
    }

    function getCommentPolicy() {
        $('.btnCommentPolicy').on('click', function () {
            $.ajax({
                url: 'api/commentPolicy/getCommentPolicy',
                type: "GET",
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        let contents = data.contents;
                        $('#policyDisplay').empty().html(contents);
                    } else {
                        $('#policyDisplay').empty().html('Comment Policy not available');
                    }
                }
            });
        });
    }

    function btnGoBack() {
        $('.btnGoBack').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiCusSerList?yId=' + year + '&&cId=' + companyId
        });
    }

    return {
         initEditor: initEditor
        , getTarget: getTarget
        , getPreData: getPreData
        , autoSuggest: autoSuggest
        , editTarget: editTarget
        , actionBtnEdit: actionBtnEdit
        , toggleLabel: toggleLabel
        , getAllocatedWt: getAllocatedWt
        , getAttachments: getAttachments
        , deleteFile: deleteFile
        , viewFile: viewFile
        , downloadFile: downloadFile
        , remarkModalEditMode: remarkModalEditMode
        , remarkModalDisplayMode: remarkModalDisplayMode
        , addRemark: addRemark
        , editRemark: editRemark
        , addComment: addComment
        , getComment: getComment
        , closeOrReopenTarget: closeOrReopenTarget
        , btnAddMore: btnAddMore
        , deleteSubTarget: deleteSubTarget
        , deleteTarget: deleteTarget
        , getEditHistoryDetail: getEditHistoryDetail
        , checkCommentPolicy: checkCommentPolicy
        , getCommentPolicy: getCommentPolicy
        , btnGoBack: btnGoBack
    }
})
();
$(document).ready(
    function () {
        let year = $('#year').val();
        let companyId = $('#companyId').val();
        tfDhiCusSerEditTarget.initEditor();
        tfDhiCusSerEditTarget.getTarget();
        tfDhiCusSerEditTarget.getPreData();
        tfDhiCusSerEditTarget.autoSuggest();
        tfDhiCusSerEditTarget.toggleLabel(year);
        tfDhiCusSerEditTarget.actionBtnEdit();
        tfDhiCusSerEditTarget.editTarget();
        tfDhiCusSerEditTarget.getAllocatedWt(year, companyId);
        tfDhiCusSerEditTarget.getAttachments();
        tfDhiCusSerEditTarget.deleteFile();
        tfDhiCusSerEditTarget.viewFile();
        tfDhiCusSerEditTarget.downloadFile();
        tfDhiCusSerEditTarget.remarkModalEditMode();
        tfDhiCusSerEditTarget.remarkModalDisplayMode();
        tfDhiCusSerEditTarget.addRemark();
        tfDhiCusSerEditTarget.editRemark();
        tfDhiCusSerEditTarget.addComment();
        tfDhiCusSerEditTarget.getComment();
        tfDhiCusSerEditTarget.closeOrReopenTarget();
        tfDhiCusSerEditTarget.btnAddMore();
        tfDhiCusSerEditTarget.deleteSubTarget();
        tfDhiCusSerEditTarget.deleteTarget();
        tfDhiCusSerEditTarget.getEditHistoryDetail();
        tfDhiCusSerEditTarget.checkCommentPolicy();
        tfDhiCusSerEditTarget.getCommentPolicy();
        tfDhiCusSerEditTarget.btnGoBack();
    });
