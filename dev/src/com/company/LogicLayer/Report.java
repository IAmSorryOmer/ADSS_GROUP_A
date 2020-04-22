package com.company.LogicLayer;
import java.util.ArrayList;
import java.util.List;

public class Report {
    private String reportId;
    private String employeeId;
    private String description;
    private List<Discountable> subjects;

    public Report(String reportId, String employeeId, String description) {
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.description = description;
        subjects = new ArrayList<>();
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Discountable> getSubjects() {
        return subjects;
    }
}
