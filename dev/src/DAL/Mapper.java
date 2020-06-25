package DAL;

import org.omg.PortableServer.IdAssignmentPolicy;
import org.sqlite.SQLiteConfig;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
//package net.sqlitetutorial;

import java.sql.*;






public class Mapper {

    public static   Connection conn;

    public void connect() {
        try{
            String url = "jdbc:sqlite:SuperLiUnified";
            SQLiteConfig sqLiteConfig = new SQLiteConfig();
            sqLiteConfig.enforceForeignKeys(true);
            conn = DriverManager.getConnection(url, sqLiteConfig.toProperties());
            System.out.println("opened database successfully");
            DBHandler.connection = conn;
        }
        catch (SQLException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }


    public Mapper() {
        connect();
    }


    public LocalDate getCurrentDate(){
        try {
            String query = "SELECT date  FROM CurrentDate ";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            String d=rs.getString("date");
            LocalDate date = LocalDate.parse(d);
            return date;
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return null;
    }

    public List<DStore> LoadStores() {
        List<DStore> dStores = new LinkedList<>();
        try {
            String query = "SELECT store_num FROM Stores";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // loop through the result set
            while (rs.next()) {
                dStores.add(new DStore(rs.getInt("store_num")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dStores;
    }

    public List<DEmployee_Details> loadEmployeesDetails() {
        List<DEmployee_Details> DDetails = new LinkedList<>();
        try {
            String query = "SELECT id,name,store_num  FROM Employees ";
            PreparedStatement stmt = conn.prepareStatement(query);

            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                DDetails.add(new DEmployee_Details(rs.getInt("id"), rs.getInt("store_num"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return DDetails;
    }
    public void removeEmployee(int id) {
        try {
            String query = "DELETE FROM Employees WHERE id="+id;
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void removeNoDeliveryOrders(String lastDay){
        try {
            String query = "DELETE FROM SingleProviderOrder WHERE OrderDate = ? and DeliveryDate is null";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, lastDay);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public void updateShiftManagerID(int id){

        try {
            String query = "UPDATE Assigments_To_Shifts SET id=0  WHERE id=? AND role='shift manager'";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    public List<String> loadRolesByID(int id) {
        List<String> d = new LinkedList<>();
        try {
            String query = "SELECT role  FROM Role_ID WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                d.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return d;
    }


    public  List<DEmployee> LoadEmployees(int store_num) {
        List<DEmployee> dEmployees = new LinkedList<>();
        try {
            String query = "SELECT name, id, bankAccount, salary, store_num, employee_condition, start_date,hasAssignedShifts FROM Employees " +
                    "WHERE store_num = " + store_num;

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // loop through the result set
            while (rs.next()) {

                DEmployee dEmployee = new DEmployee(rs.getString("name"), rs.getInt("id"), rs.getString("bankAccount"), rs.getInt("salary"), rs.getInt("store_num"), rs.getString("employee_condition"), rs.getString("start_date"), rs.getBoolean("hasAssignedShifts"));
                List<String> capableJobs = new LinkedList<>();
                try {
                    String queryJobs = "SELECT role FROM Role_ID WHERE id =" + rs.getInt("id");
                    Statement stmtJobs = conn.createStatement();
                    ResultSet rsJobs = stmtJobs.executeQuery(queryJobs);
                    while (rsJobs.next())
                        capableJobs.add(rsJobs.getString("role"));
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                String[] capableShifts = new String[7];
                for (int i = 0; i < 7; i++) {
                    capableShifts[i] = "";
                }
                try {
                    String queryShifts = "SELECT day, day_part FROM Capable_Shifts WHERE id =" + rs.getInt("id");
                    Statement stmtShifts = conn.createStatement();
                    ResultSet rsShifts = stmtShifts.executeQuery(queryShifts);
                    while (rsShifts.next()) {
                        capableShifts[rsShifts.getInt("day")] += rsShifts.getString("day_part");
                    }
                }
                catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
                dEmployee.setCapable_jobs(listToArray(capableJobs));

                dEmployee.setCapable_shifts(capableShifts);

                dEmployees.add(dEmployee);

            }
            return dEmployees;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dEmployees;
    }
    public static String[] listToArray(List<String> t){
        String[] ret=new String[t.size()];
        for(int i=0;i<t.size();++i){
            ret[i]=t.get(i);
        }
        return ret;
    }
    public DDay[] loadDays(int store_num) {

        DDay[] dDays = new DDay[7];
        try {
            String query = "SELECT date, day_num, is_history FROM Days WHERE store_num = " + store_num;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // loop through the result set
            while (rs.next()) {
                if (rs.getInt("is_history") == 0)
                    dDays[rs.getInt("day_num")] = new DDay(rs.getString("date"));
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dDays;

    }

    public  List<DRole_ID> loadRole_ID(int day_num, int store_num, int day_part) {

        List<DRole_ID> dRoles = new LinkedList<>();
        try {
            String query = "SELECT role, id FROM Assigments_To_Shifts WHERE day_num =" + day_num + " AND store_num=" + store_num + " AND day_part=" + day_part + " AND is_history=0";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            boolean isRoleExist = false;
            int roleIndex = 0;
            // loop through the result set
            while (rs.next()) {
                for (int i = 0; i < dRoles.size(); i++) {
                    if (dRoles.get(i).getRole().equals(rs.getString("role"))) {
                        isRoleExist = true;
                        roleIndex = i;
                    }
                }
                if (isRoleExist) {
                    dRoles.get(roleIndex).addId(rs.getInt("id"));
                }
                else {
                    List<Integer> idList = new LinkedList<>();
                    idList.add(rs.getInt("id"));
                    DRole_ID dRole = new DRole_ID(rs.getString("role"), idList);
                    dRoles.add(dRole);
                }
                isRoleExist = false;
                roleIndex = 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dRoles;

    }


    public List<DDelivery> loadDeliveries(int store_num) {
        List<DDelivery> dDelivery = new LinkedList<>();
        try {
            String query = "SELECT id,date,dispatchTime,TID,DName,source,preWeight, returnHour,Address, OrderId  FROM Deliveries WHERE store_num=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, store_num);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                dDelivery.add(new DDelivery(rs.getInt("id"), rs.getString("date"), rs.getString("dispatchTime"),
                        rs.getString("TID"), rs.getString("DName"), rs.getString("source"), rs.getInt("preWeight"),rs.getString("Address"),rs.getString("OrderId")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dDelivery;
    }
    public void setAssignedCapableShifts(int id)
    {
        String sql = "UPDATE Employees SET hasAssignedShifts = 1 WHERE id=?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            //     stmt.executeUpdate(sql);
            stmt.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }




    public List<DNumberedFile> loadNumberedFiles(int deliveryId) {
        List<DNumberedFile> dNumberedFiles = new LinkedList<>();
        try {
            String query = "SELECT number,address  FROM NumberedFiles WHERE deliveryId=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, deliveryId);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                dNumberedFiles.add(new DNumberedFile(rs.getString("number"), rs.getString("address")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dNumberedFiles;
    }


    public  void isSpecialRole(int id,String role) {
        String query = "SELECT employeeId FROM SpecialRole WHERE employeeId=? AND role=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.setString(2,role);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new Exception("You are not " + role);
            }
        }
        catch (Exception e){
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<DDestination> loadDestinations() {
        List<DDestination> dDestinations = new LinkedList<>();
        try {
            String query = "SELECT address,contact,phone,area  FROM Destinations";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                dDestinations.add(new DDestination(rs.getString("address"), rs.getString("contact"),
                        rs.getString("phone"), rs.getString("area")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dDestinations;
    }


    public List<DTruck> loadTrucks(int store_num) {
        List<DTruck> dTrucks = new LinkedList<>();
        try {
            String query = "SELECT id,weight,maxWeight,model  FROM Trucks WHERE store_num=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, store_num);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                dTrucks.add(new DTruck(rs.getString("id"), rs.getInt("weight"), rs.getInt("maxWeight"), rs.getString("model")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dTrucks;
    }

    public List<DDriver> loadDrivers(int store_num) {
        List<DDriver> dDrivers = new LinkedList<>();
        try {
            String query = "SELECT license,name,id FROM Drivers WHERE store_num=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, store_num);
            ResultSet rs = stmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                dDrivers.add(new DDriver(rs.getString("license"), rs.getString("name"),rs.getInt("id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dDrivers;
    }

    public void saveDriver(String license, String name, int store_num, int id) {
        try {
            String query = "INSERT INTO Drivers(license, name, store_num, id) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, license);
            stmt.setString(2, name);
            stmt.setInt(3, store_num);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void saveRoleInShift(String role,String date,String dayPart,int store_num,int neededNum)
    {
        try {
            String query = "INSERT INTO Role_Shift(role, date, day_part, store_num, needed) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, role);
            stmt.setString(2, date);
            stmt.setString(3, dayPart);
            stmt.setInt(4, store_num);
            stmt.setInt(5, neededNum);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public List<DRole_Needed> loadRole_NumNeeded(String date, int store_num, String day_part)
    {
        List<DRole_Needed> dRoles_need = new LinkedList<>();
        try {
            //TODO this query has errors
            String query = "SELECT role, needed FROM Role_Shift WHERE date =" + date + " AND store_num=" + store_num + " AND day_part=\"" + day_part + "\"";
            //String query = "SELECT role, needed FROM Role_Shift WHERE date =" + date + " AND store_num=" + store_num + " AND day_part=" + day_part;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                DRole_Needed dRole_needed = new DRole_Needed(rs.getString("role"),rs.getInt("needed"));
                dRoles_need.add(dRole_needed);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dRoles_need;
    }

    public void saveTruck(String id, int weight, int maxWeight, String model, int store_num) {
        try {
            String query = "INSERT INTO Trucks(id, model, maxWeight, weight, store_num) VALUES(?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, id);
            stmt.setString(2, model);
            stmt.setInt(3, maxWeight);
            stmt.setInt(4, weight);
            stmt.setInt(5, store_num);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDelivery( int id, String date, String dispatchTime, String TID, String DName, String source, int preWeight, String address,String orderId) {
        try {
            String query = "INSERT INTO Deliveries(date, dispatchTime, DName, source, TID, id, preWeight, store_num, Address,OrderId) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, date);
            stmt.setString(2, dispatchTime);
            stmt.setString(3, DName);
            stmt.setString(4, source);
            stmt.setString(5, TID);
            stmt.setInt(6, id);
            stmt.setInt(7, preWeight);
            stmt.setInt(8, Integer.parseInt(source));
            stmt.setString(9,address);
            stmt.setString(10,orderId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDestination(String address, String contact, String phone, String area) {
        try {
            String query = "INSERT INTO Destinations(address, area, contact, phone) VALUES(?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, address);
            stmt.setString(2, area);
            stmt.setString(3, contact);
            stmt.setString(4, phone);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveNumberedFile(String number, String address, int deliveryId) {
        try {
            String query = "INSERT INTO NumberedFiles(number, address, deliveryId) VALUES(?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, number);
            stmt.setString(2, address);
            stmt.setInt(3, deliveryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void saveEmployee(String[] jobs, String name, int id, String bankAccount, int store_num, int salary, String employee_conditions, String start_date, int has_assigned_shifts){


        // create a connection to the database

        String sql = "INSERT INTO Employees (name,id,bankAccount,salary,store_num,employee_condition,start_date,hasAssignedShifts) Values(\""+name+"\","+id+",\""+bankAccount+"\","+salary+","+store_num+",\""+employee_conditions+"\",\""+start_date+"\","+has_assigned_shifts+")";


        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            for(int i=0;i<jobs.length;++i){
                sql="INSERT INTO Role_ID (role,id) VALUES(\""+jobs[i]+"\","+id+")";
                stmt.executeUpdate(sql);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
        }


    }
    public void saveCapableShifts(int day,String day_part,int id){


        // create a connection to the database

        String sql = "INSERT INTO Capable_Shifts (day,day_part,id) Values("+day+",\""+day_part+"\","+id+")";


        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
        }


    }
    public void saveShiftManger(String date, String role,int day_part,int day_num,int id, int is_history,int store_num)
    {
        String sql = "UPDATE Assigments_To_Shifts SET id = ? WHERE date=? AND role=? AND day_part=? AND is_history=? AND store_num=?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.setString(2, date);
            stmt.setString(3, role);
            stmt.setInt(4, day_part);
            stmt.setInt(5, is_history);
            stmt.setInt(6, store_num);
            //     stmt.executeUpdate(sql);
            stmt.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void saveAssignments_To_Shifts(String date, String role,int day_part,int day_num,int id, int is_history,int store_num){


        // create a connection to the database

        String sql = "INSERT INTO Assigments_To_Shifts (role,day_num,day_part,id,is_history,store_num,date) Values(\""+role+"\","+day_num+","+day_part+","+id+","+is_history+","+store_num+",\""+date+"\")";


        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(date);
            System.out.println(role);
            System.out.println(day_part);
            System.out.println(day_num);
            System.out.println(id);
            System.out.println(is_history);
            System.out.println(store_num);
        }


        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
        }


    }
    public void saveDay(String date,int store_num,int day_num, int is_history){

        // create a connection to the database
        String sql = "INSERT INTO Days (date,store_num,day_num,is_history) Values(\""+date+"\","+store_num+","+day_num+","+is_history+")";


        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
        }
    }

    public void saveStore(int store_num) {

        // create a connection to the database

        String sql = "INSERT INTO Stores (store_num) Values(" + store_num + ")";

        try {

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);


        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
//            try {
//                if (conn != null) {
//                    conn.close();
//                }
//            } catch (SQLException ex) {
//                System.out.println(ex.getMessage());
//            }
        }
    }



    public void updateEmployee(String name, int id, String bankAccount, int salary, String employee_conditions) {

        String sql = "UPDATE Employees SET name=?,bankAccount=?,salary=?,employee_condition=? WHERE id=?";


        try {

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setString(2, bankAccount);
            stmt.setString(4,employee_conditions);
            stmt.setInt(3, salary);
            stmt.setInt(5, id);
            //     stmt.executeUpdate(sql);
            stmt.execute();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getSundayDate() {
        try {
            String query = "SELECT date FROM Days WHERE day_num=0 AND is_history=0 ";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // loop through the result set
            while (rs.next()) {

                return rs.getString("date");
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return "1.5";
    }


    public void setAssignments_History(String date){
        try{
            String query="UPDATE Assigments_To_Shifts SET is_history=1 WHERE date=?";
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,date);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void setDay_History(String date){
        try{
            String query="UPDATE Days SET is_history=1 WHERE date=?";
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,date);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void saveCurrent_Date(String current_date) {
        try{
            String query="UPDATE CurrentDate SET date=?";
            PreparedStatement stmt=conn.prepareStatement(query);
            stmt.setString(1,current_date);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}