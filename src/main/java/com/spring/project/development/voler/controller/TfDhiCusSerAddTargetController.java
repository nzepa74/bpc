package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.CustomerServiceTargetDto;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.entity.cusService.formulation.dhi.TfDhiCusSerTargetActivity;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTarget;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfDhiCusSerAddTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tfDhiCusSerAddTarget")
public class TfDhiCusSerAddTargetController {
    private final CommonService commonService;
    private final TfDhiCusSerAddTargetService tfDhiCusSerAddTargetService;

    public TfDhiCusSerAddTargetController(CommonService commonService, TfDhiCusSerAddTargetService tfDhiCusSerAddTargetService) {
        this.commonService = commonService;
        this.tfDhiCusSerAddTargetService = tfDhiCusSerAddTargetService;
    }

    @RequestMapping(value = "/addTarget", method = RequestMethod.POST)
    public ResponseMessage addTarget(HttpServletRequest request, CustomerServiceTargetDto customerServiceTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiCusSerAddTargetService.addTarget(request, currentUser, customerServiceTargetDto);
    }

    @RequestMapping(value = "/getTargetActivity", method = RequestMethod.GET)
    public List<TfDhiCusSerTargetActivity> getTargetActivity() {
        return commonService.getTargetActivity();
    }

    @RequestMapping(value = "/getAllocatedWt", method = RequestMethod.GET)
    public ResponseMessage getAllocatedWt(String year, String companyId) {
        return commonService.getAllocatedWt(year, companyId);
    }

}
