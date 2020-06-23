package PL;

import BL.Employee;
import DAL.DBHandler;
import DAL.OrdersDAL;
import Entities.*;
import IL.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static Scanner reader = new Scanner(System.in);
    public static  ManagerController managerController = ManagerController.getInstance();
    
    public static void main(String[] args) {
        DBHandler.connect();
        boolean dummyLoaded = false;
        initialSelection();
    }


    private static void initialSelection() {
        ManagerController managerController = ManagerController.getInstance();
        EmployeeController employeeController = EmployeeController.getInstance();
        if (!managerController.loadStores())//Initializing the day
        {
            System.out.println("Initializing System..");
            System.out.println("ENTER MONTH");
            int month = reader.nextInt();
            System.out.println("ENTER DAY");
            int day = reader.nextInt();

            managerController.initializeStores(month, day);
        }

        //CHOOSE ROLE IN THE SYSTEM
        int choice = 0;
        while (choice != 5) {
            System.out.println("Hello, Welcome To Super-Li System");
            System.out.println("Choose Your Role Manager/Employee or exit program");
            System.out.println("1.Manager");
            System.out.println("2.Employee");
            System.out.println("3.Store manager");
            System.out.println("4.Logistic manager");
            System.out.println("5.Exit");
            choice = reader.nextInt();

            switch(choice){
                case 1:
                    managerMenu();
                    break;
                case 2:
                    employeeMenu();
                    break;
                case 3:
                    storeManagerMenu();
                    break;
                case 4:
                    logisticManagerMenu();
                    break;
                case 5:
                    return;
                    break;
                case 6:
                    System.out.println("Please choose a number between 1 and 5");
                    break;
            }
        }
    }
    private static void managerMenu(){
        System.out.println("Choose part to work on:");
        String[] options=new String[]{"workers","deliveries","supplies/products"};
        printOptions(options);
        int option = Integer.parseInt(reader.nextLine());
        try {
            switch(option){
                case 1:
                    manageWorkers();
                    break;
                case 2:
                    manageDeliveries();
                    break;
                case 3:
                    manageProductsDeliveries();
                    break;
                default:
                    System.out.println("choose an option between 1 to 3");
            }
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void manageWorkers(){
        System.out.println("1.Add new employee");
        System.out.println("2.Add employee to a shift");
        System.out.println("3.Add a role to a shift");
        System.out.println("4.Update employee details");
        System.out.println("5.Watch capable hours and capable roles of employees for shifts");
        System.out.println("6.Watch employees");
        System.out.println("7.Watch shifts");
        System.out.println("8.Remove Employee");
        int choice=reader.nextInt();
        switch(choice){
            case 1:
                enterEmployee();
                break;
            case 2:
                assignEmployeeToShift();
                break;
            case 3:
                addRoleToShift();
                break;
            case 4:
                updateEmployeeDetails();
                break;
            case 5:
                watchCapableHours();
                break;
            case 6:
                watchEmployeeDetails();
                break;
            case 7:
                watchShiftDetails();

            case 8:
                removeEmployee();


             default:
                System.out.println("Please choose a number between 1 and 8");
        }
    }



    private static void manageDeliveries(){
    ;   System.out.println("1.Add Destination");
        System.out.println("2.Add Delivery");
        System.out.println("3.Add Truck");
        System.out.println("4.View Deliveries");
        int choice=reader.nextInt();
        switch(choice){
            case 1:
                addDestination();
                break;
            case 2:
                addDelivery();
                break;
            case 3:
                addTruck();
                break;
            case 4:
                viewDeliveries();
                break;



            default:
                System.out.println("Please choose a number between 1 and 4");
        }
    }

    private static void manageProductsDeliveries(){
        DBHandler.connect();
        boolean dummyLoaded = false;
        while(true) {
            System.out.println("Please select a category to manage or operation to perform:");
            String[] options = new String[]{"product types", "discounts", "categories", "providers and catalogs", "agreements", "manage store(reports, products, orders, more...)", "exit"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
                switch(option){
                    case 1:
                        manageProductTypesMenu();
                        break;
                    case 2:
                        manageDiscountsMenu();
                        break;
                    case 3:
                        manageCategoriesMenu();
                        break;
                    case 4:
                        manageProvidersMenu();
                        break;
                    case 5:
                        manageAgreementsMenu();
                        break;
                    case 6:
                        manageStoreMenu();
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
    private static void manageStoreMenu(){
        int storeId = selectStore();
        System.out.println("please insert your employee id:");
        String employeeId = reader.nextLine();
        //TODO here validate the employee id
        String[] options = new String[]{"manage products", "manage orders(and auto orders)", "manage reports(including auto order missing items)", "change store", "change employee id", "return to main menu"};
        while(true) {
            System.out.println("Please select a category to manage or operation to perform:");
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
                switch (option){
                    case 1:
                        manageProductsMenu(storeId);
                        break;
                    case 2:
                        manageOrdersMenu(storeId);
                        break;
                    case 3:
                        manageReportsMenu(storeId, employeeId);
                        break;
                    case 4:
                        storeId = selectStore();
                        break;
                    case 5:
                        System.out.println("please insert new employee id:");
                        employeeId = reader.nextLine();
                        //TODO here validate the employee id
                        break;
                    case 6:
                        return;
                    default:
                        System.out.println("please insert option between 1 to 6");
                }
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }
    private static void manageProductTypesMenu(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"add type", "modify minimum quantity", "print all products with name", "print all products", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
    }

    private static void manageProductsMenu(int storeId){
        while(true) {
            System.out.println("Please select an option to perform on products:");
            String[] options = new String[]{"add product", "move product", "prints all products of type", "print all products within storage", "print all missings products", "print all damaged products", "mark product as damaged", "print all products", "return to stores menu"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
                    case 6:
                        printNumberedList(ProductInterface.getAllDamagedOfStore(storeId));
                        break;
                    case 7:
                        System.out.println("please insert product id:");
                        ProductInterface.markAsDamaged(reader.nextLine(), storeId);
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
    private static void printAllStoreProductsOfType(int storeId) {
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
            try {
                ProductInterface.addProduct(product, productId);
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void manageDiscountsMenu(){
        while(true) {
            System.out.println("Please select an option to perform on discounts:");
            String[] options = new String[]{"add discount", "print discounts of certain type or category", "print current discount percentage of certain type","print pricing history of certain type",  "return to main menu"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
            int option = Integer.parseInt(reader.nextLine());
            try {
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
    }

    private static void manageReportsMenu(int storeId, String employeeId){
        while(true) {
            System.out.println("Please select an option to perform on reports:");
            String[] options = new String[]{"add report", "print all reports", "return to store menu"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
                switch(option){
                    case 1:
                        addReportFromUser(storeId, employeeId);
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
    private static void addReportFromUser(int storeId, String employeeId){
        System.out.println("please insert report id:");
        String id = reader.nextLine();
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
            int option = Integer.parseInt(reader.nextLine());
            try {
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
            int option = Integer.parseInt(reader.nextLine());
            try {
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
        SingleProviderOrder singleProviderOrder = new SingleProviderOrder(null, storeId, orderId, answer ? null:LocalDate.now(), orderDays);
        OrdersInterface.SingleProviderOrderCreator(singleProviderOrder, providerId);
    }
    private static void printSpecificOrder(int storeId, String providerId){
        System.out.println("please insert the order id(to print all the orders of the provider insert @print): ");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            try {
                printNumberedList(OrdersInterface.getAllStoreProviderOrders(storeId, providerId));
                System.out.println("now insert the order id: ");
                orderId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println(OrdersInterface.printOrderExpanded(providerId, orderId, storeId));
    }

    private static void manageOrderItemsMenu(int storeId, String providerId){
        String orderId = null;
        try {
            orderId = selectOrder(storeId, providerId);
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
            return;
        }
        while(true) {
            System.out.println("Please select an option to perform on items of the selected order: ");
            String[] options = new String[]{"add item to order", "edit item on order", "remove item from order", "choose another order to manage", "return to manage provider orders"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println("please insert the amount to order: ");
        int orderAmount = Integer.parseInt(reader.nextLine());
        OrdersInterface.AddToOrder(providerId, orderId, storeId, catalogItemId, orderAmount);
    }
    private static void editItemOfOrder(int storeId, String providerId, String orderId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println("please insert the new amount: ");
        int orderAmount = Integer.parseInt(reader.nextLine());
        OrdersInterface.EditOrder(providerId, orderId, storeId, catalogItemId, orderAmount);
    }
    private static void removeItemFromOrder(int storeId, String providerId, String orderId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        OrdersInterface.RemoveFromOrder(providerId, orderId, storeId, catalogItemId);
    }


    private static void manageProvidersMenu(){
        while(true) {
            System.out.println("Please select an option to perform on providers:");
            String[] options = new String[]{"add provider", "edit provider details", "manage provider catalog", "print all providers", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
            int option = Integer.parseInt(reader.nextLine());
            try {
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
        try {
            CatalogItemsInterface.addItem(providerId, catalogItem, productDetailsId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void editItemOfCatalog(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println("please insert the new price: ");
        double newPrice = Double.parseDouble(reader.nextLine());
        try {
            CatalogItemsInterface.editItem(providerId, catalogItemId, newPrice);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void removeItemFromCatalog(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of provider insert @print): ");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        try {
            CatalogItemsInterface.removeItem(providerId, catalogItemId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void printProviderCatalog(String providerId){
        try {
            printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageAgreementsMenu(){
        String message = "please insert the id of the provider that you want to manage his orders";
        String providerId = selectProvider(message);
        while(true) {
            System.out.println("Please select an option to perform on agreements:");
            String[] options = new String[]{"add item to agreement", "edit item of agreement", "remove item from agreement", "print all items of agreement", "select another provider to manage his agreement", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            try {
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
            try {
                printNumberedList(CatalogItemsInterface.getAllItemsOfProvider(providerId));
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println("please insert minimum amount to get discount:");
        int minAmount = Integer.parseInt(reader.nextLine());
        System.out.println("please insert percentage of discount:");
        double discount = Double.parseDouble(reader.nextLine());
        try {
            AgreementsInterface.addItemToAgreement(providerId, catalogItemId, minAmount, discount);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void editItemOfAgreement(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of agreement insert @print):");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
                System.out.println(stringifiedItems);
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println("please insert the new minimum amount to get discount(or @none to keep it unchanged):");
        String minAmountStr = reader.nextLine();
        Integer minAmount = minAmountStr.equals("@none") ? null : Integer.parseInt(minAmountStr);
        System.out.println("please insert the new percentage of discount(or @none to keep it unchanged):");
        String discountStr = reader.nextLine();
        Double discount = discountStr.equals("@none") ? null : Double.parseDouble(discountStr);
        try {
            AgreementsInterface.editItemAgreement(providerId, catalogItemId, minAmount, discount);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void removeItemFromAgreement(String providerId){
        System.out.println("please insert the catalog item id(to print all the items of agreement insert @print):");
        String catalogItemId = reader.nextLine();
        if(catalogItemId.equals("@print")){
            try {
                String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
                System.out.println(stringifiedItems);
                System.out.println("now insert the catalog item id: ");
                catalogItemId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        try {
            AgreementsInterface.removeItemFromAgreement(providerId, catalogItemId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void printItemsOfAgreement(String providerId){
        try {
            String stringifiedItems = AgreementsInterface.stringifyAgreementItems(providerId);
            System.out.println(stringifiedItems);
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }

    private static int selectStore(){
        System.out.println("please insert the store id:");
        int storeId = Integer.parseInt(reader.nextLine());
        while(storeId != 1 && storeId != 2){
            System.out.println("there is no store with that id. please insert again.");
            storeId = Integer.parseInt(reader.nextLine());
        }
        return storeId;
    }
    private static String selectOrder(int storeId, String providerId) throws Exception{
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
    private static void printOptions(String[] array){
        int i = 1;
        for(String str: array)
            System.out.println(i++ + ") " + str);
    }
    private static void printNumberedList(List<? extends Object> objects){
        int i = 1;
        for(Object object : objects)
            System.out.println(i++ + ") " + object.toString());
    }

    static void enterEmployee() {
        System.out.println("enter the following details, as in the example:");
        System.out.println("name,employee id,bank account,store num,salary,employee conditions,start date");
        System.out.println("aviv,5,6045,1,3000,no special conditions,12/4/2020");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 7) {
            System.out.println("un appropriate amount of details inserted");

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
            System.out.println(managerController.addDriverEmployee(role,detailsStringArr[0],
                    Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                    Integer.parseInt(detailsStringArr[4]), detailsStringArr[5], detailsStringArr[6],license));
        }
        else {
            System.out.println("enter capable jobs, as in the example:");
            System.out.println("cashier,shift manager");
            String roles = reader.nextLine();

            String[] rolesArr = roles.split(",");

            String addingNewEmployee = managerController.addEmployee(rolesArr, detailsStringArr[0],
                    Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], Integer.parseInt(detailsStringArr[3]),
                    Integer.parseInt(detailsStringArr[4]), detailsStringArr[5], detailsStringArr[6]);
            System.out.println(addingNewEmployee);
        }

    }
    //Assigning employee to shift
    static void assignEmployeeToShift() {
        System.out.println("enter the following details, as in the example:");
        System.out.println("store num,employee id,day num(1-7),day part(morning-evening),role");
        System.out.println("1,5,2,morning,cashier");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 5) {
            System.out.println("un appropriate amount of details inserted");

        }

        String addingEmployeeToShift = managerController.addToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), Integer.parseInt(detailsStringArr[2]), detailsStringArr[3], detailsStringArr[4]);
        System.out.println(addingEmployeeToShift);

    }
    //Adding a role to a shift, still need someone to fill that role
    static void addRoleToShift(){
        System.out.println("enter the following details, as in the example:");
        System.out.println("store num,day num(1-7),day part(morning-evening),role,amount");
        System.out.println("1,3,evening,shift manager,2");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 5) {
            System.out.println("un appropriate amount of details inserted");

        }
        String addingRoleToShift = managerController.addRoleToShift(Integer.parseInt(detailsStringArr[0]), Integer.parseInt(detailsStringArr[1]), detailsStringArr[2], detailsStringArr[3], Integer.parseInt(detailsStringArr[4]));
        System.out.println(addingRoleToShift);

    }
    //update details of employee
    static void updateEmployeeDetails() {

        System.out.println("Enter Employee ID:");
        int eID = reader.nextInt();
        System.out.println("Enter Store Number:");
        int store_num = reader.nextInt();
        reader.nextLine();

        System.out.println(managerController.getEmployeeDetails(store_num, eID));
        System.out.println("enter the following details, as in the example:");
        System.out.println("name,bank account,salary,employee conditions");
        System.out.println("tal,6156,3000,no special conditions");
        String details = reader.nextLine();
        String[] detailsStringArr = details.split(",");
        if (detailsStringArr.length != 4) {
            System.out.println("un appropriate amount of details inserted");

        }
//                            System.out.println("enter capable jobs, as in the example:");
//                            System.out.println("cashier,shift manager");
//                            String roles = reader.nextLine();
//                            String[] rolesArr = roles.split(",");

        String updatingEmployee = managerController.updateEmployee(store_num, detailsStringArr[0],
                eID, detailsStringArr[1], Integer.parseInt(detailsStringArr[2]),
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
    static void watchCapableHours() {
        System.out.println("enter the store num");
        int store_num = reader.nextInt();
        reader.nextLine();
        System.out.println(managerController.getCapableShiftsByEmployees(store_num));

    }
    //Watch all the details of all the employees
    static void watchEmployeeDetails() {
        System.out.println(managerController.getEmployeesDetails());

    }
    //Watch details of all the shifts
    static void watchShiftDetails() {

        System.out.println("enter store num");
        int store_num = reader.nextInt();
        reader.nextLine();
        System.out.println(managerController.getShiftsDetails(store_num));

    }
    //Remove employee from system
    static void removeEmployee() {
        System.out.println("enter employee id");
        int ID = reader.nextInt();
        reader.nextLine();
        System.out.println(managerController.removeEmployee(ID));

    }
    //Add new destination, delivery can go to that destination now
    static void addDestination() {
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
    static void addDelivery() {
        sendDelivery();

    }
    //Add a new truck to the system
    static void addTruck()
    {
        System.out.println("enter model");
        String model = reader.nextLine();

        System.out.println("enter Truck id");
        String truckId = reader.nextLine();

        System.out.println("enter store number");
        int store_num = reader.nextInt();

        System.out.println("enter weight");
        int weight = reader.nextInt();

        System.out.println("enter max weight");
        int maxWeight = reader.nextInt();



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
    static void viewDeliveries()
    {
        System.out.println("enter store number");
        int store_num = reader.nextInt();

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


    public static void sendDelivery(){
        System.out.println("Delivery can be added to this week only");
        System.out.println("Please enter date, of a day in this week, for example : 1.5 (it's a random date)");
        String date = reader.nextLine();

        System.out.println("Please enter hour in format of: 23:40 (4 digits) ..");
        String hour = reader.nextLine();
        while (!isCorrectHour(hour))
        {
            System.out.println("illegal hour, insert again");
            hour = reader.nextLine();
        }
        System.out.println("Please enter *return* hour in format of: 23:40 (4 digits) ..");
        String returnHour = reader.nextLine();
        while (!isCorrectHour(returnHour))
        {
            System.out.println("illegal hour, insert again");
            returnHour = reader.nextLine();
        }


        System.out.println("Please enter truck id:");
        String truckid = reader.nextLine();

        System.out.println("Please enter the name of the driver:");
        String driver = reader.nextLine();

        System.out.println("Please enter the source of the delivery:");
        String src = reader.nextLine();

        System.out.println("Please enter the total weight of the stuff you want to deliver:");
        int weight = Integer.parseInt(reader.nextLine());
        boolean stop = false;
        List<String> numberlst = new LinkedList<>();
        HashMap<String, String> destlst = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> productlst = new HashMap<>();
        while (true) {

            System.out.println("Please enter the number of the file or \"r\" to stop entering files");
            String number = reader.nextLine();
            if (number.equals("r")) {

            }
            numberlst.add(number);
            System.out.println("Please enter address of a destination for that file:");
            String dest = reader.nextLine();
            destlst.put(number, dest);
            productlst.put(number, new HashMap<String, Integer>());
            while (true) {
                System.out.println("Please enter name of product or \"r\" to stop entering products");
                String product = reader.nextLine();
                if (product.equals("r")) {

                }

                System.out.println("Please enter quantity of that product");
                int quantity = Integer.parseInt(reader.nextLine());
                productlst.get(number).put(product, quantity);

            }
        }
        System.out.println("Enter Store Num");
        int store_num = reader.nextInt();
        System.out.println(ManagerController.getInstance().addDelivery(store_num,date,hour,truckid,driver,src,weight,numberlst,destlst,productlst,returnHour));
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
    private static void employeeMenu(){
        System.out.println("Enter Employee ID:");
        int eID = reader.nextInt();
        EmployeeController employeeController=EmployeeController.getInstance();
        System.out.println("Enter Store Number:");
        int store_num = reader.nextInt();


        if (employeeController.connect(eID,store_num)) {
            //If you are a storage worker, you enter here the storage worker menu
            if (employeeController.isStorage())
            {
                System.out.println("hello storage employee " + employeeController.getActiveName() + "\nchoose operation:");
                int operationNum = 0;
                operationNum = reader.nextInt();
                reader.nextLine();
                print_Storage_employee_menu();
                while (operationNum != 3) {



                }
            }



            //menu for normal employees(non storage)
            System.out.println("hello employee " + employeeController.getActiveName() + "\nchoose operation:");
            int operationNum = 0;
            while (operationNum != 3) {

                print_employee_menu();
                operationNum = reader.nextInt();
                reader.nextLine();
                switch (operationNum) {
                    case 1: {
                        if (!employeeController.hasActiveUserAssignedShifts()) {
                            print_shifts();
                            System.out.println("enter numbers of the capable shifts with ',' between each number, for example: ");
                            System.out.println("1,2,4,12");
                            String input = reader.nextLine();
                            String[] detailsStringArr = input.split(",");
                            int[] capableShifts = new int[detailsStringArr.length];
                            for (int i = 0; i < detailsStringArr.length; i++) {
                                capableShifts[i] = Integer.parseInt(detailsStringArr[i]);
                            }
                            System.out.println(employeeController.enterMyCapableShifts(capableShifts));
                        }
                        else {
                            System.out.println("you already assigned shifts");
                        }

                    }
                    case 2: {
                        System.out.println(employeeController.watchMyShifts());

                    }
                    case 3:
                    {
                        System.out.println("logging out");

                    }
                }
            }
        } else {
            System.out.println("there is no employee with that id \n");
        }

    }
    
    
    
}
