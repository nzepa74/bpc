package com.spring.project.development.voler.entity.orgMgt.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_org_mgt_sub_target")
public class TfBcpmOrgMgtSubTarget extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "subTargetId", columnDefinition = "varchar(255)")
    private String subTargetId;

    @Column(name = "targetId", columnDefinition = "varchar(255)")
    private String targetId;

    @Column(name = "subTarget", columnDefinition = "longtext")
    private String subTarget;

    @Column(name = "deadline", columnDefinition = "date")
    private Date deadline;

    @Column(name = "weightage", columnDefinition = "decimal(18,2)")
    private BigDecimal weightage;

    @Column(name = "isNegative", columnDefinition = "char(1)")
    private Character isNegative;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
