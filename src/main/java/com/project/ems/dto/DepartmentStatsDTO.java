package com.project.ems.dto;

public class DepartmentStatsDTO {

    private String department;
    private Long totalEmployees;

    public DepartmentStatsDTO(String department, Long totalEmployees) {
        this.department = department;
        this.totalEmployees = totalEmployees;
    }

    public String getDepartment() {
        return department;
    }

    public Long getTotalEmployees() {
        return totalEmployees;
    }
}