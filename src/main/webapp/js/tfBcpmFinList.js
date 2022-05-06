tfBcpmFinList = (function () {
    "use strict";
    let form = $('#targetListForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/tfBcpmFinList/';
    }

    function triggerSearch() {
        let year = $('#yearHidden').val();
        let companyId = $('#companyIdHidden').val();
        $('#year').val(year).trigger("chosen:updated");
        $('#companyId').val(companyId).trigger("chosen:updated");
        // $(".btnSearch").trigger("click");
        searchTarget(year, companyId);
        toggleTheadLbl(year);
    }

    function navigateToTfBcpmFinAddTarget() {
        $('.btnAddNewTarget').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmFinAddTarget?yId=' + year + '&&cId=' + companyId
        });
    }

    function btnSearch() {
        $('.btnSearch').on('click', function () {
            $('.actionBtns').addClass('hidden');
            $('.moreHere').addClass('hidden');
            $('.moreStagesUl').empty();
            $('.latestTextMsg').empty();
            $('#targetListTable').dataTable().fnDestroy();
            $('#targetListTable tbody').empty();
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            searchTarget(year, companyId);
            $('.stageSearch').empty().text('');
        });
    }

    function searchTarget(year, companyId) {
        $.ajax({
            url: _baseURL() + 'searchTarget',
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                toggleTheadLbl(year);
                if (res.status === 1) {
                    let data = res.dto;
                    displaySearchResult(data);
                }
            }
        });
        getStages(year, companyId);
        checkPermission(year, companyId);
    }

    function calculateTotalWeight() {
        let weightTotal = 0;
        $('#targetListTable').find('tbody tr').each(function () {
            let weight;
            let row = $(this).closest('tr');
            let selectedRow = row.addClass('selected');
            weight = selectedRow.find('.weightage').text();
            if (($.isNumeric(weight))) {
                if (weight > 0) {
                    weightTotal = parseFloat(weightTotal) + parseFloat(weight);
                }
            }
            row.removeClass('selected');
        });
        $('.totalWeight').empty().text(+weightTotal.toFixed(2));
    }

    function displaySearchResult(data, isStage) {
        $('#targetListTable').dataTable().fnDestroy();
        $('#targetListTable tbody').empty();
        let columnDef = [
            {data: 'serialNo', class: "serialNo align-middle"},
            {
                data: {targetAuditId: 'targetAuditId'}, class: "hidden align-middle",
                mRender: function (data) {
                    return '<input type="hidden" value="' + data.targetAuditId + '" required name="targetStageDetailDtos[0].targetAuditId" class="targetAuditId"/>';

                }
            },
            {data: 'targetId', class: "targetId hidden align-middle"},
            {
                data: {commentCount: 'commentCount', statusFlag: 'statusFlag', finKpi: 'finKpi'},
                class: "align-middle",
                "mRender": function (data) {
                    let statusFlag = data.statusFlag;
                    let commentCount = data.commentCount;
                    let finKpi = '<span class="cus-link cursor-pointer finKpi targetName" title="Click to edit target">' + data.finKpi + '</span>';
                    let comment = '';
                    let status = '<small class="badge bg-warning">Created</small>';
                    if (statusFlag === 'X') {
                        status = '<small class="badge bg-success">Closed</small>';
                    }
                    if (statusFlag === 'R') {
                        status = '<small class="badge bg-danger">Reopened</small>';
                    }
                    if (commentCount.toString() === "1") {
                        comment = '<small title="Click to view" class="cursor-pointer btnViewComment" data-bs-toggle="modal" data-bs-target="#commentModal">' + commentCount + ' ' + '<i class="fa fa-comment"></i>Comment</small>';
                    }
                    if (commentCount > 1) {
                        comment = '<small title="Click to view" class="cursor-pointer btnViewComment" data-bs-toggle="modal" data-bs-target="#commentModal">' + commentCount + ' ' + '<i class="fa fa-comments"></i>Comments</small>';
                    }
                    return finKpi + '<br>' + status + comment
                }
            },
            {data: 'preYearActual', class: "preYearActual align-middle"},
            {data: 'preYearMidActual', class: "preYearMidActual align-middle"},
            {data: 'preYearTarget', class: "preYearTarget align-middle"},
            {data: 'curYearBudget', class: "curYearBudget align-middle"},
            {data: 'curYearTarget', class: "curYearTarget align-middle"},
            {data: 'curYearMidTarget', class: "curYearMidTarget align-middle"},
            {data: 'weightage', class: "weightage align-middle"},
            {
                data: {attachmentCount: 'attachmentCount'},
                class: "align-middle",
                "mRender": function (data) {
                    let attachmentCount = data.attachmentCount;
                    let attachment = '';

                    if (attachmentCount === 1) {
                        attachment = '<small title="Click to view" class="cursor-pointer btnAttachment" data-bs-toggle="modal" data-bs-target="#attachmentModal">' + attachmentCount + '<i class="fa fa-paperclip"></i>Attachment</small>'
                    }
                    if (attachmentCount > 1) {
                        attachment = '<small title="Click to view" class="cursor-pointer btnAttachment" data-bs-toggle="modal" data-bs-target="#attachmentModal">' + attachmentCount + '<i class="fa fa-paperclip"></i>Attachments</small>'
                    }
                    let btnWriteup = '<small title="Click to view" id="btnWriteup" class="link btnWriteup" data-bs-toggle="modal" data-bs-target="#writeupModal"> Detail Writeup</small>';
                    return btnWriteup + '<br>' + attachment;
                }
            },
        ];
        $('#targetListTable').DataTable({
            data: data
            , columns: columnDef
            , pageLength: 100
        });
        formIndexing($('#targetListTable tbody'), $('#targetListTable tbody').find('tr'));
        if (isStage) {
            $('#targetListTable tbody').find('td').addClass('bg-history');
        }
        calculateTotalWeight();
    }

    function checkPermission(year, companyId) {
        $('.actionBtns').addClass('hidden');
        $.ajax({
            url: _baseURL() + 'checkPermission',
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                let myRoleId = $('#myRoleId').val();
                let creatorRoleId = $('#creatorRoleId').val();
                let submittedStage = $('#submittedStage').val();
                let approvedStage = $('#approvedStage').val();
                let revertedStage = $('#revertedStage').val();
                if (res.status === 1) {
                    let data = res.dto;
                    let stageRoleId = data.roleId;//3=Reviewer, 2=Creator
                    let stageStatus = data.status;//S=Submitted, R=Reverted, A=Approved
                    if (stageStatus === approvedStage) {
                        $('.actionBtns').addClass('hidden');
                    }
                    if ((stageStatus === revertedStage) && (stageRoleId.toString() === myRoleId.toString())) {//Creator
                        $('.actionBtns').removeClass('hidden');
                    }
                    if ((stageStatus === submittedStage) && (stageRoleId.toString() === myRoleId.toString())) {//Reviewer
                        $('.actionBtns').removeClass('hidden');
                    }
                } else {
                    if (myRoleId.toString() === creatorRoleId.toString()) {
                        $('.actionBtns').removeClass('hidden');
                    }
                }
            }
        });
    }

    function searchByStage() {
        $('.stageDiv ul').on('click', 'li', function () {
            let stageId = $(this).closest('.moreStagesUl .stagesLi').find('.stageId').val();
            let actionTakenBy = $(this).closest('.moreStagesUl .stagesLi').find('.actionTakenBy').text();
            $('.actionBtns').addClass('hidden');
            $.ajax({
                url: _baseURL() + 'searchByStage',
                type: "GET",
                data: {stageId: stageId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        displaySearchResult(data, true);
                        $('.stageSearch').empty().text(actionTakenBy);
                    }
                }
            });
        });
    }

    function getStages(year, companyId) {
        $.ajax({
            url: _baseURL() + 'getStages',
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let stagesCount = data.length;
                    let targetStatusLi = '';
                    $('.moreStagesUl').empty();
                    let latestStatus = data[0].status;
                    let latestActionTakenBy = data[0].actionTakenBy;
                    let latestActionTakenDate = data[0].actionTakenDate;
                    let latestActionTakenTime = data[0].actionTakenTime;
                    let latestStageName = 'Submitted';//S=Submitted, R= Reverted,A=Approved
                    let latestTextMsg;
                    if (latestStatus === 'S') {
                        latestTextMsg = '<span class="badge bg-warning">' + latestStageName + ' </span>' + ' by ' + latestActionTakenBy + ' in ' + formatAsDate(latestActionTakenDate) + ' at ' + latestActionTakenTime;
                    }
                    if (latestStatus === 'R') {
                        latestStageName = 'Reverted';
                        latestTextMsg = '<span class="badge bg-danger">' + latestStageName + ' </span>' + ' by ' + latestActionTakenBy + ' in ' + formatAsDate(latestActionTakenDate) + ' at ' + latestActionTakenTime;
                    }
                    if (latestStatus === 'A') {
                        latestStageName = 'Approved';
                        latestTextMsg = '<span class="badge bg-success">' + latestStageName + ' </span>' + ' by ' + latestActionTakenBy + ' in ' + formatAsDate(latestActionTakenDate) + ' at ' + latestActionTakenTime;

                    }
                    $('.latestTextMsg').empty().append(latestTextMsg);

                    for (let i = 1; i < stagesCount; i++) {
                        let stageId = data[i].stageId;
                        let status = data[i].status;
                        let latestActionTakenBy = data[i].actionTakenBy;
                        let latestActionTakenDate = data[i].actionTakenDate;
                        let latestActionTakenTime = data[i].actionTakenTime;
                        let stagesName = '<small class="badge bg-warning">Submitted</small>';
                        if (status === 'A') {
                            stagesName = '<small class="badge bg-success">Approved</small>';
                        }
                        if (status === 'R') {
                            stagesName = '<small class="badge bg-danger">Reverted</small>';
                        }
                        let textMsg = stagesName + ' by ' + latestActionTakenBy + ' in ' + formatAsDate(latestActionTakenDate) + ' at ' + latestActionTakenTime;
                        targetStatusLi = '<li class="dropdown-item cursor-pointer stagesLi" title="Click to view this version"><input type="hidden" value="' + stageId + '" class="stageId"><small class="actionTakenBy">' + textMsg + '</small></li><div class="dropdown-divider"></div>';
                        $('.moreStagesUl').append(targetStatusLi);
                        $('.moreHere').removeClass('hidden');
                    }
                    $('.countStage').html('&nbsp;&nbsp;' + parseInt(stagesCount - 1));
                }
            }
        });
    }

    function toggleTheadLbl(year) {
        let preYear = year - 1;
        $('.headingYear').text(year);

        $('.preYearActualLbl').text(preYear);
        $('.preYearMidActualLbl').text(preYear);
        $('.preYearTargetLbl').text(preYear);

        $('.curYearBudgetLbl').text(year);
        $('.curYearTargetLbl').text(year);
        $('.curYearMidTargetLbl').text(year);
    }

    function navigateToEditTarget() {
        $('#targetListTable tbody').on('click', 'tr .finKpi', function () {
            let row = $(this).closest('tr');
            let targetAuditId = row.find(".targetAuditId").val();
            let targetId = row.find(".targetId").text();
            window.location.href = 'tfBcpmFinEditTarget?taId=' + targetAuditId + '&&tId=' + targetId
        });
    }

    function getWriteup() {
        $('#targetListTable tbody').on('click', 'tr .btnWriteup', function () {
            let row = $(this).closest('tr');
            let targetAuditId = row.find(".targetAuditId").val();
            let targetName = row.find(".targetName").text();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');

            $.ajax({
                url: _baseURL() + 'getWriteup',
                type: 'GET',
                data: {targetAuditId: targetAuditId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        let detailWriteup = '<div class="row"><strong>a) Background</strong></div>' +
                            '<div class="form-group">' + data.background + '</div>' +
                            '<div class="row"><strong> b) Target Output</strong></div>' +
                            '<div class="form-group">' + data.output + '</div>' +
                            '<div class="row"><strong> c) Risks Associated</strong></div>' +
                            '<div class="form-group">' + data.risks + '</div>' +
                            '<div class="row"><strong> d) Evaluation Method</strong></div>' +
                            '<div class="form-group">' + data.evalMethod + '</div>';
                        $('.detailWriteup').empty().html(detailWriteup);
                        let evalFormulaId = data.evalFormulaId;
                        let evalFormula = '(Achievement-Baseline)/(Target - Baseline) X Weight';
                        let formulaTwo = '(Baseline - Achievement)/(Baseline -Target) X weight';
                        let other = 'Other';
                        if (evalFormulaId === 2) {
                            evalFormula = formulaTwo;
                        }
                        if (evalFormulaId === 3) {
                            evalFormula = other;
                        }
                        $('.evalFormulaDisplay').empty().html('<strong>Evaluation Formula: </strong>' + evalFormula);
                    }
                }
            });
        });
    }

    function getComment() {
        $('#targetListTable tbody').on('click', 'tr .btnViewComment', function () {
            let row = $(this).closest('tr');
            let targetAuditId = row.find(".targetAuditId").text();
            let targetId = row.find(".targetId").text();
            let targetName = row.find(".targetName").text();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            $.ajax({
                url: 'api/tfBcpmFinEditTarget/getFinComment',
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
                        $('.attachmentModalTitle').empty().html(commentTitle);
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
        });
    }

    function getAttachment() {
        $('#targetListTable tbody').on('click', 'tr .btnAttachment', function () {
            let row = $(this).closest('tr');
            let targetAuditId = row.find(".targetAuditId").val();
            let targetId = row.find(".targetId").text();
            let targetName = row.find(".targetName").text();
            $('.targetNameDisplay').empty().html('<strong>' + targetName + ' </strong>');
            $('.fileUl').empty();
            let btnView = '<span class="viewFile infoMsg" title="Click to view">&nbsp;&nbsp;<i class="fa fa-eye cursor-pointer"></i></span>';
            $.ajax({
                url: 'api/tfBcpmFinEditTarget/getAttachments',
                type: "GET",
                data: {targetId: targetId},
                success: function (res) {
                    if (res.status === 1) {
                        let data = res.dto;
                        let fileCount = data.length;
                        let title = fileCount + '&nbsp;&nbsp;<i class="fa fa-paperclip"></i>Attachment';
                        if (fileCount > 1) {
                            title = fileCount + '&nbsp;&nbsp;<i class="fa fa-paperclip"></i>Comments';
                        }
                        $('.attachmentModalTitle').empty().html(title);
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
                                fileList = '<li class="fileLi"><input type="hidden" class="fileId" value="' + fileId + '"><span><i class="fa fa-file-image-o infoMsg"></i>' + fileName + btnView + '</span></li>';
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
        });
    }

    function viewFile() {
        $('.fileListDiv ul').on('click', 'li .viewFile', function () {
            let fileId = $(this).closest('.fileUl .fileLi').find('.fileId').val();

            let url = 'api/tfBcpmFinEditTarget/viewFile/' + fileId;
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
            let url = 'api/tfBcpmFinEditTarget/downloadFile/' + fileId;
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

    function submit() {
        $('.btnSubmit').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnSubmit').attr('disabled', true);
                let data = new FormData($('.targetListForm')[0]);
                $.confirm({
                    title: 'Confirmation',
                    content: 'Are you sure you want to submit now?  <center class="small">You cannot edit once submitted.</center>',
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
                                    url: _baseURL() + 'submit',
                                    type: "POST",
                                    data: data,
                                    contentType: false,
                                    processData: false,
                                    success: function (res) {
                                        if (res.status === 1) {
                                            successMsg(res.text);
                                            $('.btnSubmit').attr('disabled', false);
                                            isSubmitted = false;
                                            let year = $('#year').val();
                                            let companyId = $('#companyId').val();
                                            searchTarget(year, companyId);
                                        } else {
                                            warningMsg(res.text);
                                            isSubmitted = false;
                                            $('.btnSubmit').attr('disabled', false);
                                        }
                                    }, complete: function (res) {
                                        isSubmitted = false;
                                        $('.btnSubmit').attr('disabled', false);
                                    }
                                });
                            }
                        },
                    }
                });
            }
        });
    }

    function revert() {
        $('.btnRevert').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnRevert').attr('disabled', true);
                let data = new FormData($('.targetListForm')[0]);
                $.confirm({
                    title: 'Confirmation',
                    content: 'Are you sure you want to revert now?  <center class="small">You cannot edit once reverted.</center>',
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
                                    url: _baseURL() + 'revert',
                                    type: "POST",
                                    data: data,
                                    contentType: false,
                                    processData: false,
                                    success: function (res) {
                                        if (res.status === 1) {
                                            successMsg(res.text);
                                            $('.btnRevert').attr('disabled', false);
                                            isSubmitted = false;
                                            let year = $('#year').val();
                                            let companyId = $('#companyId').val();
                                            searchTarget(year, companyId);
                                        } else {
                                            warningMsg(res.text);
                                            isSubmitted = false;
                                            $('.btnRevert').attr('disabled', false);
                                        }
                                    }, complete: function (res) {
                                        isSubmitted = false;
                                        $('.btnRevert').attr('disabled', false);
                                    }
                                });
                            }
                        },
                    }
                });
            }
        });
    }

    function approve() {
        $('.btnApprove').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnApprove').attr('disabled', true);
                let data = new FormData($('.targetListForm')[0]);
                $.confirm({
                    title: 'Confirmation',
                    content: 'Are you sure you want to approve now?  <center class="small">You cannot edit once approved.</center>',
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
                                    url: _baseURL() + 'approve',
                                    type: "POST",
                                    data: data,
                                    contentType: false,
                                    processData: false,
                                    success: function (res) {
                                        if (res.status === 1) {
                                            successMsg(res.text);
                                            $('.btnRevert').attr('disabled', false);
                                            isSubmitted = false;
                                            let year = $('#year').val();
                                            let companyId = $('#companyId').val();
                                            searchTarget(year, companyId);
                                        } else {
                                            warningMsg(res.text);
                                            isSubmitted = false;
                                            $('.btnApprove').attr('disabled', false);
                                        }
                                    }, complete: function (res) {
                                        isSubmitted = false;
                                        $('.btnApprove').attr('disabled', false);
                                    }
                                });
                            }
                        },
                    }
                });
            }
        });
    }

    function btnGoBack() {
        $('.btnGoBack').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'targetDashboard?yId=' + year + '&&cId=' + companyId
        });
    }

    return {
        triggerSearch: triggerSearch
        , navigateToTfBcpmFinAddTarget: navigateToTfBcpmFinAddTarget
        , btnSearch: btnSearch
        , navigateToEditTarget: navigateToEditTarget
        , getWriteup: getWriteup
        , getComment: getComment
        , getAttachment: getAttachment
        , viewFile: viewFile
        , downloadFile: downloadFile
        , submit: submit
        , revert: revert
        , approve: approve
        , searchByStage: searchByStage
        , btnGoBack: btnGoBack
    }
})
();
$(document).ready(
    function () {
        let year = $('#year').val();
        let companyId = $('#companyId').val();
        tfBcpmFinList.triggerSearch();
        tfBcpmFinList.navigateToTfBcpmFinAddTarget();
        tfBcpmFinList.btnSearch();
        tfBcpmFinList.navigateToEditTarget();
        tfBcpmFinList.getWriteup();
        tfBcpmFinList.getComment();
        tfBcpmFinList.getAttachment();
        tfBcpmFinList.viewFile();
        tfBcpmFinList.downloadFile();
        tfBcpmFinList.submit();
        tfBcpmFinList.revert();
        tfBcpmFinList.approve();
        tfBcpmFinList.searchByStage();
        tfBcpmFinList.btnGoBack();
    });
