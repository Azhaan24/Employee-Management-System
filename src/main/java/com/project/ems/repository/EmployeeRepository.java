package com.project.ems.repository;

import com.project.ems.entity.Employee;
import com.project.ems.dto.DepartmentStatsDTO;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    Page<Employee> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String firstName,
            String lastName,
            String email,
            String department,
            Pageable pageable
    );

    @Query("SELECT AVG(e.salary) FROM Employee e")
    Double getAverageSalary();

    @Query("SELECT COUNT(DISTINCT e.department) FROM Employee e")
    long countDepartments();

    @Query("""
        SELECT COUNT(e)
        FROM Employee e
        WHERE MONTH(e.joiningDate)=MONTH(CURRENT_DATE)
        AND YEAR(e.joiningDate)=YEAR(CURRENT_DATE)
    """)
    long countEmployeesJoinedThisMonth();

    List<Employee> findTop5ByOrderByJoiningDateDesc();

    @Query("""
        SELECT new com.project.ems.dto.DepartmentStatsDTO(e.department, COUNT(e))
        FROM Employee e
        GROUP BY e.department
    """)
    List<DepartmentStatsDTO> getDepartmentStatistics();
}