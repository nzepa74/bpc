
package com.spring.project.development.voler.entity.financial.formulation.bcpm;
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
@Table(name = "tf_bcpm_fin_target_writeup_a")
public class TfBcpmFinTargetWriteup_a extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "writeupAuditId", columnDefinition = "varchar(255)")
    private String writeupAuditId;

    @Column(name = "writeupId", columnDefinition = "varchar(255)")
    private String writeupId;

    @Column(name = "targetId", columnDefinition = "varchar(255)")
    private String targetId;

    @Column(name = "targetAuditId", columnDefinition = "varchar(255)")
    private String targetAuditId;

    @Column(name = "background", columnDefinition = "longtext")
    private String background;

    @Column(name = "output", columnDefinition = "longtext")
    private String output;

    @Column(name = "risks", columnDefinition = "longtext")
    private String risks;

    @Column(name = "evalMethod", columnDefinition = "longtext")
    private String evalMethod;

    @Column(name = "evalFormulaId", columnDefinition = "int")
    private Integer evalFormulaId;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
