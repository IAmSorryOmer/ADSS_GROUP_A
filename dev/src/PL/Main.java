package PL;

import BL.StoreController;
import DAL.DBHandler;
import Entities.*;
import IL.*;
import javafx.scene.control.Alert;

import java.lang.annotation.RetentionPolicy;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static PL.WatchMenues.*;

public class Main {
    public static Scanner reader = new Scanner(System.in);
    public static ManagerController managerController = ManagerController.getInstance();
    public static EmployeeController employeeController = EmployeeController.getInstance();
    
    public static void main(String[] args) {
        try {
            DBHandler.connect();
            initialSelection();
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }


    private static void initialSelection() {
        if (!managerController.loadStores())//Initializing the day
        {
            selectDay();
        }

        //CHOOSE ROLE IN THE SYSTEM
        String[] options = new String[]{"General actions", "Store specific actions", "pass day", "exit"};
        while (true) {
            System.out.println("Hello, Welcome To Super-Li System");
            System.out.println("Choose which actions you want to preform:");
            printOptions(options);
            try {
                int choice = Integer.parseInt(reader.nextLine());
                switch(choice){
                    case 1:
                        generalManagerMenu();
                        break;
                    case 2:
                        storeSpecificMenu();
                        break;
                    case 3:
                        ManagerController.passDay();
                        System.out.println("you passed a day successfully, current day is: " + StoreController.current_date.toString());
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Please choose a number between 1 and 4");
                        break;
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void generalManagerMenu(){
        validateRole("please insert your id(only logistic manager can access this menu):", "Logistic manager");
        String[] options = new String[]{"products details", "categories", "discounts", "providers", "agreements", "return to main menu"};
        while (true) {
            System.out.println("chooses category to manage:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        manageProductTypesMenu();
                        break;
                    case 2:
                        manageCategoriesMenu();
                        break;
                    case 3:
                        manageDiscountsMenu();
                        break;
                    case 4:
                        manageProvidersMenu();
                        break;
                    case 5:
                        manageAgreementsMenu();
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 6");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void storeSpecificMenu(){
        int storeId = selectStore();
        String[] options = new String[]{"Store manager", "Stock keeper", "Logistic manager", "Human resource manager", "Regular employee", "select another store", "return to main menu"};
        while (true) {
            System.out.println("please chooses your role:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        storeManagerMenu(storeId);
                        break;
                    case 2:
                        stockKeeperMenu(storeId);
                        break;
                    case 3:
                        logisticManagerMenu(storeId);
                        break;
                    case 4:
                        humanResourceManagerMenu(storeId);
                        break;
                    case 5:
                        employeeMenu(storeId);
                        break;
                    case 6:
                        storeId = selectStore();
                        System.out.println("switched store successfully");
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 7");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void storeManagerMenu(int storeId){
        validateRole("please insert your id:", "Store manager");
        String[] options = new String[]{"Trucks", "Deliveries", "Employees", "Shifts", "Products", "Orders", "Reports", "return to specific store actions menu"};
        while (true) {
            System.out.println("please chooses the category that you want to examine:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        watchTrucks(storeId);
                        break;
                    case 2:
                        watchDeliveries(storeId);
                        break;
                    case 3:
                        watchEmployees(storeId);
                        break;
                    case 4:
                        watchShifts(storeId);
                        break;
                    case 5:
                        watchProducts(storeId);
                        break;
                    case 6:
                        watchOrders(storeId);
                        break;
                    case 7:
                        watchReports(storeId);
                        break;
                    case 8:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 8");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void stockKeeperMenu(int storeId){
        System.out.println("please insert your employee id:");
        int employeeId = Integer.parseInt(reader.nextLine());
        boolean answer = employeeController.connect(employeeId, storeId);
        if(!answer || !employeeController.isStorage()){
            throw new IllegalArgumentException("there is no storage employee with id " + employeeId);
        }
        String[] options = new String[]{"products", "orders", "reports(including auto order missing products)", "show alerts(orders waiting to be accepted to stock)", "accept waiting orders", "return to specific store actions menu"};
        while (true) {
            System.out.println("please chooses the category that you want to manage:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        manageProductsMenu(storeId);
                        break;
                    case 2:
                        manageOrdersMenu(storeId);
                        break;
                    case 3:
                        manageReportsMenu(storeId);
                        break;
                    case 4:
                        Alerts.stockKeeperAlert(storeId);
                        break;
                    case 5:
                        Alerts.acceptSupply(storeId);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 6");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }

    }

    private static void logisticManagerMenu(int storeId){
        validateRole("please insert your id:", "Logistic manager");
        String[] options = new String[]{"manage deliveries", "show alerts(orders which should be delivered today)", "accept deliveries", "return to specific store actions menu"};
        while (true) {
            System.out.println("please chooses action to preform:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        manageDeliveries(storeId);
                        break;
                    case 2:
                        Alerts.logisticManagerAlertMenu(storeId);
                        break;
                    case 3:
                        Alerts.accceptDelivery(storeId);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 4");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }

    }

    private static void humanResourceManagerMenu(int storeId){
        validateRole("please insert your id:", "Human resource manager");
        String[] options = new String[]{"manage workers", "show alerts(orders which should be rescheduled due to shifts problems)", "reschedule order", "return to specific store actions menu"};
        while (true) {
            System.out.println("please chooses action to preform:");
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        manageWorkers(storeId);
                        break;
                    case 2:
                        Alerts.humanResourceAlert(storeId);
                        break;
                    case 3:
                        Alerts.rescheduleDelivery(storeId);
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 4");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }

    }

    private static void employeeMenu(int store_num){
        System.out.println("Enter Employee ID:");
        int eID = reader.nextInt();
        EmployeeController employeeController=EmployeeController.getInstance();
        if (employeeController.connect(eID,store_num)) {
            //If you are a storage worker, you enter here the storage worker menu
            //menu for normal employees(non storage)
            System.out.println("hello employee " + employeeController.getActiveName() + "\nchoose operation:");
            int operationNum = 0;
            while (operationNum != 3) {

                print_employee_menu();
                while (true){
                    try {
                        operationNum = Integer.parseInt(reader.nextLine());
                        break;
                    }
                    catch (Exception e){
                        System.out.println("not this time Irad! " + e.getMessage());
                    }
                }
                switch (operationNum) {
                    case 1: {
                        if (!employeeController.hasActiveUserAssignedShifts()) {
                            while (true){
                                boolean badInput = false;
                                print_shifts();
                                System.out.println("enter numbers of the capable shifts with ',' between each number, for example: ");
                                System.out.println("1,2,4,12");
                                String input = reader.nextLine();
                                String[] detailsStringArr = input.split(",");
                                int[] capableShifts = new int[detailsStringArr.length];
                                if (detailsStringArr.length != 4) {
                                    System.out.println("un appropriate amount of details inserted");
                                    continue;
                                }
                                for (int i = 0; i < detailsStringArr.length; i++) {
                                    try {
                                        capableShifts[i] = Integer.parseInt(detailsStringArr[i]);
                                    }
                                    catch (Exception e){
                                        System.out.println(e.getMessage());
                                        badInput = true;
                                        break;
                                    }
                                }
                                if (!badInput){
                                    System.out.println(employeeController.enterMyCapableShifts(capableShifts));
                                    break;
                                }
                            }
                        }
                        else {
                            System.out.println("you already assigned shifts");
                        }
                        break;
                    }
                    case 2: {
                        System.out.println(employeeController.watchMyShifts());
                        break;
                    }
                    case 3:
                    {
                        System.out.println("logging out");
                        break;
                    }
                }
            }
        } else {
            System.out.println("there is no employee with that id \n");
        }
    }

    private static void manageWorkers(int storeId){
        String[] options = new String[]{"Add new employee", "Add employee to a shift", "Add a role to a shift", "Update employee details",
                "Watch capable hours and capable roles of employees for shifts", "Watch employees", "Watch shifts", "Remove Employee", "return to human resource manager menu"};
        while (true){
            System.out.println("please choose action to perform:");
            printOptions(options);
            try {
                int choice= Integer.parseInt(reader.nextLine());
                switch(choice){
                    case 1:
                        enterEmployee(storeId);
                        break;
                    case 2:
                        assignEmployeeToShift(storeId);
                        break;
                    case 3:
                        addRoleToShift(storeId);
                        break;
                    case 4:
                        updateEmployeeDetails(storeId);
                        break;
                    case 5:
                        watchCapableHours(storeId);
                        break;
                    case 6:
                        watchEmployeeDetails(storeId);
                        break;
                    case 7:
                        watchShiftDetails(storeId);
                        break;
                    case 8:
                        removeEmployee(storeId);
                        break;
                    case 9:
                        return;
                    default:
                        System.out.println("Please choose a number between 1 and 8");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void manageDeliveries(int storeId){
        String[] options = new String[]{"Add Destination", "Add delivery", "Add truck", "View Deliveries", "return to logistic manager menu"};
        while (true){
            System.out.println("please choose action to perform:");
            printOptions(options);
            try {
                int choice= Integer.parseInt(reader.nextLine());
                switch(choice){
                    case 1:
                        addDestination(storeId);
                        break;
                    case 2:
                        addDelivery(storeId);
                        break;
                    case 3:
                        addTruck(storeId);
                        break;
                    case 4:
                        viewDeliveries(storeId);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Please choose a number between 1 and 5");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void manageProductTypesMenu(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"add type", "modify minimum quantity", "print all products with name", "print all products", "return to main"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch (option) {
                    case 1:
                        addProductDetailsFromUser();
                        break;
                    case 2:
                        System.out.println("please insert product type id:");
                        String id = reader.nextLine();
                        System.out.println("please insert new minimum quantity");
                        int newQuantity = Integer.parseInt(reader.nextLine());
                        ProductDetailsInterface.changeMinimalQuantity(id, newQuantity);
                        System.out.println("action preformed successfully");
                        break;
                    case 3:
                        System.out.println("please insert name:");
                        String name = reader.nextLine();
                        printNumberedList(ProductDetailsInterface.getProductDetailsByName(name));
                        break;
                    case 4:
                        printNumberedList(ProductDetailsInterface.getAllProducts());
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 5");
                }
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addProductDetailsFromUser(){
        System.out.println("please insert id:");
        String id = reader.nextLine();
        System.out.println("please insert name:");
        String name = reader.nextLine();
        System.out.println("please insert manufacturer:");
        String manufacturer = reader.nextLine();
        System.out.println("please insert retail price:");
        double retailPrice = Double.parseDouble(reader.nextLine());
        System.out.println("please insert days until expiration:");
        int daysUntilExpiration = Integer.parseInt(reader.nextLine());
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = Integer.parseInt(reader.nextLine());
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print):");
        String catId = reader.nextLine();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.nextLine();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, daysUntilExpiration, null, minimumQuantity);
        ProductDetailsInterface.addProductDetails(productDetails, catId);
        System.out.println("product type added successfully");
    }

    private static void manageProductsMenu(int storeId){
        while(true) {
            System.out.println("Please select an option to perform on products:");
            String[] options = new String[]{"add product", "move product", "prints all products of type", "print all products within storage", "print all missings products", "print all damaged products", "mark product as damaged", "print all products", "return to stores menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addProductFromUser(storeId);
                        break;
                    case 2:
                        moveProduct(storeId);
                        break;
                    case 3:
                        printAllStoreProductsOfType(storeId);
                        break;
                    case 4:
                        printNumberedList(ProductDetailsInterface.getStoreProductDetailsByStock(storeId));
                        break;
                    case 5:
                        printNumberedList(ProductDetailsInterface.getAllMissingsOfStore(storeId));
                        break;
                    case 6:
                        printNumberedList(ProductInterface.getAllDamagedOfStore(storeId));
                        break;
                    case 7:
                        System.out.println("please insert product id:");
                        ProductInterface.markAsDamaged(reader.nextLine(), storeId);
                        System.out.println("action preformed successfully");
                        break;
                    case 8:
                        System.out.println(ProductInterface.stringifyStoreProducts(storeId));
                        break;
                    case 9:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 9");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    public  static void printAllStoreProductsOfType(int storeId) {
        System.out.println("please insert the id of the product type(to print all the products types insert @print):");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        printNumberedList(ProductInterface.getStoreProductsByType(storeId, productId));
    }
    private static void moveProduct(int storeId) {
        System.out.println("please insert the id of the product to move:");
        String id = reader.nextLine();
        System.out.println("please insert the new location:");
        String newLocation = reader.nextLine();
        System.out.println("is the new location in the storage?(y for yes, else no):");
        String ans = reader.nextLine();
        ProductInterface.moveProduct(id, storeId, newLocation, ans.equals("y"));
        System.out.println("action preformed successfully");
    }
    private static void addProductFromUser(int storeId){
        System.out.println("please insert id:");
        String id = reader.nextLine();
        System.out.println("if you want to add multiple products insert y(the id will be the id you inserted, followed by running number):");
        String ans = reader.nextLine();
        int multiples = -1;
        if(ans.equals("y")){
            System.out.println("alright, please insert the number of products to add:");
            multiples = Integer.parseInt(reader.nextLine());
            if(multiples <= 0){
                System.out.println("the number must be greater than zero. canceling operation");
                return;
            }
        }
        System.out.println("please insert the id of the product type which this product is a type of(to print all the products types insert @print): ");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        for(int i = 1; i<= multiples; i++){
            String idToAssign = multiples != 1 ? id + i:id;
            Product product = new Product(idToAssign, storeId, "storage", true, false, null);
            ProductInterface.addProduct(product, productId);
        }
        System.out.println("action preformed successfully");
    }

    private static void manageDiscountsMenu(){
        while(true) {
            System.out.println("Please select an option to perform on discounts:");
            String[] options = new String[]{"add discount", "print discounts of certain type or category", "print current discount percentage of certain type","print pricing history of certain type",  "return to main menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addDiscountFromUser();
                        break;
                    case 2:
                        printDiscountsOfDiscountable();
                        break;
                    case 3:
                        printDiscountPercentageOfDiscountable();
                        break;
                    case 4:
                        printPricingHistoryOfCertainProduct();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 5");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void printDiscountsOfDiscountable() {
        System.out.println("is this product type?(y for product, else for category):");
        String ans = reader.nextLine();
        System.out.println("do you want to watch retail price?(y for retail, else for supplier price)");
        String ans2 = reader.nextLine();
        System.out.println("please insert the id of the discountable(to print all the products types insert @print):");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        System.out.println(DiscountInterface.getDiscountableDiscounts(productId, ans.equals("y"), ans2.equals("y")));
    }
    private static void printDiscountPercentageOfDiscountable() {
        System.out.println("do you want to watch retail price?(y for retail, else for supplier price)");
        String ans = reader.nextLine();
        System.out.println("please insert the id of the product type(to print all the products types insert @print):");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        System.out.println("the current discount percentage is: " + DiscountInterface.getProductDiscountPercentage(productId, ans.equals("y")));
    }
    private static void printPricingHistoryOfCertainProduct() {
        System.out.println("do you want to watch retail price?(y for retail, else for supplier price)");
        String ans = reader.nextLine();
        System.out.println("please insert the id of the product type(to print all the products types insert @print):");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        System.out.println("the pricing history is: " + DiscountInterface.getProductPricingHistory(productId, ans.equals("y")));
    }
    private static void addDiscountFromUser(){
        System.out.println("please insert the id of the discount:");
        String discId = reader.nextLine();
        System.out.println("please insert the percentage of the discount:");
        double percentage = Double.parseDouble(reader.nextLine());
        System.out.println("please insert the starting date of the discount(format like YYYY-MM-DD):");
        String startDateStr = reader.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("please insert the ending date of the discount(format like YYYY-MM-DD):");
        String endDateStr = reader.nextLine();
        LocalDate endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

        Discount discount = new Discount(discId, percentage, startDate, endDate);
        System.out.println("is the discount is on retail prices?(y for yes, else for no):");
        String ans = reader.nextLine();
        boolean retail = ans.equals("y");
        List<String> categoriesIds = new ArrayList<>();
        if(retail){
            System.out.println("now insert the id's of the discounted categories(separated by line breaks). when finished insert @stop:");
            String id = reader.nextLine();
            while(!id.equals("@stop")){
                categoriesIds.add(id);
                id = reader.nextLine();
            }
        }
        List<String> productsIds = new ArrayList<>();
        System.out.println("now insert the id's of the discounted products(separated by line breaks). when finished insert @stop:");
        String id = reader.nextLine();
        while(!id.equals("@stop")){
            productsIds.add(id);
            id = reader.nextLine();
        }
        DiscountInterface.addDiscount(discount, productsIds, categoriesIds, retail);
        System.out.println("Done.");
    }

    private static void manageCategoriesMenu(){
        while(true) {
            System.out.println("Please select an option to perform on categories:");
            String[] options = new String[]{"add category", "print all categories", "return to main menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addCategoryFromUser();
                        break;
                    case 2:
                        System.out.println(CategoryInterface.stringifyCategories());
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            }
            catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addCategoryFromUser(){
        System.out.println("please insert the category id:");
        String id = reader.nextLine();
        System.out.println("please insert the category name:");
        String name = reader.nextLine();
        System.out.println("please insert the category id, or @none to create new main category(to print all the categories insert @print):");
        String catId = reader.nextLine();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id, or @none to create new main category:");
            catId = reader.nextLine();
        }
        if(catId.equals("@none"))
            catId = null;
        Category category = new Category(id, name);
        category.setUpdated(true);
        CategoryInterface.addCategory(category, catId);
        System.out.println("category added successfully");
    }

    private static void manageReportsMenu(int storeId){
        while(true) {
            System.out.println("Please select an option to perform on reports:");
            String[] options = new String[]{"add report", "print all reports", "return to store menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addReportFromUser(storeId);
                        break;
                    case 2:
                        System.out.println(ReportInterface.stringifyStoreReports(storeId));
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 3");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addReportFromUser(int storeId){
        System.out.println("please insert report id:");
        String id = reader.nextLine();
        System.out.println("please insert employee id:");
        String employeeId = reader.nextLine();
        System.out.println("please insert description:");
        String description = reader.nextLine();
        Report report = new Report(id, storeId, employeeId, description, null, Report.reportType.Inventory);
        System.out.println("please select a type of report:");
        System.out.println("1) inventory");
        System.out.println("2) damaged products");
        System.out.println("3) missing products");
        int option = Integer.parseInt(reader.nextLine());
        switch (option){
            case 1:
                createInventoryReportFromUser(report);
                break;
            case 2:
                report.setReportType(Report.reportType.Damaged);
                ReportInterface.addDamagedReport(report);
                break;
            case 3:
                System.out.println("do you want to auto order missing products?(y for yes else no)");
                boolean autoOrder = reader.nextLine().equals("y");
                report.setReportType(Report.reportType.Missings);
                ReportInterface.addMissingReport(report, autoOrder);
                break;
            default:
                System.out.println("this isnt an option. operation canceled");
        }
        System.out.println("report added successfully");
    }
    private static void createInventoryReportFromUser(Report report){
        report.setReportType(Report.reportType.Inventory);
        System.out.println("please insert the id's of the category to track separated by line breaks.");
        System.out.println("insert @stop when finished.");
        String id = reader.nextLine();
        List<String> categoriesIds = new ArrayList<>();
        while (!id.equals("@stop")){
            categoriesIds.add(id);
            id = reader.nextLine();
        }
        ReportInterface.addInventoryReport(categoriesIds, report);
    }

    private static void manageOrdersMenu(int storeId){
        while(true) {
            System.out.println("Please select an option to perform on orders of the selected store: ");
            String[] options = new String[]{"manage store orders of specific provider", "print all store orders", "print all store automatic orders", "return to store menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        manageProviderOrdersMenu(storeId);
                        break;
                    case 2:
                        printNumberedList(OrdersInterface.getAllOrdersOfStore(storeId));
                        break;
                    case 3:
                        printNumberedList(OrdersInterface.getAllAutomaticsOrdersOfStore(storeId));
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 4");
                }
            }
            catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }

    }
    private static void manageProviderOrdersMenu(int storeId){
        String message = "please insert the id of the provider that you want to manage his orders";
        String providerId = selectProvider(message);
        while(true) {
            System.out.println("Please select an option to perform on orders of the selected provider in the selected store: ");
            String[] options = new String[]{"add order(regular or automatic)", "manage order items", "print all orders of provider", "print expanded order(with items)", "select another provider to manage", "return to order manage menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addOrderFromUser(storeId, providerId);
                        break;
                    case 2:
                        manageOrderItemsMenu(storeId, providerId);
                        break;
                    case 3:
                        printNumberedList(OrdersInterface.getAllStoreProviderOrders(storeId, providerId));
                        break;
                    case 4:
                        printSpecificOrder(storeId, providerId);
                        break;
                    case 5:
                        providerId = selectProvider(message);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 6");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addOrderFromUser(int storeId, String providerId){
        System.out.println("please insert the order id: ");
        String orderId = reader.nextLine();
        System.out.println("is this an automatic order?(y for yes else for no)");
        int orderDays = 0;
        Boolean answer = reader.nextLine().equals("y");
        if(answer){
            System.out.println("please insert the order days(when to auto create the order) as binary string.\nfor example if an order is to be sent every thursday, insert 0000100):");
            while (orderDays == 0) {
                String orderDaysString = reader.nextLine();
                if (orderDaysString.length() != 7) {
                    System.out.println("the string size should be seven(a digit for each day)");
                    continue;
                }
                try {
                    orderDays = Integer.parseInt(orderDaysString, 2);
                    if(orderDays == 0 ){
                        System.out.println("you cant add order which never get ordered. please select at least one day");
                    }
                } catch (Exception e) {
                    System.out.println("you inserted invalid binary string. try again:");
                }
            }
        }
        SingleProviderOrder singleProviderOrder = new SingleProviderOrder(null, storeId, orderId, answer ? null:StoreController.current_date, orderDays);
        OrdersInterface.SingleProviderOrderCreator(singleProviderOrder, providerId);
        System.out.println("order added successfully");
    }
    private static void printSpecificOrder(int storeId, String providerId){
        System.out.println("please insert the order id(to print all the orders of the provider insert @print): ");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getAllStoreProviderOrders(storeId, providerId));
            System.out.println("now insert the order id: ");
            orderId = reader.nextLine();
        }
        System.out.println(OrdersInterface.printOrderExpanded(providerId, orderId, storeId));
    }

    private static void manageOrderItemsMenu(int storeId, String providerId){
        String orderId = null;
        orderId = selectOrder(storeId, providerId);
        while(true) {
            System.out.println("Please select an option to perform on items of the selected order: ");
            String[] options = new String[]{"add item to order", "edit item on order", "remove item from order", "choose another order to manage", "return to manage provider orders"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addItemToOrder(storeId, providerId, orderId);
                        break;
                    case 2:
                        editItemOfOrder(storeId, providerId, orderId);
                        break;
                    case 3:
                        removeItemFromOrder(storeId, providerId, orderId);
                        break;
                    case 4:
                        orderId = selectOrder(storeId, providerId);
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 5");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addItemToOrder(int storeId, String providerId, String orderId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        System.out.println("please insert the amount to order: ");
        int orderAmount = Integer.parseInt(reader.nextLine());
        OrdersInterface.AddToOrder(providerId, orderId, storeId, catalogItemId, orderAmount);
        System.out.println("item added successfully");
    }
    private static void editItemOfOrder(int storeId, String providerId, String orderId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        System.out.println("please insert the new amount: ");
        int orderAmount = Integer.parseInt(reader.nextLine());
        OrdersInterface.EditOrder(providerId, orderId, storeId, catalogItemId, orderAmount);
        System.out.println("item modified successfully");
    }
    private static void removeItemFromOrder(int storeId, String providerId, String orderId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        OrdersInterface.RemoveFromOrder(providerId, orderId, storeId, catalogItemId);
        System.out.println("item removed successfully");
    }


    private static void manageProvidersMenu(){
        while(true) {
            System.out.println("Please select an option to perform on providers:");
            String[] options = new String[]{"add provider", "edit provider details", "manage provider catalog", "print all providers", "return to main"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addProviderFromUser();
                        break;
                    case 2:
                        editProviderDetails();
                        break;
                    case 3:
                        manageProviderCatalogMenu();
                        break;
                    case 4:
                        printProviders();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 5");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addProviderFromUser(){
        System.out.println("please insert id:");
        String providerId = reader.nextLine();
        System.out.println("please insert provider name:");
        String providerName = reader.nextLine();
        System.out.println("please insert credit card number:");
        String creditCardNumber = reader.nextLine();
        System.out.println("is the provider need transport?(y for yes, else no):");
        boolean needTransport = reader.nextLine().equals("y");
        boolean fixedDays = false;
        int delayDays = 0;
        int arrivalDays = -1;
        if(!needTransport) {
            System.out.println("is the provider comes in fixed days?(y for yes, else no):");
            fixedDays = reader.nextLine().equals("y");
            if (fixedDays) {
                System.out.println("please insert the arrival days of the provider as binary string.\nfor example if a provider comes in sunday, monday and friday insert 0100011):");
                while (arrivalDays == -1) {
                    String arrivalString = reader.nextLine();
                    if (arrivalString.length() != 7) {
                        System.out.println("the string size should be seven(a digit for each day)");
                        continue;
                    }
                    try {
                        arrivalDays = Integer.parseInt(arrivalString, 2);
                        if(arrivalDays == 0){
                            arrivalDays = -1;
                            System.out.println("provider must come at some day. try again:");
                        }
                    } catch (Exception e) {
                        System.out.println("you inserted invalid binary string. try again:");
                    }
                }
            } else {
                System.out.println("insert the number of days until the provider provides:");
                delayDays = Integer.parseInt(reader.nextLine());
            }
        }
        System.out.println("please insert a phone to contact with the provider:");
        String phoneNumber = reader.nextLine();
        System.out.println("please insert the adress of the provider:");
        String adress = reader.nextLine();
        Provider provider = new Provider(providerId, creditCardNumber, needTransport, delayDays, arrivalDays, providerName, null);
        CommunicationDetails communicationDetails = new CommunicationDetails(provider, fixedDays, phoneNumber, adress, null);
        communicationDetails.setUpdated(true);
        provider.setCommunicationDetails(communicationDetails);
        ProviderInterface.ProviderCreator(provider, communicationDetails);
        System.out.println("provider added successfully");
    }
    private static void editProviderDetails(){
        String providerId = selectProvider("please insert the id of the provider to edit");
        System.out.println("please insert provider new name, or @none for keep it unchanged:");
        String providerName = reader.nextLine();
        if(providerName.equals("@none"))
            providerName = null;
        System.out.println("is the provider need transport?(y for yes, else no, or @none for keep it unchanged):");
        String needTransportStr = reader.nextLine();
        Boolean needTransport = needTransportStr.equals("@none") ? null : needTransportStr.equals("y");
        System.out.println("is the provider comes in fixed days?(y for yes, else no, or @none for keep it unchanged):");
        String fixedDaysStr = reader.nextLine();
        Boolean fixedDays = fixedDaysStr.equals("@none") ? null : fixedDaysStr.equals("y");
        Integer arrivalDays = null;
        if(fixedDays != null && fixedDays) {
            System.out.println("please insert the new arrival days of the provider as binary string, or @none for keep it unchanged.\nfor example if a provider comes in sunday, monday and friday insert 0100011):");
            while (arrivalDays == null) {
                String arrivalString = reader.nextLine();
                if(arrivalString.equals("@none")){
                    arrivalDays = null;
                    break;
                }
                if (arrivalString.length() != 7) {
                    System.out.println("the string size should be seven(a digit for each day)");
                }
                try {
                    arrivalDays = Integer.parseInt(arrivalString, 2);
                } catch (Exception e) {
                    System.out.println("you inserted invalid binary string. try again:");
                }
            }
        }
        System.out.println("please insert the new phone of the provider, or @none for keep it unchanged:");
        String phoneNumber = reader.nextLine();
        if(phoneNumber.equals("@none"))
            phoneNumber = null;
        System.out.println("please insert the new adress of the provider, or @none for keep it unchanged:");
        String address = reader.nextLine();
        if(address.equals("@none"))
            address = null;
        ProviderInterface.editDetails(providerId, needTransport, null, arrivalDays, providerName, fixedDays, phoneNumber, address);
        System.out.println("provider edited successfully");
    }
    private static void printProviders(){
        printNumberedList(ProviderInterface.getAllProviders());
    }

    private static void manageProviderCatalogMenu(){
        String providerId = selectProvider("select provider to manage his items");
        while(true) {
            System.out.println("Please select an option to perform on items of the selected provider: ");
            String[] options = new String[]{"add item to catalog", "edit item on catalog", "print provider catalog","choose another provider to manage", "return to manage provider menu"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addItemToCatalog(providerId);
                        break;
                    case 2:
                        editItemOfCatalog(providerId);
                        break;
                    case 3:
                        printProviderCatalog(providerId);
                        break;
                    case 4:
                        providerId = selectProvider("select provider to manage his items");
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 5");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addItemToCatalog(String providerId){
        System.out.println("please insert the catalog item number:");
        String catalogItemId = reader.nextLine();
        System.out.println("please insert the price of the item: ");
        double price = Double.parseDouble(reader.nextLine());
        System.out.println("please insert the id of the product details that the catalog item represent(to print all the product details insert @print): ");
        String productDetailsId = reader.nextLine();
        if(productDetailsId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productDetailsId = reader.nextLine();
        }
        CatalogItem catalogItem = new CatalogItem(providerId, catalogItemId, price, null);
        CatalogItemsInterface.addItem(providerId, catalogItem, productDetailsId);
        System.out.println("item added successfully");
    }
    private static void editItemOfCatalog(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        System.out.println("please insert the new price: ");
        double newPrice = Double.parseDouble(reader.nextLine());
        CatalogItemsInterface.editItem(providerId, catalogItemId, newPrice);
        System.out.println("item modified successfully");
    }
    private static void removeItemFromCatalog(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        CatalogItemsInterface.removeItem(providerId, catalogItemId);
        System.out.println("item removed successfully");
    }
    private static void printProviderCatalog(String providerId){
        printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
    }

    private static void manageAgreementsMenu(){
        String message = "please insert the id of the provider that you want to manage his orders";
        String providerId = selectProvider(message);
        while(true) {
            System.out.println("Please select an option to perform on agreements:");
            String[] options = new String[]{"add item to agreement", "edit item of agreement", "remove item from agreement", "print all items of agreement", "select another provider to manage his agreement", "return to main"};
            printOptions(options);
            try {
                int option = Integer.parseInt(reader.nextLine());
                switch(option){
                    case 1:
                        addItemToAgreement(providerId);
                        break;
                    case 2:
                        editItemOfAgreement(providerId);
                        break;
                    case 3:
                        removeItemFromAgreement(providerId);
                        break;
                    case 4:
                        printItemsOfAgreement(providerId);
                        break;
                    case 5:
                        providerId = selectProvider(message);
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("choose an option between 1 to 6");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void addItemToAgreement(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        System.out.println("please insert minimum amount to get discount:");
        int minAmount = Integer.parseInt(reader.nextLine());
        System.out.println("please insert percentage of discount:");
        double discount = Double.parseDouble(reader.nextLine());
        AgreementsInterface.addItemToAgreement(providerId, catalogItemId, minAmount, discount);
        System.out.println("item added successfully");
    }
    private static void editItemOfAgreement(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of agreement insert @print):");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
            System.out.println(stringifiedItems);
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        System.out.println("please insert the new minimum amount to get discount(or @none to keep it unchanged):");
        String minAmountStr = reader.nextLine();
        Integer minAmount = minAmountStr.equals("@none") ? null : Integer.parseInt(minAmountStr);
        System.out.println("please insert the new percentage of discount(or @none to keep it unchanged):");
        String discountStr = reader.nextLine();
        Double discount = discountStr.equals("@none") ? null : Double.parseDouble(discountStr);
        AgreementsInterface.editItemAgreement(providerId, catalogItemId, minAmount, discount);
        System.out.println("item modified successfully");
    }
    private static void removeItemFromAgreement(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of agreement insert @print):");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
            System.out.println(stringifiedItems);
            System.out.println("now insert the catalog item id: ");
            catalogItemId = reader.nextLine();
        }
        AgreementsInterface.removeItemFromAgreement(providerId, catalogItemId);
        System.out.println("item removed successfully");
    }
    private static void printItemsOfAgreement(String providerId){
        String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
        System.out.println(stringifiedItems);
    }

    static void enterEmployee(int store_num) {
        System.out.println("enter the following details, as in the example:");
        System.out.println("name,employee id,bank account,salary,employee conditions,start date");
        System.out.println("aviv,5,6045,3000,no special conditions,12/4/2020");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 6) {
            System.out.println("un appropriate amount of details inserted");
            return;
        }
        System.out.println("is the new employee a driver? y/n");
        String answer = reader.nextLine();
        while (!(answer.equals("y") || answer.equals("n")))
        {
            System.out.println("enter y or n");
            answer = reader.nextLine();
        }
        if (answer.equals("y"))
        {
            System.out.println("enter the driver license");
            String license = reader.nextLine();

            String[] role = {"driver"};
            int eID = 0;
            int salary = 0;
            try {
                eID = Integer.parseInt(detailsStringArr[1]);
                salary = Integer.parseInt(detailsStringArr[3]);
            }
            catch (Exception e){
                System.out.println(e.getMessage());
                return;
            }
            System.out.println(managerController.addDriverEmployee(role,detailsStringArr[0],
                    eID, detailsStringArr[2], store_num,
                    salary, detailsStringArr[4], detailsStringArr[5],license));
        }
        else {
            System.out.println("enter capable jobs, as in the example:");
            System.out.println("cashier,shift manager, 'storage' for an employee that will be stock keeper");
            System.out.println("stock keeper has more options");
            String roles = reader.nextLine();

            String[] rolesArr = roles.split(",");

            String addingNewEmployee = managerController.addEmployee(rolesArr, detailsStringArr[0],
                    Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], store_num,
                    Integer.parseInt(detailsStringArr[3]), detailsStringArr[4], detailsStringArr[5]);
            System.out.println(addingNewEmployee);
        }


    }
    //Assigning employee to shift
    static void assignEmployeeToShift(int store_num) {

        System.out.println("enter the following details, as in the example:");
        System.out.println("employee id,day num(1-7),day part(morning-evening),role");
        System.out.println("5,2,morning,cashier");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 4) {
            System.out.println("un appropriate amount of details inserted");

        }

        int eID = 0;
        int dayNum = 0;
        try {
            eID = Integer.parseInt(detailsStringArr[0]);
            dayNum = Integer.parseInt(detailsStringArr[1]);
        }
        catch (Exception e){
            System.out.println("Error " + e.getMessage());
            return;
        }

        String addingEmployeeToShift = managerController.addToShift(store_num, eID, dayNum, detailsStringArr[2], detailsStringArr[3]);
        System.out.println(addingEmployeeToShift);

    }
    //Adding a role to a shift, still need someone to fill that role
    static void addRoleToShift(int store_num){

        System.out.println("enter the following details, as in the example:");
        System.out.println("day num(1-7),day part(morning-evening),role,amount");
        System.out.println("3,evening,shift manager,2");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 4) {
            System.out.println("un appropriate amount of details inserted");

        }
        int dayNum = 0;
        int amount = 0;
        try {
            dayNum = Integer.parseInt(detailsStringArr[0]);
            amount = Integer.parseInt(detailsStringArr[3]);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        String addingRoleToShift = managerController.addRoleToShift(store_num, dayNum, detailsStringArr[1], detailsStringArr[2], amount);
        System.out.println(addingRoleToShift);

    }
    //update details of employee
    static void updateEmployeeDetails(int store_num) {

        System.out.println("Enter Employee ID:");
        int eID = 0;
        try {
            eID = Integer.parseInt(reader.nextLine());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }

        System.out.println(managerController.getEmployeeDetails(store_num, eID));
        System.out.println("enter the following details, as in the example:");
        System.out.println("name,bank account,salary,employee conditions");
        System.out.println("tal,6156,3000,no special conditions");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 4) {
            System.out.println("un appropriate amount of details inserted");

        }
        int salary = 0;
        try {
            salary = Integer.parseInt(detailsStringArr[2]);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
//                            System.out.println("enter capable jobs, as in the example:");
//                            System.out.println("cashier,shift manager");
//                            String roles = reader.nextLine();
//                            String[] rolesArr = roles.split(",");

        String updatingEmployee = managerController.updateEmployee(store_num, detailsStringArr[0],
                eID, detailsStringArr[1], salary,
                detailsStringArr[3]);
        System.out.println(updatingEmployee);




                            /*
                            System.out.println("Enter Employee ID:");
                            int eID = reader.nextInt();
                            System.out.println("Enter Store Number:");
                            int store_num = reader.nextInt();
                            reader.nextLine();

                                System.out.println(managerController.getEmployeeDetails(store_num,eID));
                                System.out.println("enter the following details, as in the example:");
                                System.out.println("name,bank account,salary,employee conditions,start date");
                                System.out.println("tal,6156,3000,no special conditions,12/4/2020");
                                String details = reader.nextLine();
                                String[] detailsStringArr = details.split(",");
                                if (detailsStringArr.length != 6) {
                                    System.out.println("un appropriate amount of details inserted");

                                }
                                System.out.println("enter capable jobs, as in the example:");
                                System.out.println("cashier,shift manager");
                                String roles = reader.nextLine();
                                String[] rolesArr = roles.split(",");

                                String updatingEmployee = managerController.updateEmployee(Integer.parseInt(detailsStringArr[0]), rolesArr, detailsStringArr[1],
                                        eID, detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                                        detailsStringArr[4], detailsStringArr[5]);
                                System.out.println(updatingEmployee); */


    }
    //Watch all the hours that people are capable to work in
    static void watchCapableHours(int store_num) {
        System.out.println(managerController.getCapableShiftsByEmployees(store_num));

    }
    //Watch all the details of all the employees
    static void watchEmployeeDetails(int store_num) {
        //TODO change to print store specific employees   --- aviv: more comfortable to keep it for all stores

        System.out.println(managerController.getEmployeesDetails());
    }
    //Watch details of all the shifts
    static void watchShiftDetails(int store_num) {

        System.out.println(managerController.getShiftsDetails(store_num));

    }
    //Remove employee from system
    static void removeEmployee(int store_num) {

        System.out.println("enter employee id");
        int ID = 0;
        try {
            ID = Integer.parseInt(reader.nextLine());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(managerController.removeEmployee(ID));

    }
    //Add new destination, delivery can go to that destination now
    static void addDestination(int store_num) {
        System.out.println("enter address");
        String address = reader.nextLine();

        System.out.println("enter contact");
        String contact = reader.nextLine();

        System.out.println("enter phone");
        String phone = reader.nextLine();

        System.out.println("enter area");
        String area = reader.nextLine();

        boolean goodInput = false;
        while (!goodInput){
            goodInput = true;
            if (address.isEmpty()){
                System.out.println("empty address, please enter a new one");
                address = reader.nextLine();
                goodInput = false;
            }
            if (contact.isEmpty()){
                System.out.println("empty contact, please enter a new one");
                contact = reader.nextLine();
                goodInput = false;
            }
            if (phone.isEmpty()){
                System.out.println("empty phone number, please enter a new one");
                phone = reader.nextLine();
                goodInput = false;
            }
            if (area.isEmpty()){
                System.out.println("empty area, please enter a new one");
                area = reader.nextLine();
                goodInput = false;
            }
            for (int i = 0; i < phone.length(); i++) {
                if (!((phone.charAt(i) >= '0' & phone.charAt(i) <= '9') | (phone.charAt(i) == '-'))){
                    System.out.println("invalid phone number, please enter a new one");
                    phone = reader.nextLine();
                    goodInput = false;

                }
            }
        }
        managerController.addDestination(address, contact, phone, area);

    }
    //Add a new delivery to the system
    static void addDelivery(int store_num) {
        sendDelivery(store_num);

    }
    //Add a new truck to the system
    static void addTruck(int store_num)
    {
        System.out.println("enter model");
        String model = reader.nextLine();

        System.out.println("enter Truck id");
        String truckId = reader.nextLine();

        System.out.println("enter weight");
        int weight = 0;
        int maxWeight = 0;
        try {
            weight = Integer.parseInt(reader.nextLine());
            System.out.println("enter max weight");
            maxWeight = Integer.parseInt(reader.nextLine());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }



        boolean goodInput = false;
        while (!goodInput) {
            goodInput = true;
            if (truckId.isEmpty()) {
                System.out.println("empty truck id, please enter a new one");
                truckId = reader.nextLine();
                goodInput = false;
            }
            if (model.isEmpty()) {
                System.out.println("empty model, please enter a new one");
                model = reader.nextLine();
                goodInput = false;
            }
        }
        managerController.addTruck(store_num,truckId, weight, maxWeight, model);

    }
    //View all the deliveries
    static void viewDeliveries(int store_num)
    {
        System.out.println(managerController.viewDeliveries(store_num));

    }
    //Watch the date of the first day in the week(for some reason)
    void WatchSunday()
    {
        System.out.println(managerController.getSundayDate());

    }

    public static void print_Storage_employee_menu()
    {
        System.out.println("1. Insert available times for shifts");
        System.out.println("2. Watch shifts of this week");
        System.out.println("3. Log out");
    }
    public static void print_employee_menu()
    {
        System.out.println("1. Insert available times for shifts");
        System.out.println("2. Watch shifts of this week");
        System.out.println("3. Log out");
    }


    public static void sendDelivery(int store_num){

        System.out.println("Delivery can be added to this week only");

        System.out.println("Please enter truck id:");
        String truckid = reader.nextLine();

        System.out.println("Please enter order id:");
        String orderid = reader.nextLine();

        System.out.println("Please enter the total weight of the stuff you want to deliver:");
        int weight = 0;
        try {
            weight = Integer.parseInt(reader.nextLine());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        boolean stop = false;
        System.out.println(ManagerController.getInstance().addDelivery(weight,truckid,orderid,store_num));
    }

    public static void print_shifts()
    {
        System.out.println("1.sunday morning      2.sunday evening");
        System.out.println("3.monday morning      4.monday evening");
        System.out.println("5.tuesday morning     6.tuesday evening");
        System.out.println("7.wednesday morning   8.wednesday evening");
        System.out.println("9.thursday morning    10.thursday evening");
        System.out.println("11.friday morning     12.friday evening");
        System.out.println("13.saturday morning   14.saturday evening");

    }

    private static int validateRole(String message, String wantedRole){
        System.out.println(message);
        int employeeId = Integer.parseInt(reader.nextLine());
        ManagerController.validateRole(employeeId, wantedRole);
        return employeeId;
    }
    private static void selectDay(){

        System.out.println("Initializing System..");
        System.out.println("ENTER MONTH");
        int month = 0;
        int day = 0;
        try {
            month = Integer.parseInt(reader.nextLine());
            System.out.println("ENTER DAY");
            day = Integer.parseInt(reader.nextLine());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return;
        }
        managerController.initializeStores(month, day);
    }
    private static int selectStore(){
        System.out.println("please insert the store num:");
        int storeId = Integer.parseInt(reader.nextLine());
        while(storeId != 1 && storeId != 2){
            System.out.println("there is no store with that id. please insert again.");
            storeId = Integer.parseInt(reader.nextLine());
        }
        return storeId;
    }
    private static String selectOrder(int storeId, String providerId){
        System.out.println("please insert the id of the order that you want to manage her items(to print all the orders of provider insert @print): ");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getAllStoreProviderOrders(storeId, providerId));
            System.out.println("now insert the id of the provider: ");
            orderId = reader.nextLine();
        }
        return orderId;
    }
    private static String selectProvider(String message){
        System.out.println(message + "(to print all providers insert @print):");
        String providerId = reader.nextLine();
        if(providerId.equals("@print")){
            printProviders();
            System.out.println("now insert the id of the provider: ");
            providerId = reader.nextLine();
        }
        return providerId;
    }
    public static void printOptions(String[] array){
        int i = 1;
        for(String str: array)
            System.out.println(i++ + ") " + str);
    }
    public static void printNumberedList(List<? extends Object> objects){
        int i = 1;
        for(Object object : objects)
            System.out.println(i++ + ") " + object.toString());
    }
}
