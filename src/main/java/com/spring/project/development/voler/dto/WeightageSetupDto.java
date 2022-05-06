package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Setter
@Getter
public class WeightageSetupDto {
    //region private variables
    private String weightageSetupId;
    private String companyId;
    private String companyName;
    private String year;
    private BigDecimal financialWt;
    private BigDecimal customerWt;
    private BigDecimal productionWt;
    private BigDecimal orgManagementWt;
    //endregion
}
