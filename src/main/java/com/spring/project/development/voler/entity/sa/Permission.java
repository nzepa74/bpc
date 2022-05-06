package com.spring.project.development.voler.entity.sa;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "sa_permission")
@Setter
@Getter
public class Permission extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "permissionId")
    private String permissionId;

    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "screenId")
    private Integer screenId;

    @Column(name = "viewAllowed")
    private Character viewAllowed;

    @Column(name = "editAllowed")
    private Character editAllowed;

    @Column(name = "deleteAllowed")
    private Character deleteAllowed;

    @Column(name = "saveAllowed")
    private Character saveAllowed;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;
    //endregion

}
