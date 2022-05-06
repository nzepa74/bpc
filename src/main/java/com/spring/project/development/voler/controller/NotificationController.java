package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.service.NotificationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RequestMapping(value = "/getNotificationCount", method = RequestMethod.GET)
    public ResponseMessage getNotificationCount(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return notificationService.getNotificationCount(currentUser);
    }

    @RequestMapping(value = "/getNotificationList", method = RequestMethod.GET)
    public ResponseMessage getNotificationList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return notificationService.getNotificationList(currentUser);
    }

    @RequestMapping(value = "/getAllNotificationList", method = RequestMethod.GET)
    public ResponseMessage getAllNotificationList(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return notificationService.getAllNotificationList(currentUser);
    }

    @RequestMapping(value = "/seenNotification", method = RequestMethod.POST)
    public ResponseMessage seenNotification(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return notificationService.seenNotification(currentUser);
    }

    @RequestMapping(value = "/readNotification", method = RequestMethod.POST)
    public ResponseMessage readNotification(HttpServletRequest request, String notifyToId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return notificationService.readNotification(currentUser, notifyToId);
    }
}
