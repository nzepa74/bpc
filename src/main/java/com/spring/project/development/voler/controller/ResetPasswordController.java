package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.ResetPasswordDto;
import com.spring.project.development.voler.service.ResetPasswordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created By zepaG on 8/16/2021.
 */
@RestController
@RequestMapping("api/resetPassword")
public class ResetPasswordController {
    private final ResetPasswordService resetPasswordService;

    public ResetPasswordController(ResetPasswordService resetPasswordService) {
        this.resetPasswordService = resetPasswordService;
    }

    @RequestMapping(value = "/requestPasswordChange", method = RequestMethod.POST)
    public ResponseMessage requestPasswordChange(ResetPasswordDto resetPasswordDto) throws Exception {
        return resetPasswordService.requestPasswordChange(resetPasswordDto);
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public ResponseMessage resetPassword(ResetPasswordDto resetPasswordDto) {
        return resetPasswordService.resetPassword(resetPasswordDto);
    }

}
