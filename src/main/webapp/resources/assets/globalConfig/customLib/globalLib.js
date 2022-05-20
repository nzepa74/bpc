//region === Global Ajax Settings ===
$(document).ajaxSend(function (e, xhr, options) {
    let token = $('input[name="_csrf"]').val();
    xhr.setRequestHeader("X-CSRF-TOKEN", token);
    if (options.type.toUpperCase() === "POST") {
        $.blockUI();
    }
    NProgress.start();
    // $.blockUI();//to start blockUI while loading page
}).ajaxStart(function () {
    NProgress.set(0.4);
    // $.blockUI();//to start blockUI in every ajax call
}).ajaxStop(function () {
    $.unblockUI();
    NProgress.done();
}).ajaxError(function (event, jgxhr, settings, thrownError) {

    $.unblockUI();
    NProgress.done();

    switch (jgxhr.status) {
        case 500:
            errorMsg("Something went wrong. Please refresh the page and try again.");
            break;
        case 404:
            errorMsg("Invalid Request - 404");
            break;
        case 400:
            errorMsg("Bad Request - 400");
            break;
        case 403:
            errorMsg("Access Denied - 403");
            break;
    }
}).ajaxComplete(function () {
    $.unblockUI();
    NProgress.done();
}).ajaxSuccess(function (event, request, settings) {
    $.unblockUI();
    NProgress.done();
    if (event.redirect) {
        window.location.href = event.redirect;
    }
    let location = request.getResponseHeader('Location');
    if (location && location != window.location.href) {
        window.location.href = location;
    }
});

// $(document).ajaxStart($.blockUI).ajaxStop($.unblockUI);

function isEmail(email) {
    let regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}

function textAreaAdjust(element) {
    element.style.height = "1px";
    element.style.height = (4 + element.scrollHeight) + "px";
}

function successMsg(msg, isDelete) {
    tata.success('<strong>' + 'Success' + '</strong>', '<small>' + msg + '</small>', {
        animate: 'slide', progress: true, holding: false, position: 'br', duration: 5000
    });
    let audio = new Audio('resources/assets/sounds/success/3.mp3');
    if (isDelete === true) {
        audio = new Audio('resources/assets/sounds/success/1.mp3');
    }
    audio.play();
}

function warningMsg(msg) {
    let audio = new Audio('resources/assets/sounds/warning/3.mp3');
    audio.play();
    tata.warn('<strong>' + 'Warning' + '</strong>', '<small>' + msg + '</small>', {
        animate: 'slide', progress: true, holding: false, position: 'tr', duration: 5000
    });
}

function msgInfo(msg) {
    let audio = new Audio('resources/assets/sounds/info/3.mp3');
    audio.play();
    tata.info('<strong>' + 'Info' + '</strong>', '<small>' + msg + '</small>', {
        animate: 'slide', progress: true, holding: false, position: 'br', duration: 5000
    });
}

function errorMsg(msg) {
    let audio = new Audio('resources/assets/sounds/error/1.mp3');
    audio.play();
    tata.error('<strong>' + 'Error' + '</strong>', '<small>' + msg + '</small>', {
        animate: 'slide', progress: true, holding: false, position: 'tr', duration: 5000
    });
}

function editorConfig(placeHolderValue) {
    return {
        bounds: "#full-container .editor", modules: {
            toolbar: [[{font: []}, {size: []}], ["bold", "italic", "underline", "strike"], [{color: []}, {background: []}], [{script: "super"}, {script: "sub"}], [{list: "ordered"}, {list: "bullet"}, {indent: "-1"}, {indent: "+1"}], ["direction", {align: []}], ["clean"]]
        }, theme: "snow", placeholder: placeHolderValue,
    }
}

function isNumeric(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

function generateUniqueUiId() {
    let dt = new Date().getTime();
    let uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        let r = (dt + Math.random() * 16) % 16 | 0;
        dt = Math.floor(dt / 16);
        return (c == 'x' ? r : (r & 0x3 | 0x8)).toString(16);
    });
    return uuid;
}

function getShortName(fullName) {
    // let firstLetter = fullName.substr(0, 1);
    // let indexOfDot = fullName.substring(0, fullName.indexOf('.'));
    //
    // let lastLetter = fullName.substr(2, 1);
    // return firstLetter + lastLetter;

    let firstLetter = fullName.substr(0, 1);
    let indexOfWhiteSpace = fullName.split("").reverse().join("").indexOf(' ');
    let lastLetter = fullName.split("").reverse().join("").substr(indexOfWhiteSpace - 1, 1);
    return firstLetter.toUpperCase() + lastLetter.toUpperCase();
}

function targetFormIndexing(tableBody, row, serialNo, iterator) {
    if (!iterator) iterator = 0;

    for (let i = 0; i < row.length; i++) {
        tableBody.children().eq(i).children().children().children().children().each(function () {
            if (this.name) {
                this.name = this.name.replace(/\[(\d+)\]/, function (str, p) {
                    return '[' + (i + iterator) + ']';
                });
            }

            if ($(this).hasClass(serialNo)) {
                $(this).val(i + 1);
            }
        });
    }
}

function targetFormIndexing2(tableBody, row, serialNo, iterator) {
    if (!iterator) iterator = 0;

    for (let i = 0; i < row.length; i++) {
        tableBody.children().eq(i).children().children().children().children().children().children().each(function () {
            if (this.name) {
                this.name = this.name.replace(/\[(\d+)\]/, function (str, p) {
                    return '[' + (i + iterator) + ']';
                });
            }

            if ($(this).hasClass(serialNo)) {
                $(this).val(i + 1);
            }
        });
    }
}

function formIndexing(tableBody, row, serialNo, iterator) {
    if (!iterator) iterator = 0;

    for (let i = 0; i < row.length; i++) {
        tableBody.children().eq(i).children().children().each(function () {
            if (this.name) {
                this.name = this.name.replace(/\[(\d+)\]/, function (str, p) {
                    return '[' + (i + iterator) + ']';
                });
            }

            if ($(this).hasClass(serialNo)) {
                $(this).val(i + 1);
            }
        });
    }
}

function indexRowNo(tableId) {
    let childIndex = 0;
    let parentIndex = 0;
    tableId.find('tbody tr').each(function () {
        let row = $(this).closest('tr');
        let selectedRow = row.addClass('selected');
        if (selectedRow.find('.particular').hasClass('particular')) {
            parentIndex = parentIndex + 1;
            selectedRow.find('.parentRowIndex').html(parentIndex);
        } else if (selectedRow.find('.particularDescription').hasClass('particularDescription')) {
            if (selectedRow.prev().find('.particular').hasClass('particular')) {
                childIndex = 0;
            }
            childIndex = childIndex + 1;
            selectedRow.find('.rowNumber').html(parentIndex + '.' + childIndex);
        }
        row.removeClass('selected');
    });
}

function formatAsDate(date) {
    if (date) return $.datepicker.formatDate(globalConf.dateFormat(), new Date(date));
    "MM dd, yy";
    return '';
}

function formatAsDateDay(date) {
    if (date) return $.datepicker.formatDate(globalConf.dateFormatDay(), new Date(date));
    return '';
}

function formatAsDateMonth(date) {
    if (date) return $.datepicker.formatDate(globalConf.dateFormatMonth(), new Date(date));
    return '';
}

function populate(data) {
    for (let i in data) {
        if (typeof (data[i]) === 'object') {
            //populate(data[i]);
        } else {
            let newData;
            if (typeof data[i] == "string") {
                newData = data[i].replace("'", "\'");
            } else {
                newData = data[i];
            }
            //let data=data[i].replace('\'', '\\\'');
            $("input[type='text'][name='" + i + "']," + "input[type='hidden'][name='" + i + "'], " + "input[type='checkbox'][name='" + i + "'], " + "input[type='email'][name='" + i + "'], " + "input[type='number'][name='" + i + "'], " + "select[name='" + i + "'], textarea[name='" + i + "']").val(newData);

            if (typeof data[i] !== "string") {
                $("input[type='radio'][name='" + i + "'][value='" + newData + "']").prop('checked', true);

            }
            if ($("input[name='" + i + "']").hasClass('datepicker')) {
                $("input[name='" + i + "']").val($.datepicker.formatDate("MM dd, yy", new Date(newData)));
            }
        }
    }

    $('form').find('input[type="checkbox"]').each(function () {
        if ($(this).siblings('input[type="hidden"]').val() == "true" || $(this).siblings('input[type="hidden"]').val() == 1) {
            $(this).prop('checked', true);
        } else {
            $(this).prop('checked', false);
        }
    });
}

function allowKeys(e) {
    //Allow: backspace, delete, tab, escape, enter
    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 || //Allow: Ctr+A
        (e.keyCode == 65 && e.ctrlKey === true) || //Allow: home, end, left, right, down, up
        (e.keyCode >= 35 && e.keyCode <= 40)) {
        return true;
    }
    return false;
}

function parseEntryAsDate(id) {
    id = '#' + id;
    let val = $(id).val();
    val = val.trim();
    if (val) {
        let date = null;
        if (val.length > 8) {
            date = $.datepicker.parseDate(globalConf.dateFormat(), val);
            if (date.getTime()) {
                val = $.datepicker.formatDate(globalConf.dateFormat(), new Date(date));
                $(id).val(val);
                return true;
            }
        }
        date = parseAsDate(val);
        if (!date) {
            $(id).val('');
            return;
        }
        $(id).datepicker("setDate", date);
        val = $.datepicker.formatDate(globalConf.dateFormat(), date);
        $(id).val(val);
        return true;
    }
}

function parseAsDate(val) {
    if (val) {
        let dateRegex = /((3[01])|([012]\d)|[1-9])((1[012])|(0?[1-9]))?((19|20)?\d\d?)?/;
        let dateMatch = val.match(dateRegex);
        if (!dateMatch || !dateMatch[1]) {
            return;
        }
        let day = dateMatch[1];
        let date = new Date();
        //if (currentUser.systemOpenDate) {
        //    date = new Date(currentUser.systemOpenDate);
        //}
        let month = date.getMonth();
        let year = date.getFullYear();
        if (dateMatch[4]) {
            month = dateMatch[4];
        }
        if (dateMatch[7]) {
            year = dateMatch[7];
            year = parseInt(year);
            if (dateMatch[7].length < 3) {
                if (year > 70) year = 1900 + year; else year = 2000 + year;
            }
        }
        date = new Date(year, month - 1, day);
        return date;

    }
}

globalLib = (function () {
    "use strict";

    function baseUrl() {
        ////TODO for production environment
        // return window.location.protocol + '//' + window.location.host + '/compact/';
        return window.location.protocol + '//' + window.location.host + '/';
    }

    function ignoreIISUrl() {
        ////TODO for production environment
        // return window.location.protocol + '//' + window.location.host + '/compnact/';
        return window.location.origin + ':8080/';
    }


    function baseReportLocation() {
        //TODO for production environment
        // return window.location.protocol + '//' + window.location.host + '/resources/reports/';
        return window.location.protocol + '//' + window.location.host + '/reports/';
    }

    function ajax(url, type, data, callback) {
        $.ajax({
            url: url, type: type, data: data, success: callback
        });
    }

    function ajax_with_attachment(url, type, data, callback) {
        $.ajax({
            url: url,
            type: type,
            data: data,
            enctype: 'multipart/form-data',
            contentType: false,
            processData: false,
            success: callback
        });
    }

    //to add row to the grid
    function addRow(tableBody, row) {

        let lastRow = tableBody.children('tr:last');

        let firstRowClone = $('tr:first-child', tableBody).clone(false);
        lastRow.before(firstRowClone);

        firstRowClone.find('input[type="text"]').val('');
        if (firstRowClone.find('.datepicker').hasClass('hasDatepicker')) {

            firstRowClone.find('.datepicker').attr('id', '')
                .removeClass('hasDatepicker').datepicker({
                dateFormat: globalConf.dateFormat(),
                changeMonth: true,
                changeYear: true,
                yearRange: 'c-65:c+10',
                beforeShow: function (input, inst) {
                    if ($(input).prop("readonly")) {
                        return false;
                    }
                }
            });
        }
        formIndexing(tableBody, row);
    }

    function loadDropDown(element, data, type) {
        element.empty();
        if (!data) {
            data = [];
        }
        if (data.length) {
            element.append($('<option/>', {
                value: "", text: "--- Please Select ---"
            }));
            if (type === 'char') {
                $.each(data, function (index, itemData) {
                    element.append($('<option/>', {
                        value: itemData.valueChar, text: itemData.text
                    }));
                });
            }


            if (type === 'string') {
                $.each(data, function (index, itemData) {
                    element.append($('<option/>', {
                        value: itemData.value, text: itemData.text
                    }));
                });
            }

            if (type === 'integer') {
                $.each(data, function (index, itemData) {
                    if (itemData.text != null) {
                        element.append($('<option/>', {
                            value: itemData.valueInteger, text: itemData.text
                        }));
                    }
                });
            }
            if (type === 'integer') {
                $.each(data, function (index, itemData) {
                    if (itemData.valueText != null) {
                        element.append($('<option/>', {
                            value: itemData.valueInteger, text: itemData.valueText
                        }));
                    }
                });
            }
            if (type === 'bigInteger') {
                $.each(data, function (index, itemData) {
                    element.append($('<option/>', {
                        value: itemData.valueBigInteger, text: itemData.text
                    }));
                });
            }

            if (type === 'short') {
                $.each(data, function (index, itemData) {
                    element.append($('<option/>', {
                        value: itemData.valueShort, text: itemData.text
                    }));
                });
            }

            if (type === 'valueInteger') {
                $.each(data, function (index, itemData) {
                    element.append($('<option/>', {
                        value: itemData.valueInteger, text: itemData.valueText
                    }));
                });
            }
        }
    }

    function getCurrentTimeAmPmFormat(date) {
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var ampm = hours >= 12 ? 'PM' : 'AM';
        hours = hours % 12;
        hours = hours ? hours : 12; // the hour '0' should be '12'
        minutes = minutes < 10 ? '0' + minutes : minutes;
        return hours + ':' + minutes + ' ' + ampm;
    }

    function isFormValid(form) {
        form.validate({
            errorElement: 'span', errorPlacement: function (error, element) {
                error.addClass('invalid-feedback');
                element.closest('.form-group').append(error);
            }, highlight: function (element) {
                $(element).addClass('is-invalid');
            }, unhighlight: function (element) {
                $(element).removeClass('is-invalid');
            }
        });
    }

    //<editor-fold desc="to close modal on pressing Escape key">
    $(document).on('keydown', function (event) {
        if (event.key == "Escape") {
            $('.fade').modal('hide');
        }
    });
    //</editor-fold>

    return {
        baseUrl: baseUrl,
        ignoreIISUrl: ignoreIISUrl,
        ajax: ajax,
        ajax_with_attachment: ajax_with_attachment,
        formIndexing: formIndexing,
        baseReportLocation: baseReportLocation,
        addRow: addRow,
        loadDropDown: loadDropDown,
        getCurrentTimeAmPmFormat: getCurrentTimeAmPmFormat,
        isFormValid: isFormValid
    };
})();

//*********************************************************************************************************

//region *** Document Ready Method ***
$(document).ready(function () {

    //<editor-fold desc="to show datepicker in subtarget">
    $('#subTargetTableId tbody').on('click', 'tr .showDatepicker', function () {
        let row = $(this).closest('tr');
        let selectedRow = row.addClass('selected');
        selectedRow.find('.calendarDate').datepicker({
            // dateFormat: 'MM dd, yy',
            dateFormat: 'M dd',
        });
        selectedRow.find('.calendarDate').datepicker('show');
        selectedRow.removeClass('selected');
    });

//to show hide on clicking input field
    $('#subTargetTableId tbody').on('click', 'tr .calendarDate', function () {
        $(this).datepicker('hide');
        $(this).removeClass('hasDatepicker');
    });
    //</editor-fold>


    //Local letiable for show errors on pop instead of tooltip
    let body = $('body');
    //<editor-fold desc="to show title on hover">
    $('textarea, input').hover(function () {
        $(this).attr('title', $(this).val());
    });

    $('table').on('mouseover', 'tbody tr', function (e) {
        let currentCell = $(e.target).closest("td");
        currentCell.find('textarea').attr('title', currentCell.find('textarea').val());
        currentCell.find('input').attr('title', currentCell.find('input').val());
    });
    //</editor-fold>
    //region *** Restriction Event ***
    body.on('keypress', '.alphanumeric', function (e) {
        if (allowKeys(e)) {
            return true;
        }
        let regex = new RegExp("^[a-zA-Z0-9]+$");
        let str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        }

        e.preventDefault();
        return false;
    });

    /**
     * to allow only numeric characters
     * it allow to copy and paste number only characters only
     */
    body.on('keypress keyup blur', '.numeric', function (e) {
        $(this).val($(this).val().replace(/[^\d].+/, ""));
        if ((e.which < 48) || e.which > 57) {
            e.preventDefault();
        }
    });

    /**
     * to allow only decimal numbers
     */
    body.on('keypress keyup blur', '.decimal', function (e) {
        $(this).val($(this).val().replace(/[0-9\.]/g, ''));
        if ((e.which != 46 || $(this).val().indexOf('.') != -1) && (e.which < 48 || e.which > 57)) {
            e.preventDefault();
        }
    });

    body.on('keypress', '.phone', function (e) {
        let evt = (e) ? e : window.event;
        let charCode = (evt.which) ? evt.which : evt.keyCode;
        if (charCode != 43 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        return true;
    });


    function isAmount(event, element) {
        let charCode = (event.which) ? event.which : event.keyCode;
        if ((charCode != 46 || $(element).val().indexOf('.') != -1) && charCode != 45 && charCode != 46 && !(charCode >= 48 && charCode <= 57)) {
            return false;
        }
            //if (
            //    (charCode != 46 || $(element).val().indexOf('.') != -1) &&
            //    (charCode != 9) &&
            //    (charCode < 48 || charCode > 57) &&
            //    (charCode != 8))
        //    return false;
        else return true;
    }

    body.on('keypress', '.amount', function (e) {
        return isAmount(e, this);
    });

    body.on('keydown', '.percentage', function (e) {
        if (allowKeys(e)) {
            return;
        }

        if ((e.which >= 96 && e.which <= 105) || (e.which >= 48 && e.which <= 57) || e.which === 190 || e.which === 110) {

        } else {
            e.preventDefault();
        }
    });

    body.on('blur', '.percentage', function (e) {
        let $this = $(this);
        if ($this.val()) {
            let value = $this.val();

            let regex = new RegExp("^[0-9]{1,3}(\\.([0-9]{1,2})?)?$");
            if (!regex.test(value)) {
                warningMsg('Incorrect Format. Format is ###.##');
                $this.val('');
                return;
            }
        }
        if (value > 100) {
            warningMsg('Please insert interest rate between zero(0) and hundred(100)');
            $this.val('');
            return;
        }
    });

    let datePickerOptions = {
        dateFormat: globalConf.dateFormat(),
        changeMonth: true,
        changeYear: true,
        yearRange: 'c-65:c+10',
        beforeShow: function (input, inst) {
            if ($(input).prop("readonly")) {
                return false;
            }
        }
    };

    $(".datepicker").datepicker(datePickerOptions);

    $('body').on('focus', '.datepicker', function () {
        if ($(this).hasClass('dynamic')) {
            $(this).datepicker(datePickerOptions);
        }
    });

    body.on('keydown', 'input ,a , select', function (e) {
        let key = e.charCode ? e.charCode : e.keyCode ? e.keyCode : 0;
        if (key == 13 || key == 9 & e.target.type != 'submit' & $(e.target).hasClass('datepicker')) {
            let id = $(e.target).attr('id');
            parseEntryAsDate(id);
        }
        if (key == 13 & e.target.type != 'submit') {
            e.preventDefault();

            if (e.target.tagName.toLocaleLowerCase() === 'a' || e.target.type === 'button') {
                $(this).click();
            }

            let manualNextIndex = $(this).attr("data-nextIndex");
            if (manualNextIndex) {
                let $manualNext = $('[tabindex=' + manualNextIndex + ']');
                $manualNext.focus();
                return false;
            }

            let curIndex = this.tabIndex;
            let i = +curIndex + 1;
            let $next = null;
            let allNext = $('[tabindex=' + i + ']:not(body):not([readonly]):not(:disabled)');
            if (allNext.length) {
                $next = allNext[0];
            }
            if (!$next) {
                let nextPossibleTabIndex = 10000;
                allNext = $('[tabindex]:not(body):not([readonly]):not(:disabled)').filter(function (ix, el) {
                    let tb = el.tabIndex;
                    if (+tb > +curIndex) {
                        nextPossibleTabIndex = nextPossibleTabIndex > +tb ? +tb : nextPossibleTabIndex;
                        return true;
                    } else {
                        return false;
                    }
                });
                if (allNext.length) {
                    $next = $('[tabindex=' + nextPossibleTabIndex + ']');
                }
            }

            if (!$next) {
                $next = $('[tabindex]:not(body):not([readonly]):not(:disabled)')[0];
            }
            if ($next.attr('type') == 'reset') {
                $('input[type="submit"]').focus();
            } else {
                $($next).focus();
            }
        }
    });
});
//endregion *** Document Ready Method ***

//Resizing table column
$(function () {
    let pressed = false;
    let start = undefined;
    let startX, startWidth;

    $("table th").mousedown(function (e) {
        start = $(this);
        pressed = true;
        startX = e.pageX;
        startWidth = $(this).width();
        $(start).addClass("resizing");
    });

    $(document).mousemove(function (e) {
        if (pressed) {
            $(start).width(startWidth + (e.pageX - startX));
        }
    });
    $(document).mouseup(function () {
        if (pressed) {
            $(start).removeClass("resizing");
            pressed = false;
        }
    });
});