package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.service.TfBcpmProdSaleListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/tfBcpmProdSaleList")
public class TfBcpmProdSaleListController {
    private final TfBcpmProdSaleListService tfBcpmProdSaleListService;

    public TfBcpmProdSaleListController(TfBcpmProdSaleListService tfBcpmProdSaleListService) {
        this.tfBcpmProdSaleListService = tfBcpmProdSaleListService;
    }

    @RequestMapping(value = "/searchTarget", method = RequestMethod.GET)
    public ResponseMessage searchTarget(String year, String companyId) {
        return tfBcpmProdSaleListService.searchTarget(year, companyId);
    }

    @RequestMapping(value = "/searchSubTarget", method = RequestMethod.GET)
    public ResponseMessage searchSubTarget(String targetAuditId) {
        return tfBcpmProdSaleListService.searchSubTarget(targetAuditId);
    }

    @RequestMapping(value = "/searchByStage", method = RequestMethod.GET)
    public ResponseMessage searchByStage(String stageId) {
        return tfBcpmProdSaleListService.searchByStage(stageId);
    }

    @RequestMapping(value = "/getWriteup", method = RequestMethod.GET)
    public ResponseMessage getWriteup(String targetAuditId) {
        return tfBcpmProdSaleListService.getWriteup(targetAuditId);
    }

    @RequestMapping(value = "/getStages", method = RequestMethod.GET)
    public ResponseMessage getStages(String year, String companyId) {
        return tfBcpmProdSaleListService.getStages(year, companyId);
    }

    //    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseMessage submit(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleListService.submit(currentUser, targetStageDto);
    }

    //    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/revert", method = RequestMethod.POST)
    public ResponseMessage revert(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleListService.revert(currentUser, targetStageDto);
    }

    //    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseMessage approve(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleListService.approve(currentUser, targetStageDto);
    }

    @RequestMapping(value = "/checkPermission", method = RequestMethod.GET)
    public ResponseMessage checkPermission(String year, String companyId) {
        return tfBcpmProdSaleListService.checkPermission(year, companyId);
    }
}
