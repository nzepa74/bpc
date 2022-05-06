package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.CompanyDto;
import com.spring.project.development.voler.service.CompanyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("api/company")
public class CompanyController {
    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @RequestMapping(value = "/addCompany", method = RequestMethod.POST)
    public ResponseMessage addCompany(HttpServletRequest request, CompanyDto companyDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyService.addCompany(currentUser, companyDto);
    }

    @RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
    public ResponseMessage updateCompany(HttpServletRequest request, CompanyDto companyDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return companyService.updateCompany(currentUser, companyDto);
    }

    @RequestMapping(value = "/getCompanies", method = RequestMethod.GET)
    public ResponseMessage getCompanies() {
        return companyService.getCompanies();
    }

    @RequestMapping(value = "/getByCompanyId", method = RequestMethod.GET)
    public ResponseMessage getByCompanyId(String companyId) {
        return companyService.getByCompanyId(companyId);
    }

}
