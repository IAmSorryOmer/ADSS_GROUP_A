package Entities;

import com.sun.org.apache.xerces.internal.impl.xpath.XPath;

import java.util.List;

public class Report {
    public enum reportType{Damaged, Inventory, Missings}
    private String reportId;
    private int storeId;
    private String employeeId;
    private String description;
    private List<? extends Reportable> subjects;
    private reportType reportType;

    public Report(String reportId, int storeId, String employeeId, String description, List<? extends Reportable> subjects, Report.reportType reportType) {
        this.reportId = reportId;
        this.storeId = storeId;
        this.employeeId = employeeId;
        this.description = description;
        this.subjects = subjects;
        this.reportType = reportType;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
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

    public Report.reportType getReportType() {
        return reportType;
    }

    public void setReportType(Report.reportType reportType) {
        this.reportType = reportType;
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
