package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.service.CompactDocService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By zepaG on 3/21/2022.
 */
@RestController
@RequestMapping("api/compactDoc")
public class CompactDocController {
    private final CompactDocService compactDocService;

    public CompactDocController(CompactDocService compactDocService) {
        this.compactDocService = compactDocService;
    }

    @RequestMapping(value = "/getFinWriteup", method = RequestMethod.GET)
    public ResponseMessage getCompanies(Integer stage, String year, String companyId) {
        return compactDocService.getFinWriteup(stage, year, companyId);
    }

    @RequestMapping(value = "/getCusSerWriteup", method = RequestMethod.GET)
    public ResponseMessage getCusSerWriteup(Integer stage, String year, String companyId) {
        return compactDocService.getCusSerWriteup(stage, year, companyId);
    }

    @RequestMapping(value = "/getOrgMgtWriteup", method = RequestMethod.GET)
    public ResponseMessage getOrgMgtWriteup(Integer stage, String year, String companyId) {
        return compactDocService.getOrgMgtWriteup(stage, year, companyId);
    }

    @RequestMapping(value = "/getProdSaleWriteup", method = RequestMethod.GET)
    public ResponseMessage getProdSaleWriteup(Integer stage, String year, String companyId) {
        return compactDocService.getProdSaleWriteup(stage, year, companyId);
    }

    @RequestMapping(value = "/searchProdSaleTarget", method = RequestMethod.GET)
    public ResponseMessage searchProdSaleTarget(Integer stage, String year, String companyId) {
        return compactDocService.searchProdSaleTarget(stage, year, companyId);
    }

}
