package com.spring.project.development.voler.controller;

import com.spring.project.development.helper.CurrentUser;
import com.spring.project.development.helper.DropdownDTO;
import com.spring.project.development.helper.ResponseMessage;
import com.spring.project.development.voler.dto.EmployeeDto;
import com.spring.project.development.voler.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created By zepaG on 5/18/2022.
 */
@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /// localhost:8091/api/employee/addEmployee
    @RequestMapping(value = "/addEmployee", method = RequestMethod.POST)//api
    public ResponseMessage addEmployee(HttpServletRequest request, EmployeeDto employeeDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return employeeService.addEmployee(currentUser, employeeDto);
    }

    @RequestMapping(value = "/editEmployee", method = RequestMethod.POST)//api
    public ResponseMessage editEmployee(HttpServletRequest request, EmployeeDto employeeDto) {
        CurrentUser currentUser = (CurrentUser) request.getSession().getAttribute("currentUser");
        return employeeService.editEmployee(currentUser, employeeDto);
    }

    @RequestMapping(value = "/getAllEmployees", method = RequestMethod.GET)//api
    public ResponseMessage getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @RequestMapping(value = "/getEmployeeInfo", method = RequestMethod.GET)//api
    public ResponseMessage getEmployeeInfo(String employeeId) {
        return employeeService.getEmployeeInfo(employeeId);
    }

    @RequestMapping(value = "/getGeogListByDzoId", method = RequestMethod.GET)
    public List<DropdownDTO> getGeogListByDzoId(String dzoId) {
        return employeeService.getGeogListByDzoId(dzoId);
    }

}
