package com.spring.project.development.voler.repository;

import com.spring.project.development.voler.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created By zepaG on 5/18/2022.
 */
public interface EmployeeRepo extends JpaRepository<Employee, String> {
    Employee findByEmployeeId(String employeeId);

}
