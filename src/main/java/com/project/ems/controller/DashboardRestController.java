package com.project.ems.controller;

import com.project.ems.dto.DepartmentStatsDTO;
import com.project.ems.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final EmployeeService employeeService;

    public DashboardRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/department-stats")
    public List<DepartmentStatsDTO> departmentStats() {
        return employeeService.getDepartmentStatistics();
    }

}