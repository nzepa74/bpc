package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.WeightageSetupDto;
import com.spring.project.development.voler.service.WeightageSetupService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/weightageSetup")
public class WeightageSetupController {

    private final WeightageSetupService weightageSetupService;

    public WeightageSetupController(WeightageSetupService weightageSetupService) {
        this.weightageSetupService = weightageSetupService;
    }

    @RequestMapping(value = "/addWeightage", method = RequestMethod.POST)
    public ResponseMessage addWeightage(HttpServletRequest request, WeightageSetupDto weightageSetupDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return weightageSetupService.addWeightage(currentUser, weightageSetupDto);
    }

    @RequestMapping(value = "/updateWeightage", method = RequestMethod.POST)
    public ResponseMessage updateWeightage(HttpServletRequest request, WeightageSetupDto weightageSetupDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return weightageSetupService.updateWeightage(currentUser, weightageSetupDto);
    }

    @RequestMapping(value = "/getWeightage", method = RequestMethod.GET)
    public ResponseMessage getWeightage(String year) {
        return weightageSetupService.getWeightage(year);
    }

    @RequestMapping(value = "/getWeightByWeightageSetupId", method = RequestMethod.GET)
    public ResponseMessage getWeightByWeightageSetupId(String weightageSetupId) {
        return weightageSetupService.getWeightByWeightageSetupId(weightageSetupId);
    }

}
