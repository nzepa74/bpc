package com.spring.project.development.voler.service;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.helper.SystemDataInt;
import com.spring.project.development.helper.UuidGenerator;
import com.spring.project.development.voler.dao.NotificationDao;
import com.spring.project.development.voler.dto.NotificationDto;
import com.spring.project.development.voler.entity.Notification;
import com.spring.project.development.voler.entity.NotificationLastSeen;
import com.spring.project.development.voler.entity.NotifyTo;
import com.spring.project.development.voler.repository.NotificationLastSeenRepository;
import com.spring.project.development.voler.repository.NotificationRepository;
import com.spring.project.development.voler.repository.NotifyToRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 12/30/2021.
 */
@Service
public class NotificationService {
    private final NotificationDao notificationDao;
    private final NotificationRepository notificationRepository;
    private final NotifyToRepository notifyToRepository;
    private final NotificationLastSeenRepository notificationLastSeenRepository;

    public NotificationService(NotificationDao notificationDao, NotificationRepository notificationRepository, NotifyToRepository notifyToRepository, NotificationLastSeenRepository notificationLastSeenRepository) {
        this.notificationDao = notificationDao;
        this.notificationRepository = notificationRepository;
        this.notifyToRepository = notifyToRepository;
        this.notificationLastSeenRepository = notificationLastSeenRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage saveNotification(CurrentUser currentUser, NotificationDto notificationDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        Notification notification = new Notification();
        notification.setNotificationId(UuidGenerator.generateUuid());
        notification.setNoticeMessage(notificationDto.getNoticeMessage());
        notification.setUrl(notificationDto.getUrl());
        notification.setCreatedBy(currentUser.getUserId());
        notification.setCreatedDate(new Date());
        notificationRepository.save(notification);
        for (String notifyToUserId : notificationDto.getNotifyTo()) {
            NotifyTo notifyTo = new NotifyTo();
            notifyTo.setNotifyToId(UuidGenerator.generateUuid());
            notifyTo.setNotificationId(notification.getNotificationId());
            notifyTo.setRecipientId(notifyToUserId);
            notifyTo.setCreatedBy(currentUser.getUserId());
            notifyTo.setCreatedDate(new Date());
            notifyToRepository.save(notifyTo);
        }
        return responseMessage;
    }

    public ResponseMessage getNotificationCount(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        NotificationDto notificationDto = notificationDao.getNotificationCount(currentUser.getUserId());
        if (notificationDto != null) {
            responseMessage.setDTO(notificationDto);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getNotificationList(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<NotificationDto> notificationDtos = notificationDao.getNotificationList(currentUser.getUserId());
        if (notificationDtos != null) {
            responseMessage.setDTO(notificationDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

    public ResponseMessage getAllNotificationList(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        List<NotificationDto> notificationDtos = notificationDao.getAllNotificationList(currentUser.getUserId());
        if (notificationDtos != null) {
            responseMessage.setDTO(notificationDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        } else {
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        }
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage seenNotification(CurrentUser currentUser) {
        ResponseMessage responseMessage = new ResponseMessage();
        NotificationLastSeen notificationLastSeen = new NotificationLastSeen();
        notificationLastSeen.setUserId(currentUser.getUserId());
        notificationLastSeen.setCreatedBy(currentUser.getUserId());
        notificationLastSeen.setCreatedDate(new Date());
        notificationLastSeenRepository.deleteByUserId(currentUser.getUserId());
        notificationLastSeenRepository.save(notificationLastSeen);
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        return responseMessage;
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseMessage readNotification(CurrentUser currentUser, String notifyToId) {
        NotifyTo notifyToDb = notifyToRepository.findByNotifyToId(notifyToId);
        NotifyTo notifyTo = new ModelMapper().map(notifyToDb, NotifyTo.class);
        notifyTo.setReadDate(new Date());
        notifyToRepository.save(notifyTo);
        return null;
    }
}
