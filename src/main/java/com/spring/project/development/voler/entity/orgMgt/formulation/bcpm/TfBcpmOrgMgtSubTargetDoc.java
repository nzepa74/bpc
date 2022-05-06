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
@Table(name = "tf_bcpm_org_mgt_sub_target_doc")
public class TfBcpmOrgMgtSubTargetDoc extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "fileId", columnDefinition = "varchar(255)")
    private String fileId;

    @Column(name = "subTargetId", columnDefinition = "varchar(255)")
    private String subTargetId;

    @Column(name = "fileName", columnDefinition = "varchar(255)")
    private String fileName;

    @Column(name = "fileExtension", columnDefinition = "varchar(255)")
    private String fileExtension;

    @Column(name = "fileSize", columnDefinition = "varchar(255)")
    private String fileSize;

    @Column(name = "fileUrl", columnDefinition = "varchar(255)")
    private String fileUrl;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;
    //endregion
}
