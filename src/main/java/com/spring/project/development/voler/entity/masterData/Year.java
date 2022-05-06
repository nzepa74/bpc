package com.spring.project.development.voler.entity.masterData;

import com.spring.project.development.helper.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "md_year")
@Getter
@Setter
public class Year extends BaseEntity {

    //region variables
    @Id
    @Column(name = "year")
    private String year;

    @Column(name = "status")
    private Character status;

    @Column(name = "cmdFlag")
    private Character cmdFlag;

    @Column(name = "updatedDate")
    private Date updatedDate;

    @Column(name = "updatedBy")
    private String updatedBy;

    //endregion

}
