package com.spring.project.development.voler.entity.orgMgt.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_org_mgt_sub_target_reviewer_remark")
public class TfBcpmOrgMgtSubTargetReviewerRemark extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "remarkId", columnDefinition = "varchar(255)")
    private String remarkId;

    @Column(name = "subTargetId", columnDefinition = "varchar(255)")
    private String subTargetId;

    @Column(name = "remark", columnDefinition = "longtext")
    private String remark;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
