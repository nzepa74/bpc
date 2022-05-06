package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.DocumentDto;
import com.spring.project.development.voler.entity.info.GeneralSupport;
import com.spring.project.development.voler.entity.info.TechnicalSupport;
import com.spring.project.development.voler.service.HelpService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("api/help")
public class HelpController {
    private final HelpService helpService;

    public HelpController(HelpService helpService) {
        this.helpService = helpService;
    }

    @RequestMapping(value = "/getFiles", method = RequestMethod.GET)
    public ResponseMessage getFiles() {
        return helpService.getFiles();
    }

//    @PreAuthorize("hasAuthority('26-ADD')")
    @RequestMapping(value = "/addFiles", method = RequestMethod.POST)
    public ResponseMessage addFiles(HttpServletRequest request, DocumentDto documentDto) throws IOException {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return helpService.addFiles(request, currentUser, documentDto);
    }

//    @PreAuthorize("hasAuthority('26-DELETE')")
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public ResponseMessage deleteFile(HttpServletRequest request, String fileId) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return helpService.deleteFile(currentUser, fileId);
    }

    @RequestMapping(value = "/viewFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage viewFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return helpService.viewFile(fileId, response);
    }

    @RequestMapping(value = "/downloadFile/{fileId}", method = RequestMethod.GET)
    public ResponseMessage downloadFile(@PathVariable String fileId, HttpServletResponse response) throws IOException {
        return helpService.downloadFile(fileId, response);
    }

//    @PreAuthorize("hasAuthority('26-ADD')")
    @RequestMapping(value = "/addGeneralSupport", method = RequestMethod.POST)
    public ResponseMessage addGeneralSupport(HttpServletRequest request, GeneralSupport generalSupport) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return helpService.addGeneralSupport(currentUser, generalSupport);
    }

    @RequestMapping(value = "/getGeneralSupport", method = RequestMethod.GET)
    public ResponseMessage getGeneralSupport() {
        return helpService.getGeneralSupport();
    }

//    @PreAuthorize("hasAuthority('26-ADD')")
    @RequestMapping(value = "/addTechnicalSupport", method = RequestMethod.POST)
    public ResponseMessage addTechnicalSupport(HttpServletRequest request, TechnicalSupport technicalSupport) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return helpService.addTechnicalSupport(currentUser, technicalSupport);
    }

    @RequestMapping(value = "/getTechnicalSupport", method = RequestMethod.GET)
    public ResponseMessage getTechnicalSupport() {
        return helpService.getTechnicalSupport();
    }
}
