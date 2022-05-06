package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.entity.info.About;
import com.spring.project.development.voler.service.AboutService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/about")
public class AboutController {
    private final AboutService aboutService;

    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

//    @PreAuthorize("hasAuthority('25-ADD')")
    @RequestMapping(value = "/addAbout", method = RequestMethod.POST)
    public ResponseMessage addAbout(HttpServletRequest request, About about) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return aboutService.addAbout(currentUser, about);
    }

//    @PreAuthorize("hasAuthority('25-EDIT')")
    @RequestMapping(value = "/editAbout", method = RequestMethod.POST)
    public ResponseMessage editAbout(HttpServletRequest request, About about) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return aboutService.editAbout(currentUser, about);
    }

    @RequestMapping(value = "/getAbout", method = RequestMethod.GET)
    public ResponseMessage getAbout() {
        return aboutService.getAbout();
    }
}
