package com.project.ems.export;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.*;
import com.project.ems.entity.Employee;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class EmployeePdfExporter {

    private final List<Employee> employeeList;

    public EmployeePdfExporter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void export(HttpServletResponse response) throws IOException {

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20,Color.BLUE);
        Paragraph title = new Paragraph("Employee Management Report", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(9);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{
                1.2f,
                2f,
                2f,
                3f,
                2f,
                2f,
                2f,
                2f,
                1.5f
        });

        addHeader(table);
        addRows(table);
        document.add(table);
        document.close();
    }

    private void addHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.DARK_GRAY);
        cell.setPadding(6);
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.WHITE);
        String[] headers = {
                "ID",
                "First Name",
                "Last Name",
                "Email",
                "Phone",
                "Department",
                "Salary",
                "Joining Date",
                "Status"
        };
        for(String header : headers) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    private void addRows(PdfPTable table) {
        for (Employee employee : employeeList) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getEmail() != null ? employee.getEmail() : "-");
            table.addCell(employee.getPhone() != null ? employee.getPhone().toString() : "-");
            table.addCell(employee.getFirstName() != null ? employee.getFirstName() : "-");
            table.addCell(employee.getLastName() != null ? employee.getLastName() : "-");
            table.addCell(employee.getSalary() != null ? employee.getSalary().toString() : "-");
            table.addCell(employee.getDepartment() != null ? employee.getDepartment() : "-");
            table.addCell(employee.getJoiningDate() != null ? employee.getJoiningDate().toString() : "-");
            table.addCell(employee.getActive() ? "Active" : "Inactive");
        }
    }
}