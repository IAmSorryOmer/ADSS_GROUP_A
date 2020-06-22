package DAL;

import Entities.ProductDetails;
import Entities.Report;
import Entities.Reportable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ReportDAL {

    public static HashMap<String, Report> mapper = new HashMap<>();

    public static void insertReport(Report report){
        String sql = "INSERT INTO Report(ReportId, EmployeeID, Description, Type, StoreId) VALUES (?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, report.getReportId());
            preparedStatement.setString(2, report.getEmployeeId());
            preparedStatement.setString(3, report.getDescription());
            preparedStatement.setInt(4, report.getReportType().ordinal());
            preparedStatement.setInt(5, report.getStoreId());
            preparedStatement.executeUpdate();
            mapper.put(report.getReportId(), report);
            for(Reportable reportable : report.getSubjects()){
                String newSql = "insert into ProductsToReports (ProductId, ReportId, OnProductDetails) values (?,?,?);";
                PreparedStatement newPs = DBHandler.getConnection().prepareStatement(newSql);
                newPs.setString(1, reportable.getId());
                newPs.setString(2, report.getReportId());
                newPs.setInt(3, reportable instanceof ProductDetails ? 1 : 0);
                newPs.executeUpdate();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Report getReportById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            String sql = "select * from Report where Report.ReportId = ?";
            try {
                PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, id);
                List<Report> resultList = resultSetToCategory(preparedStatement.executeQuery());
                if(resultList.size() == 0)
                    return null;
                return resultList.get(0);
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }


    public static List<Report> loadStoreAll(int storeId){
        String sql = "select * from Report where StoreId = ?;";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, storeId);
            List<Report> resultList = resultSetToCategory(preparedStatement.executeQuery());
            return resultList;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private static List<Report> resultSetToCategory(ResultSet resultSet) throws SQLException{
        List<Report> toReturn = new LinkedList<>();
        while(resultSet.next()){
            String reportId = resultSet.getString("ReportId");
            int storeId = resultSet.getInt("StoreId");
            String employeeId = resultSet.getString("EmployeeID");
            String description = resultSet.getString("Description");
            int type = resultSet.getInt("Type");
            Report.reportType typeEnum = type == 0 ? Report.reportType.Damaged : type == 1 ? Report.reportType.Damaged : Report.reportType.Missings;
            Report toEdit;
            if(mapper.containsKey(reportId)){
                toEdit = mapper.get(reportId);
                toReturn.add(toEdit);
            }
            else {
                toEdit = new Report(reportId, storeId, employeeId, description, null, typeEnum);
                mapper.put(reportId, toEdit);
                toReturn.add(toEdit);
                addSubjectsToReport(toEdit);
            }
        }
        return toReturn;
    }
    private static void addSubjectsToReport(Report report){
        String sql = "select * from ProductsToReports where ReportId = ?";
        try {
            PreparedStatement preparedStatement = DBHandler.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, report.getReportId());
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Reportable> toSet = new LinkedList<>();
            while (resultSet.next()){
                String subjectId = resultSet.getString("ProductId");
                boolean isProductDetails = resultSet.getInt("OnProductDetails") == 1;
                if(isProductDetails){
                    toSet.add(ProductDetailsDAL.getProductDetailsById(subjectId));
                }
                else{
                    toSet.add(ProductDAL.getProductById(subjectId));
                }
            }
            report.setSubjects(toSet);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
