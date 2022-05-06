package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class NotificationDto {
    //region variables
    private String notificationId;
    private String subject;
    private String noticeMessage;
    private String senderName;
    private String username;
    private Date notifiedDate;
    private Date readDate;
    private String notifiedTime;
    private String senderId;
    private String url;
    private String notifyToId;
    private String recipientId;
    private Date lastSeenOn;
    private String createdBy;
    private Date createdDate;
    private BigInteger notificationCount;
    private List<String> notifyTo;
    //endregion
}
