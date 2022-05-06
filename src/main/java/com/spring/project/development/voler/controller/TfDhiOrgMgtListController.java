package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.TargetStageDto;
import com.spring.project.development.voler.service.TfDhiOrgMgtListService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/tfDhiOrgMgtList")
public class TfDhiOrgMgtListController {
    private final TfDhiOrgMgtListService tfDhiOrgMgtListService;

    public TfDhiOrgMgtListController(TfDhiOrgMgtListService tfDhiOrgMgtListService) {
        this.tfDhiOrgMgtListService = tfDhiOrgMgtListService;
    }

    @RequestMapping(value = "/searchTarget", method = RequestMethod.GET)
    public ResponseMessage searchTarget(String year, String companyId) {
        return tfDhiOrgMgtListService.searchTarget(year, companyId);
    }

    @RequestMapping(value = "/searchByStage", method = RequestMethod.GET)
    public ResponseMessage searchByStage(String stageId) {
        return tfDhiOrgMgtListService.searchByStage(stageId);
    }

    @RequestMapping(value = "/getWriteup", method = RequestMethod.GET)
    public ResponseMessage getWriteup(String targetAuditId) {
        return tfDhiOrgMgtListService.getWriteup(targetAuditId);
    }

    @RequestMapping(value = "/getStages", method = RequestMethod.GET)
    public ResponseMessage getStages(String year, String companyId) {
        return tfDhiOrgMgtListService.getStages(year, companyId);
    }

    //    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public ResponseMessage submit(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtListService.submit(currentUser, targetStageDto);
    }

    //    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/revert", method = RequestMethod.POST)
    public ResponseMessage revert(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtListService.revert(currentUser, targetStageDto);
    }//    @PreAuthorize("hasAuthority('1-ADD')")

    @RequestMapping(value = "/approve", method = RequestMethod.POST)
    public ResponseMessage approve(HttpServletRequest request, TargetStageDto targetStageDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtListService.approve(currentUser, targetStageDto);
    }

    @RequestMapping(value = "/checkPermission", method = RequestMethod.GET)
    public ResponseMessage checkPermission(String year, String companyId) {
        return tfDhiOrgMgtListService.checkPermission(year, companyId);
    }
}
