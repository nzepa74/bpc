allNotification = (function () {
    "use strict";

    function _baseURL() {
        return 'api/notification/';
    }


    function formatAsNotifiedDate(date) {
        if (date)
            return $.datepicker.formatDate("M dd, yy", new Date(date));
        return '';
    }

    function getAllNotificationList() {
        $.ajax({
            url: _baseURL() + 'getAllNotificationList',
            type: 'GET',
            success: function (res) {
                let data = res.dto;
                let notiLi = '';
                for (let i = 0; i < data.length; i++) {
                    let noticeMessage = data[i].noticeMessage;
                    let senderName = data[i].senderName;
                    let notifiedDate = data[i].notifiedDate;
                    let notifiedTime = data[i].notifiedTime;
                    let readDate = data[i].readDate;
                    let allNotificationId = data[i].allNotificationId;
                    let notificationDetailId = data[i].notificationDetailId;
                    let url = data[i].url;
                    let notifiedOn = formatAsNotifiedDate(notifiedDate) + ', ' + notifiedTime;

                    let allNotificationList = '<li class="nav-item cursor-pointer notificationLi">' +
                        '<span class="notificationUrl hidden">' + url + '</span>' +
                        '<span class="notificationDetailId hidden">' + notificationDetailId + '</span>' +
                        '                       <a class="dropdown-item">' +
                        '                       <span class="avatar h5">' + getShortName(senderName) + '</span>' +
                        '                        <span style="margin-left: 7px">' +
                        '                              <strong>' + senderName + '</strong>' + '   ' + '<small>' + notifiedOn + '</small>' +
                        '                        </span>' +
                        '                        <br><span class="message" style="margin-left: 7px !important;">' + noticeMessage + '</span>' +
                        '                       </a>' +
                        '                    </li>';
                    if (readDate != null) {
                        allNotificationList = '<li class="nav-item success notificationLi">' +
                            '<span class="notificationUrl hidden">' + url + '</span>' +
                            '<span class="notificationDetailId hidden">' + notificationDetailId + '</span>' +
                            '                       <a class="dropdown-item cursor-pointer">' +
                            '                       <span class="avatar h5">' + getShortName(senderName) + '</span>' +
                            '                        <span style="margin-left: 7px">' +
                            '                              <strong>' + senderName + '</strong>' + '   ' + '<small>' + notifiedOn + '</small>' +
                            '                        </span>' +
                            '                        <br><span class="message" style="margin-left: 7px !important;">' + noticeMessage + '</span>' +
                            '                       </a>' +
                            '                    </li>';
                    }
                    notiLi = notiLi + allNotificationList;
                }
                $('#allNotificationList').empty().append(notiLi);
            }
        });
    }

    function navigateToNotificationDetail() {
        $('#allNotificationDiv ul').on('click', 'li', function () {
            let notificationDetailId = $(this).closest('#allNotificationDiv .notificationLi').find('.notificationDetailId').text();
            let url = $(this).closest('#allNotificationDiv .notificationLi').find('.notificationUrl').text();
            window.location.href = url;
            $.ajax({
                url: _baseURL() + 'readNotification',
                type: 'POST',
                data: {notificationDetailId: notificationDetailId},
                success: function (res) {

                }
            });
        });
    }

    return {
        getAllNotificationList: getAllNotificationList,
        navigateToNotificationDetail: navigateToNotificationDetail,
    }
})();

$(document).ready(function () {
    allNotification.getAllNotificationList();
    allNotification.navigateToNotificationDetail();
});