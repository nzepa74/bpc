$(document).ready(function () {

    if (document.URL.search("/login") > 1)
        scriptLoader("resources/assets/js/login.js");

    if (document.URL.search("/forgotPassword") > 1)
        scriptLoader("resources/assets/js/forgotPassword.js");

    if (document.URL.search("/resetPassword") > 1)
        scriptLoader("resources/assets/js/resetPassword.js");

    if (document.URL.search("user") > 1)
        scriptLoader("js/user.js");

    if (document.URL.search("myProfile") > 1)
        scriptLoader("js/myProfile.js");

    if (document.URL.search("/") > 1)
        scriptLoader("js/home.js");

    if (document.URL.search("/home") > 1)
        scriptLoader("js/home.js");

    if (document.URL.search("commentPolicy") > 1)
        scriptLoader("js/commentPolicy.js");

    if (document.URL.search("about") > 1)
        scriptLoader("js/about.js");

    if (document.URL.search("help") > 1)
        scriptLoader("js/help.js");

    if (document.URL.search("permission") > 1)
        scriptLoader("js/permission.js");

    if (document.URL.search("year") > 1)
        scriptLoader("js/year.js");

    if (document.URL.search("report") > 1)
        scriptLoader("js/report.js");

    if (document.URL.search("company") > 1)
        scriptLoader("js/company.js");

    if (document.URL.search("weightageSetup") > 1)
        scriptLoader("js/weightageSetup.js");

    if (document.URL.search("score") > 1)
        scriptLoader("resources/assets/js/score.js");


    if (document.URL.search("targetDashboard") > 1)
        scriptLoader("js/targetDashboard.js");

    if (document.URL.search("tfDhiFinList") > 1)
        scriptLoader("js/tfDhiFinList.js");

    if (document.URL.search("tfDhiFinAddTarget") > 1)
        scriptLoader("js/tfDhiFinAddTarget.js");

    if (document.URL.search("tfDhiFinEditTarget") > 1)
        scriptLoader("js/tfDhiFinEditTarget.js");


    if (document.URL.search("tfBcpmFinList") > 1)
        scriptLoader("js/tfBcpmFinList.js");

    if (document.URL.search("tfBcpmFinAddTarget") > 1)
        scriptLoader("js/tfBcpmFinAddTarget.js");

    if (document.URL.search("tfBcpmFinEditTarget") > 1)
        scriptLoader("js/tfBcpmFinEditTarget.js");

    if (document.URL.search("tfBcpmCusSerList") > 1)
        scriptLoader("js/tfBcpmCusSerList.js");

    if (document.URL.search("tfBcpmCusSerAddTarget") > 1)
        scriptLoader("js/tfBcpmCusSerAddTarget.js");

    if (document.URL.search("tfBcpmCusSerEditTarget") > 1)
        scriptLoader("js/tfBcpmCusSerEditTarget.js");


    if (document.URL.search("tfDhiCusSerAddTarget") > 1)
        scriptLoader("js/tfDhiCusSerAddTarget.js");

    if (document.URL.search("tfDhiCusSerList") > 1)
        scriptLoader("js/tfDhiCusSerList.js");

    if (document.URL.search("tfDhiCusSerEditTarget") > 1)
        scriptLoader("js/tfDhiCusSerEditTarget.js");

    if (document.URL.search("tfDhiOrgMgtAddTarget") > 1)
        scriptLoader("js/tfDhiOrgMgtAddTarget.js");

    if (document.URL.search("tfDhiOrgMgtList") > 1)
        scriptLoader("js/tfDhiOrgMgtList.js");

    if (document.URL.search("tfDhiOrgMgtEditTarget") > 1)
        scriptLoader("js/tfDhiOrgMgtEditTarget.js");

    if (document.URL.search("tfBcpmOrgMgtAddTarget") > 1)
        scriptLoader("js/tfBcpmOrgMgtAddTarget.js");

    if (document.URL.search("tfBcpmOrgMgtList") > 1)
        scriptLoader("js/tfBcpmOrgMgtList.js");

    if (document.URL.search("tfBcpmOrgMgtEditTarget") > 1)
        scriptLoader("js/tfBcpmOrgMgtEditTarget.js");

    if (document.URL.search("tfDhiProdSaleAddTarget") > 1)
        scriptLoader("js/tfDhiProdSaleAddTarget.js");

    if (document.URL.search("tfDhiProdSaleList") > 1)
        scriptLoader("js/tfDhiProdSaleList.js");

    if (document.URL.search("tfDhiProdSaleEditTarget") > 1)
        scriptLoader("js/tfDhiProdSaleEditTarget.js");

    if (document.URL.search("tfBcpmProdSaleAddTarget") > 1)
        scriptLoader("js/tfBcpmProdSaleAddTarget.js");

    if (document.URL.search("tfBcpmProdSaleList") > 1)
        scriptLoader("js/tfBcpmProdSaleList.js");

    if (document.URL.search("tfBcpmProdSaleEditTarget") > 1)
        scriptLoader("js/tfBcpmProdSaleEditTarget.js");

    if (document.URL.search("compactDoc") > 1)
        scriptLoader("js/compactDoc.js");


    if (document.URL.search("employee") > 1)
        scriptLoader("js/employee.js");


});

let scriptLoader = function (url) {
    $.ajax(
        {
            type: "GET",
            url: url,
            dataType: "script",
            cache: false
        });
};



