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
 * Created By zepaG on 7/9/2021.
 */
@Setter
@Getter
@Entity
@Table(name = "sa_user")
public class SaUser extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "mobileNo")
    private String mobileNo;

    @Column(name = "status")
    private Character status;

    @Column(name = "companyId")
    private String companyId;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    //endregion
}
