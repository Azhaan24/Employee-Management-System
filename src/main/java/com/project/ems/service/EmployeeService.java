package com.project.ems.service;

import com.project.ems.dto.DepartmentStatsDTO;
import com.project.ems.entity.Employee;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    Employee saveEmployee(Employee employee);

    Page<Employee> getAllEmployees(Pageable pageable);

    Optional<Employee> getEmployeeById(Long id);

    Employee updateEmployee(Long id, Employee employee);

    void deleteEmployee(Long id);

    Page<Employee> searchEmployees(String keyword, Pageable pageable);

    long getTotalEmployees();

    long getDepartmentCount();

    Double getAverageSalary();

    long getNewEmployeesThisMonth();

    List<Employee> getRecentEmployees();

    List<DepartmentStatsDTO> getDepartmentStatistics();
}