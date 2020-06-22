package BL;
import DAL.ReportDAL;
import Entities.*;

import java.util.List;

public class ReportController {

    //TODO: include workers in signatures

    public static void addInventoryReport(List<String> categoriesIds, Report report) throws Exception{
        List<ProductDetails> products = ProductDetailsController.getAllProductsOffCategory(categoriesIds);
        report.setReportType(Report.reportType.Inventory);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }
    public static void addDamagedReport(Report report){
        List<Product> products = ProductController.getAllDamaged();
        report.setReportType(Report.reportType.Damaged);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }

    public static void addMissingReport(Report report, boolean autoOrder){
        List<ProductDetails> products = ProductDetailsController.getAllMissing();
        if(autoOrder) {
            SingleProviderOrderController.autoOrderListOfProducts(products);
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
