package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
 import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfBcpmProdSaleAddTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tfBcpmProdSaleAddTarget")
public class TfBcpmProdSaleAddTargetController {
    private final CommonService commonService;
    private final TfBcpmProdSaleAddTargetService tfBcpmProdSaleAddTargetService;

    public TfBcpmProdSaleAddTargetController(CommonService commonService, TfBcpmProdSaleAddTargetService tfBcpmProdSaleAddTargetService) {
        this.commonService = commonService;
        this.tfBcpmProdSaleAddTargetService = tfBcpmProdSaleAddTargetService;
    }

    @RequestMapping(value = "/addTarget", method = RequestMethod.POST)
    public ResponseMessage addTarget(HttpServletRequest request, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfBcpmProdSaleAddTargetService.addTarget(request, currentUser, prodSaleTargetDto);
    }

    @RequestMapping(value = "/getTargetActivity", method = RequestMethod.GET)
    public List<TfDhiProdSaleTarget> getTarget() {
        return commonService.getProdSaleTarget();
    }

    @RequestMapping(value = "/getAllocatedWt", method = RequestMethod.GET)
    public ResponseMessage getAllocatedWt(String year, String companyId) {
        return commonService.getAllocatedWt(year, companyId);
    }

}
