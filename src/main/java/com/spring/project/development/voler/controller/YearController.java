package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.entity.masterData.Year;
import com.spring.project.development.voler.service.YearService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/year")
public class YearController {

    private final YearService yearService;

    public YearController(YearService yearService) {
        this.yearService = yearService;
    }

//    @PreAuthorize("hasAuthority('1-ADD')")
    @RequestMapping(value = "/addYear", method = RequestMethod.POST)
    public ResponseMessage addYear(HttpServletRequest request, Year year) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return yearService.addYear(currentUser, year);
    }

//    @PreAuthorize("hasAuthority('1-EDIT')")
    @RequestMapping(value = "/updateYear", method = RequestMethod.POST)
    public ResponseMessage updateYear(HttpServletRequest request, Year year) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return yearService.updateYear(currentUser, year);
    }

    @RequestMapping(value = "/getYears", method = RequestMethod.GET)
    public List<Year> getYears() {
        return yearService.getYears();
    }

}
