package com.spring.project.development.voler.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Date;

/**
 * Created By zepaG on 5/18/2022.
 */
@Data
public class EmployeeDto {

    private String employeeId;

    @NotNull(message = "Full name is required.")
    @Size(min = 1, max = 10, message = "Full name must contain maximum of 10 characters.")
    private String fullName;

    private Character gender;

    private BigInteger contactNo;

    private String updatedBy;

    private Date updatedDate;


}
