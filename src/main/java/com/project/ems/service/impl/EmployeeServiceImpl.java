package com.project.ems.service.impl;

import com.project.ems.dto.DepartmentStatsDTO;
import com.project.ems.entity.Employee;
import com.project.ems.repository.EmployeeRepository;
import com.project.ems.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        if (employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Page<Employee> getAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee existingEmployee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));

        employeeRepository.findByEmail(employee.getEmail()).ifPresent(e -> {
                    if (!e.getId().equals(id)) {
                        throw new RuntimeException("Email already exists.");
                    }
                });

        existingEmployee.setFirstName(employee.getFirstName());
        existingEmployee.setLastName(employee.getLastName());
        existingEmployee.setEmail(employee.getEmail());
        existingEmployee.setPhone(employee.getPhone());
        existingEmployee.setDepartment(employee.getDepartment());
        existingEmployee.setSalary(employee.getSalary());
        existingEmployee.setJoiningDate(employee.getJoiningDate());
        existingEmployee.setActive(employee.getActive());
        existingEmployee.setImageName(employee.getImageName());

        return employeeRepository.save(existingEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public Page<Employee> searchEmployees(String keyword, Pageable pageable) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return employeeRepository.findAll(pageable);
        }

        return employeeRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(keyword, keyword, keyword, keyword, pageable);
    }

    @Override
    public long getTotalEmployees() {
        return employeeRepository.count();
    }

    @Override
    public long getDepartmentCount() {
        return employeeRepository.countDepartments();
    }

    @Override
    public Double getAverageSalary() {
        return employeeRepository.getAverageSalary();
    }

    @Override
    public long getNewEmployeesThisMonth() {
        return employeeRepository.countEmployeesJoinedThisMonth();
    }

    @Override
    public List<Employee> getRecentEmployees() {
        return employeeRepository.findTop5ByOrderByJoiningDateDesc();
    }

    @Override
    public List<DepartmentStatsDTO> getDepartmentStatistics() {
        return employeeRepository.getDepartmentStatistics();
    }
}