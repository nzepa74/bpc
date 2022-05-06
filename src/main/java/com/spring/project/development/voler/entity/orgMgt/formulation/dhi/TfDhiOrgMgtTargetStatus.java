package com.spring.project.development.voler.entity.orgMgt.formulation.dhi;

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
@Table(name = "tf_dhi_org_mgt_target_status")
public class TfDhiOrgMgtTargetStatus extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "statusId")
    private String statusId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "statusFlag")
    private Character statusFlag;
    //endregion
}
