package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.FinancialTargetDto;
import com.spring.project.development.voler.entity.financial.formulation.bcpm.TfBcpmFinTarget;
import com.spring.project.development.voler.entity.financial.formulation.dhi.TfDhiFinTarget;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmFinAddTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tfBcpmFinAddTarget")
public class TfBcpmFinAddTargetController {
    private final CommonService commonService;
    private final TfBcpmFinAddTargetService tfBcpmFinAddTargetService;

    public TfBcpmFinAddTargetController(CommonService commonService, TfBcpmFinAddTargetService tfBcpmFinAddTargetService) {
        this.commonService = commonService;
        this.tfBcpmFinAddTargetService = tfBcpmFinAddTargetService;
    }

    @RequestMapping(value = "/addTarget", method = RequestMethod.POST)
    public ResponseMessage addTarget(HttpServletRequest request, FinancialTargetDto financialTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmFinAddTargetService.addTarget(request, currentUser, financialTargetDto);
    }

    @RequestMapping(value = "/getFinKpi", method = RequestMethod.GET)
    public List<TfDhiFinTarget> getFinKpi() {
        return commonService.getFinKpi();
    }

    @RequestMapping(value = "/getAllocatedWt", method = RequestMethod.GET)
    public ResponseMessage getAllocatedWt(String year, String companyId) {
        return commonService.getAllocatedWt(year, companyId);
    }

}
