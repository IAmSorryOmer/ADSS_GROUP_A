package com.company.LogicLayer;

import java.util.ArrayList;
import java.util.List;

public class ReportController {
    private static List<Report> reports = new ArrayList<>();

    public static void addReport(Report report, List<String> subjects) throws Exception {
        reports.add(report);
        for (String subject: subjects){
            List<Discountable> reportSubjects = report.getSubjects();
            Category category = CategoryController.getCategoryByID(subject);
            if (category != null){
                reportSubjects.add(category);
            }
            ProductDetails productDetails = ProductDetailsController.getProductDetailsById(subject);
            if(productDetails != null){
                reportSubjects.add(category);
            }
            if (productDetails == null || category == null){
                throw new Exception("Illegal subject id given to the report");
            }
        }
    }

    public static Report getReportById(String Id){
        for ( Report report: reports){
            if (report.getReportId().equals(Id)){
                return report;
            }
        }
        return null;
    }

    public static void reportAllMissing(Report report){

    }
}
