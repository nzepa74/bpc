package com.spring.project.development.voler.entity.sa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "sa_company_mapping")
public class CompanyMapping {

    @Id
    @Column(name = "companyMappingId")
    private String companyMappingId;

    @Column(name = "userId")
    private String userId;

    @Column(name = "companyId")
    private String companyId;

}
