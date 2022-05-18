package com.spring.project.development.voler.dto;

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
@Data
public class EmployeeDto {

    private String employeeId;

    private String fullName;

    private Character gender;

    private BigInteger contactNo;

    private String updatedBy;

    private Date updatedDate;


}
