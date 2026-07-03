package com.project.ems.dto;

import com.project.ems.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee e) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPhone(e.getPhone());
        dto.setDepartment(e.getDepartment());
        dto.setSalary(e.getSalary());
        dto.setJoiningDate(e.getJoiningDate());
        dto.setActive(e.getActive());
        return dto;
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee e = new Employee();
        e.setId(dto.getId());
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setPhone(dto.getPhone());
        e.setDepartment(dto.getDepartment());
        e.setSalary(dto.getSalary());
        e.setJoiningDate(dto.getJoiningDate());
        e.setActive(dto.getActive());
        return e;
    }
}