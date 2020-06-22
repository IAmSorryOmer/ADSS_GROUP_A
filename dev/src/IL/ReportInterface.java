package IL;

import Entities.Report;
import BL.*;

import java.util.List;

public class ReportInterface {
    public static void addInventoryReport(List<String> categoriesIds, Report report){
        ReportController.addInventoryReport(categoriesIds, report);
    }
    public static void addDamagedReport(Report report){
        ReportController.addDamagedReport(report);
    }

    public static void addMissingReport(Report report, boolean autoOrder){
        ReportController.addMissingReport(report, autoOrder);
    }

    public static Report getReportById(String Id){
        return ReportController.getReportById(Id);
    }

    public static String stringifyStoreReports(int storeId){
        StringBuilder stringBuilder = new StringBuilder("Reports:\n");

        for(Report report : ReportController.getAllStoreReports(storeId)){
            stringBuilder.append("-").append(report.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
