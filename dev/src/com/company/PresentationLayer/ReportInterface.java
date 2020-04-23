package com.company.PresentationLayer;

import com.company.LogicLayer.*;

import java.util.List;

public class ReportInterface {
    public static void addInventoryReport(List<Category> categories, Report report){
        ReportController.addInventoryReport(categories, report);
    }
    public static void addDamagedReport(Report report){
        ReportController.addDamagedReport(report);
    }

    public static void addMissingReport(Report report){
        ReportController.addMissingReport(report);
    }

    public static Report getReportById(String Id){
        return ReportController.getReportById(Id);
    }

}
