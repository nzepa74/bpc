reports = (function () {
    "use strict";
    let form = $('#reportFormId');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/report/';
    }

    function generateReport() {

        $('.btnGenerate').on('click', function () {
            globalLib.isFormValid(form);
            if (form.valid()) {
                isSubmitted = true;
                $('.btnGenerate').attr('disabled', true);
                let year = "2022";
                let companyId = "ASdasd";
                // let year = $('#year').val();
                // let companyId = $('#companyId').val();
                let documentType = $('input[name="documentType"]:checked').val();

                $.ajax({
                    url: _baseURL() + 'generateReport', type: "GET", data: {
                        year: year, companyId: companyId, documentType: documentType
                    }, success: function (res) {
                        if (res.status === 1) {
                            for (let i = 0; i < res.dto.length; i++) {
                                window.open(globalLib.baseReportLocation() + res.dto[i].reportName, '_blank');
                            }
                        } else {
                            warningMsg(res.text)
                        }
                    }, complete: function () {
                        $('.btnGenerate').attr('disabled', false);
                        isSubmitted = false;
                    }
                });
            }
        });
    }

    return {
        generateReport: generateReport,

    }
})();

$(document).ready(function () {
    reports.generateReport();
});