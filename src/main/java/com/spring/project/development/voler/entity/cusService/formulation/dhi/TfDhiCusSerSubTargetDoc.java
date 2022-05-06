package com.spring.project.development.voler.entity.cusService.formulation.dhi;

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
@Table(name = "tf_dhi_cus_ser_sub_target_doc")
public class TfDhiCusSerSubTargetDoc extends BaseEntity {
    //region private variables
    @Id
    @Column(name = "fileId")
    private String fileId;

    @Column(name = "subTargetId")
    private String subTargetId;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileExtension")
    private String fileExtension;

    @Column(name = "fileSize")
    private String fileSize;

    @Column(name = "fileUrl")
    private String fileUrl;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;
    //endregion
}
