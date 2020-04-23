package com.company.LogicLayer;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private static List<Report> reports = new ArrayList<>();


    public static void addInventoryReport(List<String> categoriesIds, Report report) throws Exception{
        List<ProductDetails> products = ProductDetailsController.getAllProductsOffCategory(categoriesIds);
        report.setReportType(Report.reportType.Inventory);
        report.setSubjects(products);
        reports.add(report);
    }
    public static void addDamagedReport(Report report){
        List<Product> products = ProductController.getAllDamaged();
        report.setReportType(Report.reportType.Damaged);
        report.setSubjects(products);
        reports.add(report);
    }

    public static void addMissingReport(Report report){
        List<ProductDetails> products = ProductDetailsController.getAllMissing();
        report.setReportType(Report.reportType.Missings);
        report.setSubjects(products);
        reports.add(report);
    }

    public static Report getReportById(String Id){
        for ( Report report: reports){
            if (report.getReportId().equals(Id)){
                return report;
            }
        }
        return null;
    }
    public static List<Report> getAllReports(){
        return reports;
    }
}
