package com.spring.project.development.voler.entity.info;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "info_files")
@Getter
@Setter
public class InfoHelpDoc extends BaseEntity {

    //region variables
    @Id
    @Column(name = "fileId")
    private String fileId;

    @Column(name = "fileUrl")
    private String fileUrl;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileExtension")
    private String fileExtension;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;

    //endregion

}
