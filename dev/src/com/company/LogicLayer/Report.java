package com.company.LogicLayer;
import java.util.ArrayList;
import java.util.List;

public class Report {
    public enum reportType{Damaged, Inventory, Missings}
    private reportType reportType;
    private String reportId;
    private String employeeId;
    private String description;
    private List<? extends Reportable> subjects;

    public Report(Report.reportType reportType, String reportId, String employeeId, String description, List<? extends Reportable> subjects) {
        this.reportType = reportType;
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.description = description;
        this.subjects = subjects;
    }

    public Report.reportType getReportType() {
        return reportType;
    }

    public void setReportType(Report.reportType reportType) {
        this.reportType = reportType;
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

    public List<? extends Reportable> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<? extends Reportable> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Report number: " + reportId + "\nby employee number: " + employeeId + "\n of type: " + reportType.name() + "\n" + "Products:");
        for(Reportable reportable: subjects){
            stringBuilder.append("\n");
            stringBuilder.append(reportable.toString());
        }
        return stringBuilder.toString();
    }
}
