package com.spring.project.development.voler.entity.sa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created By zepaG on 7/9/2021.
 */
@Setter
@Getter
@Entity
@Table(name = "sa_role")
public class SaRole {
    //region private variables
    @Id
    @Column(name = "roleId")
    private Integer roleId;

    @Column(name = "roleName")
    private String roleName;

    //endregion
}
