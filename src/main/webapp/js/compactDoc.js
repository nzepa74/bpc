compactDoc = (function () {
    "use strict";
    let form = $('#targetForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/compactDoc/';
    }

    function getCompanyInfo(companyId) {
        let url = 'api/common/getCompanyInfo';
        $.ajax({
            url: url,
            type: 'GET',
            data: {companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    $('.companyName').empty().text(data.companyName);
                    document.getElementById('logo').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:350px;height:350px" alt=""/>';
                }
            }
        });
    }

    function getTarget(stage, year, companyId) {
        // get fin Target list
        let finTargetUrl = '';
        let cusSerTargetUrl = '';
        let orgMgtTargetUrl = '';
        if (stage.toString() === "1") {
            finTargetUrl = 'api/tfDhiFinList/searchTarget';
            cusSerTargetUrl = 'api/tfDhiCusSerList/searchTarget';
            orgMgtTargetUrl = 'api/tfDhiOrgMgtList/searchTarget';
        }

        $.ajax({
            url: finTargetUrl,
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    displayFinTarList(res.dto);
                    let curYear = year;
                    let preYear = curYear - 1;
                    $('.preYearLbl').empty().text(preYear);
                    $('.curYearLbl').empty().text(curYear);
                }
            }
        });

        $.ajax({
            url: cusSerTargetUrl,
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let columnDef = [
                        {data: 'serialNo', class: "align-middle"},
                        {data: 'targetAuditId', class: "hidden align-middle"},
                        {data: 'activity', class: "align-middle"},
                        {data: 'subTarget', class: "subTarget align-middle"},
                        {
                            data: {deadline: 'deadline'}, class: "deadline align-middle",
                            "mRender": function (data) {
                                return formatAsDate(data.deadline);
                            }
                        },
                        {data: 'weightage', class: "weightage align-middle"},
                    ];
                    $('#cusSerTarListTable').DataTable({
                        data: data
                        , columns: columnDef
                        , pageLength: 1000000
                        , destroy: true
                        , bSort: true
                        , bFilter: false
                        , "autoWidth": true
                        , paginate: false
                        , info: false
                    });
                }
            }
        }).done(function () {
            mergeCusSerRows();
        });

        $.ajax({
            url: orgMgtTargetUrl,
            type: 'GET',
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let columnDef = [
                        {data: 'serialNo', class: "align-middle"},
                        {data: 'targetAuditId', class: "hidden align-middle"},
                        {data: 'activity', class: "align-middle"},
                        {data: 'subTarget', class: "subTarget align-middle"},
                        {
                            data: {deadline: 'deadline'}, class: "deadline align-middle",
                            "mRender": function (data) {
                                return formatAsDate(data.deadline);
                            }
                        },
                        {data: 'weightage', class: "weightage align-middle"},
                    ];
                    $('#orgMgtTarListTable').DataTable({
                        data: data
                        , columns: columnDef
                        , pageLength: 1000000
                        , destroy: true
                        , bSort: true
                        , bFilter: false
                        , "autoWidth": true
                        , paginate: false
                        , info: false
                    });
                }
            }
        }).done(function () {
            mergeOrgMgtRows();
        });

        $.ajax({
            url: 'api/compactDoc/searchProdSaleTarget',
            type: 'GET',
            data: {stage: stage, year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let columnDef = [
                        {data: 'serialNo', class: "align-middle"},
                        {data: 'targetAuditId', class: "hidden align-middle"},
                        {data: 'particular', class: "align-middle"},
                        {data: 'subTarget', class: "subTarget align-middle"},
                        {data: 'uom', class: "align-middle"},
                        {data: 'preYearActual', class: "align-middle"},
                        {data: 'curYearTarget', class: "align-middle"},
                        {data: 'weightage', class: "weightage align-middle"},
                    ];
                    $('#prodSaleTarListTable').DataTable({
                        data: data
                        , columns: columnDef
                        , pageLength: 1000000
                        , destroy: true
                        , bSort: false
                        , bFilter: false
                        , "autoWidth": true
                        , paginate: false
                        , info: false
                    });
                }
            }
        }).done(function () {
            mergeProdSaleRows();
        });

        // get fin target writeup
        $.ajax({
            url: 'api/compactDoc/getFinWriteup',
            type: 'GET',
            data: {stage: stage, year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    displayFinTarWriteup(res.dto);
                }
            }
        });
        // get cus ser writeup
        $.ajax({
            url: 'api/compactDoc/getCusSerWriteup',
            type: 'GET',
            data: {stage: stage, year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    displayCusSerWriteup(res.dto);
                }
            }
        });
        // get org mgt writeup
        $.ajax({
            url: 'api/compactDoc/getOrgMgtWriteup',
            type: 'GET',
            data: {stage: stage, year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    displayOrgMgtWriteup(res.dto);
                }
            }
        });
        // get prod sale writeup
        $.ajax({
            url: 'api/compactDoc/getProdSaleWriteup',
            type: 'GET',
            data: {stage: stage, year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    displayProdSaleWriteup(res.dto);
                }
            }
        });
    }

    function mergeCusSerRows() {
        $("#cusSerTarListTable").find('tr').each(function () {
            let columnCountFirst = 1;
            let columnCountSecond = 2;
            let columnCountThird = 3;
            let first_instance = null;
            let second_instance = null;
            let third_instance = null;
            let rowspan = 1;
            $("#cusSerTarListTable").find('tr').each(function () {
                let first_dimension_td = $(this).find('td:nth-child(' + columnCountFirst + ')');
                let second_dimension_td = $(this).find('td:nth-child(' + columnCountSecond + ')');
                let third_dimension_td = $(this).find('td:nth-child(' + columnCountThird + ')');
                if (second_instance === null) {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                } else if (second_dimension_td.text() === second_instance.text()) {
                    if (second_dimension_td.prev("td").text() === second_instance.prev("td").text()) {
                        first_dimension_td.attr('hidden', true);
                        second_dimension_td.attr('hidden', true);
                        third_dimension_td.attr('hidden', true);
                        ++rowspan;
                        first_instance.attr('rowspan', rowspan);
                        second_instance.attr('rowspan', rowspan);
                        third_instance.attr('rowspan', rowspan);
                    }

                } else {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                    rowspan = 1;
                }
            });
        });
        calculateCusSerTotalWeight();
    }

    function mergeOrgMgtRows() {
        $("#orgMgtTarListTable").find('tr').each(function () {
            let columnCountFirst = 1;
            let columnCountSecond = 2;
            let columnCountThird = 3;
            let first_instance = null;
            let second_instance = null;
            let third_instance = null;
            let rowspan = 1;
            $("#orgMgtTarListTable").find('tr').each(function () {
                let first_dimension_td = $(this).find('td:nth-child(' + columnCountFirst + ')');
                let second_dimension_td = $(this).find('td:nth-child(' + columnCountSecond + ')');
                let third_dimension_td = $(this).find('td:nth-child(' + columnCountThird + ')');
                if (second_instance === null) {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                } else if (second_dimension_td.text() === second_instance.text()) {
                    if (second_dimension_td.prev("td").text() === second_instance.prev("td").text()) {
                        first_dimension_td.attr('hidden', true);
                        second_dimension_td.attr('hidden', true);
                        third_dimension_td.attr('hidden', true);
                        ++rowspan;
                        first_instance.attr('rowspan', rowspan);
                        second_instance.attr('rowspan', rowspan);
                        third_instance.attr('rowspan', rowspan);
                    }

                } else {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                    rowspan = 1;
                }
            });
        });
        calculateOrgMgtTotalWeight();
    }

    function mergeProdSaleRows() {
        $("#prodSaleTarListTable").find('tr').each(function () {
            let columnCountFirst = 1;
            let columnCountSecond = 2;
            let columnCountThird = 3;
            let first_instance = null;
            let second_instance = null;
            let third_instance = null;
            let rowspan = 1;
            $("#prodSaleTarListTable").find('tr').each(function () {
                let first_dimension_td = $(this).find('td:nth-child(' + columnCountFirst + ')');
                let second_dimension_td = $(this).find('td:nth-child(' + columnCountSecond + ')');
                let third_dimension_td = $(this).find('td:nth-child(' + columnCountThird + ')');
                if (second_instance === null) {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                } else if (second_dimension_td.text() === second_instance.text()) {
                    if (second_dimension_td.prev("td").text() === second_instance.prev("td").text()) {
                        first_dimension_td.attr('hidden', true);
                        second_dimension_td.attr('hidden', true);
                        third_dimension_td.attr('hidden', true);
                        ++rowspan;
                        first_instance.attr('rowspan', rowspan);
                        second_instance.attr('rowspan', rowspan);
                        third_instance.attr('rowspan', rowspan);
                    }

                } else {
                    first_instance = first_dimension_td;
                    second_instance = second_dimension_td;
                    third_instance = third_dimension_td;
                    rowspan = 1;
                }
            });
        });
        calculateProdSaleTotalWeight();
    }

    function displayFinTarList(data) {
        $('#finTarListTable').dataTable().fnDestroy();
        $('#finTarListTable tbody').empty();
        let columnDef = [
            {data: 'serialNo', class: "align-middle"},
            {data: 'finKpi', class: "align-middle"},
            {data: 'preYearActual', class: "align-middle"},
            {data: 'curYearTarget', class: "align-middle"},
            {data: 'weightage', class: "weightage align-middle"},
        ];
        $('#finTarListTable').DataTable({
            data: data
            , columns: columnDef
            , pageLength: 1000000
            , destroy: true
            , bSort: false
            , bFilter: false
            , "autoWidth": true
            , paginate: false
            , info: false
        });
        calculateFinTotalWeight();
    }

    function calculateFinTotalWeight() {
        let weightTotal = 0;
        $('#finTarListTable').find('tbody tr').each(function () {
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
        $('.finTotalWeight').empty().text(' (' + weightTotal.toFixed(2) + '%)');
    }

    function calculateCusSerTotalWeight() {
        let weightTotal = 0;
        $('#cusSerTarListTable').find('tbody tr').each(function () {
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
        $('.cusSerTotalWeight').empty().text(' (' + weightTotal.toFixed(2) + '%)');
    }

    function calculateOrgMgtTotalWeight() {
        let weightTotal = 0;
        $('#orgMgtTarListTable').find('tbody tr').each(function () {
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
        $('.orgMgtTotalWeight').empty().text(' (' + weightTotal.toFixed(2) + '%)');
    }

    function calculateProdSaleTotalWeight() {
        let weightTotal = 0;
        $('#prodSaleTarListTable').find('tbody tr').each(function () {
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
        $('.prodSaleTotalWeight').empty().text(' (' + weightTotal.toFixed(2) + '%)');
    }

    function displayFinTarWriteup(data) {
        let writeup = '';
        for (let i = 0; i < data.length; i++) {
            let serialNo = data[i].serialNo;
            let finKpi = data[i].finKpi;
            let background = data[i].background;
            let evalMethod = data[i].evalMethod;
            let output = data[i].output;
            let risks = data[i].risks;
            writeup = writeup + '<div class="row"><strong>' + serialNo + '. ' + finKpi + '</strong></div>' +
                '<p><i>i. Backgound</i><p>' +
                '<div class="row">' + background + '</div>' +
                '<p><i>ii. Risks Associated</i><p>' +
                '<div class="row">' + risks + '</div>' +
                '<p><i>iii. Target Output</i><p>' +
                '<div class="row">' + output + '</div>' +
                ' <p><i>iv. Assessment Method</i><p>' +
                '<div class="row">' + evalMethod + '</div>';
        }
        $('.finWriteup').empty().html('<strong>Financial</strong>' + writeup);
    }

    function displayCusSerWriteup(data) {
        let writeup = '';
        for (let i = 0; i < data.length; i++) {
            let serialNo = data[i].serialNo;
            let activity = data[i].activity;
            let background = data[i].background;
            let evalMethod = data[i].evalMethod;
            let output = data[i].output;
            let risks = data[i].risks;
            writeup = writeup + '<div class="row"><strong>' + serialNo + '. ' + activity + '</strong></div>' +
                '<p><i>i. Backgound</i><p>' +
                '<div class="row">' + background + '</div>' +
                '<p><i>ii. Risks Associated</i><p>' +
                '<div class="row">' + risks + '</div>' +
                '<p><i>iii. Target Output</i><p>' +
                '<div class="row">' + output + '</div>' +
                ' <p><i>iv. Assessment Method</i><p>' +
                '<div class="row">' + evalMethod + '</div>';
        }
        $('.cusSerWriteup').empty().html('<strong>Customer Service</strong>' + writeup);
    }

    function displayOrgMgtWriteup(data) {
        let writeup = '';
        for (let i = 0; i < data.length; i++) {
            let serialNo = data[i].serialNo;
            let activity = data[i].activity;
            let background = data[i].background;
            let evalMethod = data[i].evalMethod;
            let output = data[i].output;
            let risks = data[i].risks;
            writeup = writeup + '<div class="row"><strong>' + serialNo + '. ' + activity + '</strong></div>' +
                '<p><i>i. Backgound</i><p>' +
                '<div class="row">' + background + '</div>' +
                '<p><i>ii. Risks Associated</i><p>' +
                '<div class="row">' + risks + '</div>' +
                '<p><i>iii. Target Output</i><p>' +
                '<div class="row">' + output + '</div>' +
                ' <p><i>iv. Assessment Method</i><p>' +
                '<div class="row">' + evalMethod + '</div>';
        }
        $('.orgMgtWriteup').empty().html('<strong>Organizational Management</strong>' + writeup);
    }

    function displayProdSaleWriteup(data) {
        let writeup = '';
        for (let i = 0; i < data.length; i++) {
            let serialNo = data[i].serialNo;
            let particular = data[i].particular;
            let background = data[i].background;
            let evalMethod = data[i].evalMethod;
            let output = data[i].output;
            let risks = data[i].risks;
            writeup = writeup + '<div class="row"><strong>' + serialNo + '. ' + particular + '</strong></div>' +
                '<p><i>i. Backgound</i><p>' +
                '<div class="row">' + background + '</div>' +
                '<p><i>ii. Risks Associated</i><p>' +
                '<div class="row">' + risks + '</div>' +
                '<p><i>iii. Target Output</i><p>' +
                '<div class="row">' + output + '</div>' +
                ' <p><i>iv. Assessment Method</i><p>' +
                '<div class="row">' + evalMethod + '</div>';
        }
        $('.prodSaleWriteup').empty().html('<strong>Production/Sales/Projects</strong>' + writeup);
    }

    function btnGoBack() {
        $('.btnGoBack').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'targetDashboard?yId=' + year + '&&cId=' + companyId
        });
    }

    function pageTitle(stage) {
        let stageName = 'Formulation';
        if (stage.toString() === "3" || stage.toString() === "4") {
            stageName = "Midterm Review";
        }
        if (stage.toString() === "5" || stage.toString() === "6") {
            stageName = "Midterm Review";
        }
        $('.stageName').empty().text(stageName);
        $('.stageCount').empty().text(stage);
    }

    return {
        getTarget: getTarget
        , getCompanyInfo: getCompanyInfo
        , btnGoBack: btnGoBack
        , pageTitle: pageTitle
    }
})
();
$(document).ready(
    function () {
        let year = $('#year').val();
        let companyId = $('#companyId').val();
        let stage = $('#stage').val();
        compactDoc.getTarget(stage, year, companyId);
        compactDoc.getCompanyInfo(companyId);
        compactDoc.btnGoBack();
        compactDoc.pageTitle(stage);
    });
