package com.spring.project.development.voler.entity.sa;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created By zepaG on 8/19/2021.
 */
@Setter
@Getter
@Entity
@Table(name = "sa_request_password_change")
public class RequestPasswordChange extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "requestId")
    private String requestId;

    @Column(name = "status")
    private Character status;

    @Column(name = "email")
    private String email;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;
    //endregion
}
