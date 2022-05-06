login = (function () {
    "use strict";
    let form = $('#loginForm');
    let isSubmitted = false;

    function _baseURL() {
        return 'api/login/';
    }

    let i = 0;
    let txt = " and was launched as Public Utility Company on 1st July 2002 with an objective that the corporatization of the utility functions would lead to greater efficiency and better delivery of electricity supply services in the power sector.\n" +
        "Later, the Ownership was transferred to Druk Holding and Investment Limited (DHI), the commercial arm of the Royal Government of Bhutan, established in 2007 upon issuance of Royal Charter in 2007 “to hold and manage the existing and future investments of the Royal Government for the long term benefit of the people of Bhutan”";
    let speed = 20;

    function typeWriter() {
        if (i < txt.length) {
            document.getElementById("displayDescription").innerHTML += txt.charAt(i);
            i++;
            setTimeout(typeWriter, speed);
        }
        if (i === txt.length - 1) {
            $('#readMore').removeClass('hidden').addClass('shake animated');
        }
    }

    return {
        typeWriter: typeWriter
    }
})();

$(document).ready(function () {
    login.typeWriter();
});