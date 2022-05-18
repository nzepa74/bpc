package com.spring.project.development.voler.service;

import com.spring.project.development.helper.*;
import com.spring.project.development.voler.dao.EmployeeDao;
import com.spring.project.development.voler.dto.EmployeeDto;
import com.spring.project.development.voler.entity.Employee;
import com.spring.project.development.voler.entity.EmployeeA;
import com.spring.project.development.voler.entity.sa.SaUser;
import com.spring.project.development.voler.repository.EmployeeARepo;
import com.spring.project.development.voler.repository.EmployeeRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created By zepaG on 5/18/2022.
 */
@Service
public class EmployeeService {
    private final EmployeeDao employeeDao;

    private final EmployeeRepo employeeRepo;
    private final EmployeeARepo employeeRepoA;

    public EmployeeService(EmployeeDao employeeDao, EmployeeRepo employeeRepo, EmployeeARepo employeeRepoA) {
        this.employeeDao = employeeDao;
        this.employeeRepo = employeeRepo;
        this.employeeRepoA = employeeRepoA;
    }

    public ResponseMessage addEmployee(CurrentUser currentUser, EmployeeDto employeeDto) {
        ResponseMessage responseMessage = new ResponseMessage();

        //all validations here


        Employee employee = new ModelMapper().map(employeeDto, Employee.class);
//        employee.setEmployeeId(employeeDto.getEmployeeId());
//        employee.setFullName(employeeDto.getFullName());
//        employee.setGender(employeeDto.getGender());
//        employee.setContactNo(employeeDto.getContactNo());

        employee.setCreatedBy(currentUser.getUserId());
        employee.setCreatedDate(new Date());
        employee.setCmdFlag(CmdFlag.CREATE.getValue());

        employeeRepo.save(employee);

        EmployeeA employeeA = new ModelMapper().map(employee, EmployeeA.class);
        employeeA.setEmployeeAuditId(UuidGenerator.generateUuid());
        employeeRepoA.save(employeeA);


        responseMessage.setStatus(1);
        responseMessage.setText("Added successfully.");

        return responseMessage;
    }

    public ResponseMessage editEmployee(CurrentUser currentUser, EmployeeDto employeeDto) {
        ResponseMessage responseMessage = new ResponseMessage();
        Employee employee = new Employee();
        Employee employeeDb = employeeRepo.findByEmployeeId(employeeDto.getEmployeeId());

        employee.setEmployeeId(employeeDto.getEmployeeId());
        employee.setFullName(employeeDto.getFullName());
        employee.setGender(employeeDto.getGender());
        employee.setContactNo(employeeDto.getContactNo());


        employee.setCreatedBy(employeeDb.getCreatedBy());
        employee.setCreatedDate(employeeDb.getCreatedDate());

        employee.setUpdatedBy(currentUser.getUserId());
        employee.setUpdatedDate(new Date());
        employee.setCmdFlag(CmdFlag.MODIFY.getValue());

        employeeRepo.save(employee);

        EmployeeA employeeA = new ModelMapper().map(employee, EmployeeA.class);
        employeeA.setEmployeeAuditId(UuidGenerator.generateUuid());
        employeeRepoA.save(employeeA);

        responseMessage.setStatus(1);
        responseMessage.setText("Updated successfully.");

        return responseMessage;
    }

    public ResponseMessage getAllEmployees() {
        ResponseMessage responseMessage = new ResponseMessage();
        //TODO:code to get list of employees from db
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
//        List<Employee> employees = employeeRepo.findAll();
        List<EmployeeDto> employeeDtos = employeeDao.getAllEmployees();
        if (employeeDtos.size() > 0) {
            responseMessage.setDTO(employeeDtos);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }

        return responseMessage;

    }

    public ResponseMessage getEmployeeInfo(String employeeId) {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_UNSUCCESSFUL.value());
        Employee employees = employeeRepo.findByEmployeeId(employeeId);
        if (employees != null) {
            responseMessage.setDTO(employees);
            responseMessage.setStatus(SystemDataInt.MESSAGE_STATUS_SUCCESSFUL.value());
        }

        return responseMessage;
    }


    //TODO:method 1: to insert to db

    //TODO:method 2: to retrieve from db
}
