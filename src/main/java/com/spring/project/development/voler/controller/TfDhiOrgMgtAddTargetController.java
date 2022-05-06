package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.OrgMgtTargetDto;
import com.spring.project.development.voler.entity.orgMgt.formulation.dhi.TfDhiOrgMgtTargetActivity;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfDhiOrgMgtAddTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tfDhiOrgMgtAddTarget")
public class TfDhiOrgMgtAddTargetController {
    private final CommonService commonService;
    private final TfDhiOrgMgtAddTargetService tfDhiOrgMgtAddTargetService;

    public TfDhiOrgMgtAddTargetController(CommonService commonService, TfDhiOrgMgtAddTargetService tfDhiOrgMgtAddTargetService) {
        this.commonService = commonService;
        this.tfDhiOrgMgtAddTargetService = tfDhiOrgMgtAddTargetService;
    }

    @RequestMapping(value = "/addTarget", method = RequestMethod.POST)
    public ResponseMessage addTarget(HttpServletRequest request, OrgMgtTargetDto orgMgtTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiOrgMgtAddTargetService.addTarget(request, currentUser, orgMgtTargetDto);
    }

    @RequestMapping(value = "/getTargetActivity", method = RequestMethod.GET)
    public List<TfDhiOrgMgtTargetActivity> getTargetActivity() {
        return commonService.getOrgMgtTargetActivity();
    }

    @RequestMapping(value = "/getAllocatedWt", method = RequestMethod.GET)
    public ResponseMessage getAllocatedWt(String year, String companyId) {
        return commonService.getAllocatedWt(year, companyId);
    }

}
