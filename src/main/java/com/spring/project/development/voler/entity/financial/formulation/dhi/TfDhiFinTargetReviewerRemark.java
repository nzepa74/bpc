package com.spring.project.development.voler.entity.financial.formulation.dhi;

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
@Table(name = "tf_dhi_fin_reviewer_remark")
public class TfDhiFinTargetReviewerRemark extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "remarkId")
    private String remarkId;

    @Column(name = "targetId")
    private String targetId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
