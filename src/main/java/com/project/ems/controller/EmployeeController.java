package com.project.ems.controller;

import com.project.ems.entity.Employee;
import com.project.ems.exception.EmployeeNotFoundException;
import com.project.ems.export.EmployeeExcelExporter;
import com.project.ems.export.EmployeePdfExporter;
import com.project.ems.service.EmailService;
import com.project.ems.service.EmployeeService;
import com.project.ems.service.FileStorageService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final FileStorageService fileStorageService;
    private final EmailService emailService;

    public EmployeeController(EmployeeService employeeService, FileStorageService fileStorageService, EmailService emailService) {
        this.employeeService = employeeService;
        this.fileStorageService = fileStorageService;
        this.emailService = emailService;
    }

    @GetMapping
    public String viewEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(required = false) String keyword, Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employeePage;

        if (keyword != null && !keyword.trim().isEmpty()) {
            employeePage = employeeService.searchEmployees(keyword, pageable);
        }
        else {
            employeePage = employeeService.getAllEmployees(pageable);
        }

        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);
        return "employee";
    }

    @GetMapping("/new")
    public String showAddEmployeeForm(Model model) {

        model.addAttribute("employee", new Employee());

        return "add-employee";
    }

    @PostMapping
    public String saveEmployee(@Valid @ModelAttribute("employee") Employee employee, BindingResult result, @RequestParam("image") MultipartFile image, Model model) {
        if (result.hasErrors()) {
            return "add-employee";
        }
        if (!image.isEmpty()) {
            String imageName = fileStorageService.uploadFile(image);
            employee.setImageName(imageName);
        }
        else {
            employee.setImageName("default.png");
        }
        Employee savedEmployee = employeeService.saveEmployee(employee);
        emailService.sendWelcomeEmail(savedEmployee.getEmail(),savedEmployee.getFirstName(),savedEmployee.getDepartment());
        return "redirect:/employees";
    }

    @GetMapping("/edit/{id}")
    public String showEditEmployeeForm(@PathVariable Long id,Model model) {
        Employee employee = employeeService.getEmployeeById(id).orElseThrow(() ->
                        new RuntimeException("Employee not found"));
        model.addAttribute("employee", employee);
        return "edit-employee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable Long id, @Valid @ModelAttribute("employee") Employee employee, BindingResult result, @RequestParam("image") MultipartFile image, Model model) {

        if(result.hasErrors()) {
            return "edit-employee";
        }

        Employee existingEmployee = employeeService.getEmployeeById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setJoiningDate(employee.getJoiningDate());
        existingEmployee.setActive(employee.getActive());

        if(!image.isEmpty()) {
            String imageName = fileStorageService.uploadFile(image);
            existingEmployee.setImageName(imageName);
        }
        employeeService.updateEmployee(id, existingEmployee);
        return "redirect:/employees";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employees";
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        response.setHeader("Content-Disposition", "attachment; filename=employees_" + dateTime +".xlsx");

        List<Employee> employeeList = employeeService.getAllEmployees(Pageable.unpaged()).getContent();
        EmployeeExcelExporter exporter = new EmployeeExcelExporter(employeeList);
        exporter.export(response);
    }

    @GetMapping("/export/pdf")
    public void exportToPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        response.setHeader("Content-Disposition", "attachment; filename=employees_"+dateTime +".pdf");

        List<Employee> employeeList = employeeService.getAllEmployees(Pageable.unpaged()).getContent();

        EmployeePdfExporter exporter = new EmployeePdfExporter(employeeList);
        exporter.export(response);
    }

}