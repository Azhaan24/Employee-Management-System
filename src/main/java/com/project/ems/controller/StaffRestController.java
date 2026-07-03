package com.project.ems.controller;

import com.project.ems.dto.EmployeeDTO;
import com.project.ems.dto.EmployeeMapper;
import com.project.ems.entity.Employee;
import com.project.ems.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffRestController {

    private final EmployeeService employeeService;

    public StaffRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Operation(summary = "Get all employees")
    @GetMapping
    public ResponseEntity<Page<EmployeeDTO>> getAllEmployees(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EmployeeDTO> employeePage = employeeService.getAllEmployees(pageable).map(EmployeeMapper::toDTO);
        return ResponseEntity.ok(employeePage);
    }

    @Operation(summary = "Get employee by ID")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable Long id) {
        return employeeService.getEmployeeById(id).map(EmployeeMapper::toDTO).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create employee")
    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@Valid @RequestBody EmployeeDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        Employee savedEmployee = employeeService.saveEmployee(employee);

        return ResponseEntity.status(HttpStatus.CREATED).body(EmployeeMapper.toDTO(savedEmployee));
    }

    @Operation(summary = "Update employee")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @Valid @RequestBody EmployeeDTO dto) {
        Employee employee = EmployeeMapper.toEntity(dto);
        Employee updatedEmployee = employeeService.updateEmployee(id, employee);

        return ResponseEntity.ok(EmployeeMapper.toDTO(updatedEmployee));
    }

    @Operation(summary = "Delete employee")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully.");
    }

}