package com.spring.project.development.voler.entity;

import com.spring.project.development.helper.BaseEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created By zepaG on 5/18/2022.
 */
@Entity
@Table(name = "employee_a")
@Data
public class EmployeeA extends BaseEntity {
    @Id
    @Column(name = "employeeAuditId", columnDefinition = "varchar(255)")
    private String employeeAuditId;

    @Column(name = "employeeId", columnDefinition = "varchar(255)")
    private String employeeId;

    @Column(name = "fullName", columnDefinition = "varchar(255)")
    private String fullName;

    @Column(name = "gender", columnDefinition = "char(1)")
    private Character gender;

    @Column(name = "contactNo", columnDefinition = "bigint")
    private BigInteger contactNo;

    @Column(name = "cmdFlag", columnDefinition = "char(1)")
    private Character cmdFlag;

    @Column(name = "updatedBy", columnDefinition = "varchar(255)")
    private String updatedBy;

    @Column(name = "updatedDate", columnDefinition = "datetime")
    private Date updatedDate;


}
