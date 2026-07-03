package com.project.ems.export;

import com.project.ems.entity.Employee;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class EmployeeExcelExporter {

    private final List<Employee> employeeList;

    public EmployeeExcelExporter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void export(HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Employees");
        Row header = sheet.createRow(0);
        String[] columns = {
                "ID",
                "First Name",
                "Last Name",
                "Email",
                "Phone",
                "Department",
                "Salary",
                "Joining Date",
                "Active"
        };

        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);

        for (int i = 0; i < columns.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(style);
        }

        int rowCount = 1;

        for (Employee employee : employeeList) {
            Row row = sheet.createRow(rowCount++);
            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getFirstName() != null ? employee.getFirstName().toString() : "");
            row.createCell(2).setCellValue(employee.getLastName() != null ? employee.getLastName().toString() : "");
            row.createCell(3).setCellValue(employee.getEmail() != null ? employee.getEmail().toString() : "");
            row.createCell(4).setCellValue(employee.getPhone() != null ? employee.getPhone().toString() : "");
            row.createCell(5).setCellValue(employee.getDepartment() != null ? employee.getDepartment().toString() : "");
            row.createCell(6).setCellValue(employee.getSalary() != null ? employee.getSalary().toString() : "");
            row.createCell(7).setCellValue(employee.getJoiningDate() != null ? employee.getJoiningDate().toString() : "");
            row.createCell(8).setCellValue(employee.getActive() != null ? employee.getActive().toString() : "");
        }
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}