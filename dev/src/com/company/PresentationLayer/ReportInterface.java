package com.company.PresentationLayer;

import com.company.LogicLayer.*;

import java.util.List;

public class ReportInterface {
    public static void addInventoryReport(List<String> categoriesIds, Report report) throws Exception{
        ReportController.addInventoryReport(categoriesIds, report);
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

    public static String stringifyReports(){
        StringBuilder stringBuilder = new StringBuilder("Reports:\n");

        for(Report report : ReportController.getAllReports()){
            stringBuilder.append("-").append(report.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
