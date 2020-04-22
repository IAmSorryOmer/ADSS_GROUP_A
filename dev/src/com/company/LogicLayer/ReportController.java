package com.company.LogicLayer;
import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private static List<Report> reports = new ArrayList<>();


    public static void addInventoryReport(List<Category> categories, Report report){
        List<ProductDetails> products = ProductDetailsController.getAllProductsOffCategory(categories);
        report.setReportType(Report.reportType.Inventory);
        report.setSubjects(products);
        reports.add(report);
    }
    public static void addDamagedReport(Report report){
        List<Product> products = ProductController.returnAllDamaged();
        report.setReportType(Report.reportType.Damaged);
        report.setSubjects(products);
        reports.add(report);
    }

    public static void addMissingReport(Report report){
        List<ProductDetails> products = ProductDetailsController.returnAllMissing();
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
}
