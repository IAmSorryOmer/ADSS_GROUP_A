package BL;
import DAL.ReportDAL;
import Entities.*;

import java.util.List;

public class ReportController {


    public static void addInventoryReport(List<String> categoriesIds, Report report){
        List<ProductDetails> products = ProductDetailsController.getAllProductsOffCategory(categoriesIds);
        report.setReportType(Report.reportType.Inventory);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }
    public static void addDamagedReport(Report report){
        List<Product> products = ProductController.getAllDamagedInStore(report.getStoreId());
        report.setReportType(Report.reportType.Damaged);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }

    public static void addMissingReport(Report report, boolean autoOrder){
        List<ProductDetails> products = ProductDetailsController.getAllStoreMissing(report.getStoreId());
        if(autoOrder) {
            SingleProviderOrderController.autoOrderListOfProductsInStore(report.getStoreId(), products);
        }
        report.setReportType(Report.reportType.Missings);
        report.setSubjects(products);
        ReportDAL.insertReport(report);
    }

    public static Report getReportById(String Id){
        return ReportDAL.getReportById(Id);
    }
    public static List<Report> getAllStoreReports(int storeId){
        return ReportDAL.loadStoreAll(storeId);
    }
}
