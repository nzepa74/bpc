package com.spring.project.development.voler.entity;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notification_last_seen")
@Getter
@Setter
public class NotificationLastSeen extends BaseEntity {

    //region variables
    @Id
    @Column(name = "userId", columnDefinition = "varchar(255)")//this table is updated on clicking bell icon
    private String userId;
    //endregion
}
