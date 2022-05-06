package com.spring.project.development.voler.entity;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "notify_to")
@Getter
@Setter
public class NotifyTo extends BaseEntity {

    //region variables
    @Id
    @Column(name = "notifyToId", columnDefinition = "varchar(255)")
    private String notifyToId;

    @Column(name = "notificationId", columnDefinition = "varchar(255)")
    private String notificationId;

    @Column(name = "recipientId", columnDefinition = "varchar(255)")//it must be userId
    private String recipientId;

    @Column(name = "readDate", columnDefinition = "datetime")
    private Date readDate;

    //endregion
}
