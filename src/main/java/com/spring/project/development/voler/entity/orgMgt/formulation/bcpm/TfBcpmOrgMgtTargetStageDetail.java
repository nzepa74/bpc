package com.spring.project.development.voler.entity.orgMgt.formulation.bcpm;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created By zepaG on 3/20/2022.
 */
@Setter
@Getter
@Entity
@Table(name = "tf_bcpm_org_mgt_target_stage_detail")
public class TfBcpmOrgMgtTargetStageDetail extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "stageDetailId", columnDefinition = "varchar(255)")
    private String stageDetailId;

    @Column(name = "stageId", columnDefinition = "varchar(255)")
    private String stageId;

    @Column(name = "targetAuditId", columnDefinition = "varchar(255)")
    private String targetAuditId;
    //endregion
}
