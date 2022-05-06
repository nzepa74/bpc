package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.service.CommonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;

@RestController
@RequestMapping("api/common")
public class CommonController {
    private final CommonService commonService;

    public CommonController(CommonService commonService) {
        this.commonService = commonService;
    }

    @RequestMapping(value = "/getCompanyInfo", method = RequestMethod.GET)
    public ResponseMessage getCompanyInfo(String companyId) {
        return commonService.getCompanyInfo(companyId);
    }

    @RequestMapping(value = "/getAllCompanyScore", method = RequestMethod.GET)
    public ResponseMessage getAllCompanyScore(String year) {
        return commonService.getAllCompanyScore(year);
    }

    @RequestMapping(value = "/evaluationScore", method = RequestMethod.GET)
    public ResponseMessage evaluationScore(String year, String companyId) {
        return commonService.evaluationScore(year, companyId);
    }

    @RequestMapping(value = "/isNewComment", method = RequestMethod.GET)
    public BigInteger getPreviousComment(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return commonService.isNewComment(currentUser);
    }
}
