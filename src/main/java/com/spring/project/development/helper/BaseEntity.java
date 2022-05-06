package com.spring.project.development.helper;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    //region private variables
    @Column(name = "createdBy", columnDefinition = "varchar(255)")
    private String createdBy;

    @Column(name = "createdDate", columnDefinition = "datetime")
    private Date createdDate;
    //endregion

    //region setters and getters

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    //endregion
}
