package com.spring.project.development.voler.entity;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
@Getter
@Setter
public class Notification extends BaseEntity {

    //region variables
    @Id
    @Column(name = "notificationId", columnDefinition = "varchar(255)")
    private String notificationId;

    @Column(name = "noticeMessage", columnDefinition = "longtext")
    private String noticeMessage;

    @Column(name = "url", columnDefinition = "varchar(1000)")
    private String url;

    //endregion
}
