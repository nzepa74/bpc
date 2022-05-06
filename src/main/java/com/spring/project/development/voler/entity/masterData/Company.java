package com.spring.project.development.voler.entity.masterData;

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
@Table(name = "md_company")
public class Company extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "companyId")
    private String companyId;

    @Column(name = "isParentCompany")
    private Character isParentCompany;

    @Column(name = "companyName")
    private String companyName;

    @Column(name = "shortName")
    private String shortName;

    @Column(name = "status")
    private Character status;

    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logoName")
    private String logoName;

    @Column(name = "logoExtension")
    private String logoExtension;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    //endregion
}
