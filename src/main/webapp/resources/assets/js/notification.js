notification = (function () {
    "use strict";

    function _baseURL() {
        return 'api/notification/';
    }

    function getNotificationCount() {
        $('.notificationBell').removeClass('icon-animated-bell errorMsg');
        $('.nconut').removeClass('notificationCount errorMsg');
        $('.nconut').text('');
        $.ajax({
            url: _baseURL() + 'getNotificationCount',
            type: 'GET',
            success: function (res) {
                let data = res.dto;
                let notificationCount = data.notificationCount;
                 if (notificationCount > 0) {
                    notificationCount = parseInt(notificationCount) >= 10 ? '9+' : notificationCount;
                    $('.notificationBell').addClass('icon-animated-bell errorMsg');
                    $('.nconut').addClass('notificationCount errorMsg');
                    $('.nconut').text(notificationCount);
                }
            }
        });
    }

    function onClickBellIcon() {
        $('.notificationBell').on('click', function () {
            $.ajax({
                url: _baseURL() + 'seenNotification',
                type: 'POST',
                success: function (res) {
                    if (res.status === 1) {
                        $('.notificationBell').removeClass('icon-animated-bell errorMsg');
                        $('.nconut').removeClass('notificationCount errorMsg');
                        $('.nconut').text('');
                    }
                }
            });
        });
    }

    function formatAsNotifiedDate(date) {
        if (date)
            return $.datepicker.formatDate("M dd, yy", new Date(date));
        return '';
    }

    function getNotificationList() {
        $.ajax({
            url: _baseURL() + 'getNotificationList',
            type: 'GET',
            success: function (res) {
                let data = res.dto;
                let notiLi = '';
                if (data.length > 0) {
                    for (let i = 0; i < data.length; i++) {
                        let url = data[i].url;
                        let noticeMessage = data[i].noticeMessage;
                        let senderName = data[i].senderName;
                        let notifiedDate = data[i].notifiedDate;
                        let notifiedTime = data[i].notifiedTime;
                        let readDate = data[i].readDate;
                        let notifyToId = data[i].notifyToId;
                        let notifiedOn = formatAsNotifiedDate(notifiedDate) + ', ' + notifiedTime;
                        let notificationList = '<li title="Click to view" class="notificationLi cursor-pointer" style="background: #c9ddec; padding: 10px !important; border-bottom: 1px solid #e6f2f7">' +
                            '<span class="notificationUrl hidden">' + url + '</span>' +
                            '<span class="notifyToId hidden">' + notifyToId + '</span>' +
                            '<div>' +
                            '<h6 class="text-bold">' + senderName + '</h6>' +
                            '<small class="text-xs">' + noticeMessage + '</small>' +
                            '<small class="text-xs pull-right">' + notifiedOn + '</small>' +
                            '</div>' +
                            '</li>';

                        if (readDate != null) {
                            notificationList = '<li title="Click to view" class="notificationLi cursor-pointer" style="padding: 10px !important; border-bottom: 1px solid #e6f2f7">' +
                                '<span class="notificationUrl hidden">' + url + '</span>' +
                                '<span class="notifyToId hidden">' + notifyToId + '</span>' +
                                '<div>' +
                                '<h6 class="text-bold">' + senderName + '</h6>' +
                                '<small class="text-xs">' + noticeMessage + '</small>' +
                                '<small class="text-xs pull-right">' + notifiedOn + '</small>' +
                                '</div>' +
                                '</li>';
                        }
                        notiLi = notiLi + notificationList;
                    }
                    let seeAll = '<div class="nav-item cursor-pointer" style="">' +
                        '                        <div class="text-center">' +
                        '                            <a class="dropdown-item">' +
                        '                                <span class="text-bold">See All</span>' +
                        '                                <i class="fa fa-angle-right"></i>' +
                        '                            </a>' +
                        '                        </div>' +
                        '                    </div>';
                    $('.notificationList').empty().append(notiLi + seeAll);
                } else {
                    let emptyNotification = '<div class="p-2">' +
                        '<div class="form-group">' +
                        '<span style="font-size:15px;">&#128550;</span><span class="text-bold infoMsg"> You have no notifications right now.</span>' +
                        '</div>' +
                        '</div>';
                    $('.notificationList').empty().append(emptyNotification);
                }
            }
        });
    }

    function navigateToNotificationDetail() {
        $('#notificationDiv ul').on('click', 'li', function () {
            let notifyToId = $(this).closest('#notificationDiv .notificationLi').find('.notifyToId').text();
            let url = $(this).closest('#notificationDiv .notificationLi').find('.notificationUrl').text();
            window.location.href = url;
            $.ajax({
                url: _baseURL() + 'readNotification',
                type: 'POST',
                data: {notifyToId: notifyToId},
                success: function (res) {
                }
            });
        });

        $('#notificationDiv ul').on('click', 'div', function () {
            window.location.href = '/allNotification';
        });
    }

    return {
        getNotificationCount: getNotificationCount,
        getNotificationList: getNotificationList,
        onClickBellIcon: onClickBellIcon,
        navigateToNotificationDetail: navigateToNotificationDetail,
    }
})();

$(document).ready(function () {
    notification.getNotificationCount();
    notification.getNotificationList();
    notification.onClickBellIcon();
    notification.navigateToNotificationDetail();
});