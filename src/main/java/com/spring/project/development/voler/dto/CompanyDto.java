package com.spring.project.development.voler.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
public class CompanyDto {

    //region private variables
    private String companyId;

    @NotNull(message = "Company name is required")
    @Size(min = 1, max = 150, message = "Company name must be maximum of 255 characters")
    private String companyName;

    private Character isParentCompany;
    private Boolean isParent;

    private String shortName;

    private MultipartFile logoMf;

    private byte[] logo;

    private String logoName;

    private String logoExtension;

    private Character status;
    //endregion

}
