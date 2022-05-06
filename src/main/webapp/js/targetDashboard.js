targetDashboard = (function () {
    "use strict";
    let form = $('#weightageFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/targetDashboard/';
    }

    function initialSetup() {
        let myRoleId = $('#myRoleId').val();
        let creatorRoleId = $('#creatorRoleId').val();
        let myCompanyId = $('#myCompanyId').val();

        let cId = $('#cId').val();
        let yId = $('#yId').val();
        $('#companyId').val(cId).trigger("chosen:updated");
        $('#year').val(yId).trigger("chosen:updated");
        if (myRoleId.toString() === creatorRoleId.toString()) {
            $('#companyId').val(myCompanyId).trigger("chosen:updated");
        }
        displayLogo($('#companyId').val());
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
                    let shortName = data.shortName;
                    $('.shortName').empty().text(shortName);
                    document.getElementById('displayLogoSearch').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:30px;height:30px" alt=""/>';
                    document.getElementById('displayLogoFin').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                    document.getElementById('displayLogoCusSer').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                    document.getElementById('displayLogoOrgMgt').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                    document.getElementById('displayLogoProdSale').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                    document.getElementById('displayLogoDoc').innerHTML =
                        '<img type="file" class="id"  src="data:image/png;base64,' + data.logo + '" style="width:20px;height:20px" alt=""/>';
                }
            }
        });
    }

    function getCompanyInfo() {
        $('#companyId').on('change', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            displayLogo(companyId);
            getAllocatedWt(year, companyId)
        });
    }

    function getAllocatedWt(year, companyId) {
        $.ajax({
            url: 'api/tfDhiFinAddTarget/getAllocatedWt',
            type: "GET",
            data: {year: year, companyId: companyId},
            success: function (res) {
                if (res.status === 1) {
                    let data = res.dto;
                    let financialWt = data.financialWt;
                    let customerWt = data.customerWt;
                    let orgManagementWt = data.orgManagementWt;
                    let productionWt = data.productionWt;
                    // setTimeout(function () {
                    $('.financialWt').empty().text('(' + financialWt + '%)');
                    $('.customerWt').empty().text('(' + customerWt + '%');
                    $('.orgManagementWt').empty().text('(' + orgManagementWt + '%)');
                    $('.productionWt').empty().text('(' + productionWt + '%)');
                    // }, 100);
                } else {
                    $('.financialWt').empty().text('(0%)');
                    $('.customerWt').empty().text('(0%)');
                    $('.orgManagementWt').empty().text('(0%)');
                    $('.productionWt').empty().text('(0%)');
                }
            }
        });
    }

    function navigateToTfDhiFinList() {
        $('.tfDhiFinancial').on('click', function () {
            //navigate to formulation target list financial 1
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiFinList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfBcpmFinancial').on('click', function () {
            //navigate to formulation target list financial 2
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmFinList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfDhiCusSer').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiCusSerList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfBcpmCusSer').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmCusSerList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfDhiOrgMgt').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiOrgMgtList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfBcpmOrgMgt').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmOrgMgtList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfDhiProdSale').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfDhiProdSaleList?yId=' + year + '&&cId=' + companyId
        });
        $('.tfBcpmProdSale').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            window.location.href = 'tfBcpmProdSaleList?yId=' + year + '&&cId=' + companyId
        });
    }

    function navigateToCompactDoc() {
        $('.btnCompactDocOne').on('click', function () {
            let year = $('#year').val();
            let companyId = $('#companyId').val();
            let stage = 1;
            window.location.href = 'compactDoc?stage=' + stage + '&&yId=' + year + '&&cId=' + companyId
        });
    }


    return {
        navigateToTfDhiFinList: navigateToTfDhiFinList
        , initialSetup: initialSetup
        , getCompanyInfo: getCompanyInfo
        , navigateToCompactDoc: navigateToCompactDoc
    }
})
();
$(document).ready(
    function () {
        targetDashboard.navigateToTfDhiFinList();
        targetDashboard.initialSetup();
        targetDashboard.getCompanyInfo();
        targetDashboard.navigateToCompactDoc();
    });
