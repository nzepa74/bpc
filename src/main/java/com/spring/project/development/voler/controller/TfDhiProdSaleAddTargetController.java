package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.ProdSaleTargetDto;
import com.spring.project.development.voler.entity.prodSale.formulation.dhi.TfDhiProdSaleTarget;
import com.spring.project.development.voler.service.CommonService;
import com.spring.project.development.voler.service.TfDhiProdSaleAddTargetService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/tfDhiProdSaleAddTarget")
public class TfDhiProdSaleAddTargetController {
    private final CommonService commonService;
    private final TfDhiProdSaleAddTargetService tfDhiProdSaleAddTargetService;

    public TfDhiProdSaleAddTargetController(CommonService commonService, TfDhiProdSaleAddTargetService tfDhiProdSaleAddTargetService) {
        this.commonService = commonService;
        this.tfDhiProdSaleAddTargetService = tfDhiProdSaleAddTargetService;
    }

    @RequestMapping(value = "/addTarget", method = RequestMethod.POST)
    public ResponseMessage addTarget(HttpServletRequest request, ProdSaleTargetDto prodSaleTargetDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return tfDhiProdSaleAddTargetService.addTarget(request, currentUser, prodSaleTargetDto);
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
