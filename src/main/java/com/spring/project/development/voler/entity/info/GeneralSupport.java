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
@Table(name = "info_general_support")
@Getter
@Setter
public class GeneralSupport extends BaseEntity {

    //region variables
    @Id
    @Column(name = "generalSupportId")
    private String generalSupportId;

    @Column(name = "contents")
    private String contents;

    @Column(name = "updatedBy")
    private String updatedBy;

    @Column(name = "updatedDate")
    private Date updatedDate;

    //endregion

}
