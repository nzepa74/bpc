package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.SaUserDto;
import com.spring.project.development.voler.service.MyProfileService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/myProfile")
public class MyProfileController {
    private final MyProfileService myProfileService;

    public MyProfileController(MyProfileService myProfileService) {
        this.myProfileService = myProfileService;
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseMessage changePassword(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.changePassword(currentUser, saUserDto);
    }

    @RequestMapping(value = "/getMyDetail", method = RequestMethod.GET)
    public ResponseMessage getMyDetail(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.getMyDetail(currentUser);
    }

    @RequestMapping(value = "/getMyCompany", method = RequestMethod.GET)
    public ResponseMessage getMyCompany(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.getMyCompany(currentUser);
    }

    @RequestMapping(value = "/getMappedCompanies", method = RequestMethod.GET)
    public ResponseMessage getMappedCompanies(HttpServletRequest request) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.getMappedCompanies(currentUser);
    }

    @RequestMapping(value = "/editFullName", method = RequestMethod.POST)
    public ResponseMessage editFullName(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.editFullName(currentUser, saUserDto);
    }

    @RequestMapping(value = "/editUsername", method = RequestMethod.POST)
    public ResponseMessage editUsername(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.editUsername(currentUser, saUserDto);
    }

    @RequestMapping(value = "/editEmail", method = RequestMethod.POST)
    public ResponseMessage editEmail(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.editEmail(currentUser, saUserDto);
    }

    @RequestMapping(value = "/editMobileNo", method = RequestMethod.POST)
    public ResponseMessage editMobileNo(HttpServletRequest request, SaUserDto saUserDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return myProfileService.editMobileNo(currentUser, saUserDto);
    }

}
