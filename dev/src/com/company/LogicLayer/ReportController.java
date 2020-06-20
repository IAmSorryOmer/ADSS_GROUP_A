package com.company.LogicLayer;
import com.company.DataAccessLayer.ReportDAL;
import com.company.Entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReportController {

    //TODO: include workers in signatures

    public static void addInventoryReport(List<String> categoriesIds, Report report, int storeNum) throws Exception{
        List<ProductDetails> products = ProductDetailsController.getAllProductsOffCategory(categoriesIds);
        report.setReportType(Report.reportType.Inventory);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }
    public static void addDamagedReport(Report report, int storeNum){
        List<Product> products = ProductController.getAllDamaged(storeNum);
        report.setReportType(Report.reportType.Damaged);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }

    public static void addMissingReport(Report report, boolean autoOrder, int storeNum){
        List<ProductDetails> products = ProductDetailsController.getAllMissing();
        if(autoOrder) {
            SingleProviderOrderController.autoOrderListOfProducts(products, storeNum);
        }
        report.setReportType(Report.reportType.Missings);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }

    public static Report getReportById(String Id){
        return ReportDAL.getReportById(Id);
    }
    public static List<Report> getAllReports(){
        return ReportDAL.loadAll();
    }
    public static List<Report> getAllReportsInStore(){
        return ReportDAL.loadAllInStore();
    }
}
