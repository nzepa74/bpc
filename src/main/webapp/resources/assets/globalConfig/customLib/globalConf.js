globalConf = (function () {
    "use strict";

    function dateFormat() {
        // return "dd-M-yy";
        return "MM dd, yy";
    }

    function dateFormatDay() {
        return "dd";
    }

    function dateFormatMonth() {
        return "M";
    }

    return {
        dateFormat: dateFormat,
        dateFormatDay: dateFormatDay,
        dateFormatMonth: dateFormatMonth
    };
})();

$(document).ready(function () {
    globalConf.dateFormat();
    globalConf.dateFormatDay();
    globalConf.dateFormatMonth();
});