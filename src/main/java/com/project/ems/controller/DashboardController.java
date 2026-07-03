package com.project.ems.controller;

import com.project.ems.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final EmployeeService employeeService;

    public DashboardController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {

        model.addAttribute("totalEmployees", employeeService.getTotalEmployees());

        model.addAttribute("departmentCount", employeeService.getDepartmentCount());

        model.addAttribute("averageSalary", employeeService.getAverageSalary());

        model.addAttribute("newEmployees", employeeService.getNewEmployeesThisMonth());

        model.addAttribute("recentEmployees", employeeService.getRecentEmployees());

        return "dashboard";
    }

}