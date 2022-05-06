package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EvaluationScoreDto {
    //region private variables
    private BigDecimal score;
    private String year;
    private String shortName;
    //endregion
}
