package com.company;

import com.company.DataAccessLayer.CategoryDAL;
import com.company.DataAccessLayer.DBHandler;
import com.company.Entities.*;
import com.company.PresentationLayer.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        DBHandler.connect();
        boolean dummyLoaded = false;
        while(true) {
            System.out.println("Please select a category to manage or operation to perform:");
            String[] options = new String[]{"product types", "products", "discounts", "categories", "reports(including auto order missing items)", "orders", "providers and catalogs", "agreements", "load dummy data", "exit"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    manageProductTypesMenu();
                    break;
                case 2:
                    manageProductsMenu();
                    break;
                case 3:
                    manageDiscountsMenu();
                    break;
                case 4:
                    manageCategoriesMenu();
                    break;
                case 5:
                    manageReportsMenu();
                    break;
                case 6:
                    manageOrdersMenu();
                    break;
                case 7:
                    manageProvidersMenu();
                    break;
                case 8:
                    manageAgreementsMenu();
                    break;
                case 9:
                    if(dummyLoaded){
                        System.out.println("dummy data already loaded");
                    }
                    else {
                        loadData();
                        dummyLoaded = true;
                    }
                    break;
                case 10:
                    return;
                default:
                    System.out.println("choose an option between 1 to 10");
            }
        }
    }

    private static void manageProductTypesMenu(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"add type", "print all missings products", "modify minimum quantity", "print all products with name", "print all products within storage","print all products", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    addProductDetailsFromUser();
                    break;
                case 2:
                    printNumberedList(ProductDetailsInterface.getAllMissing());
                    break;
                case 3:
                    System.out.println("please insert product type id:");
                    String id = reader.nextLine();
                    System.out.println("please insert new minimum quantity");
                    int newQuantity = Integer.parseInt(reader.nextLine());
                    try {
                        ProductDetailsInterface.changeMinimalQuantity(id, newQuantity);
                    }
                    catch (Exception e){
                        System.out.println("quantity must be greater or equal to -1(wich means no minimum quantity at all)");
                    }
                    break;
                case 4:
                    System.out.println("please insert name:");
                    String name = reader.nextLine();
                    printNumberedList(ProductDetailsInterface.getProductDetailsByName(name));
                    break;
                case 5:
                    printNumberedList(ProductDetailsInterface.getProductDetailsByStock());
                    break;
                case 6:
                    printNumberedList(ProductDetailsInterface.getAllProducts());
                    break;
                case 7:
                    return;
                default:
                    System.out.println("choose an option between 1 to 7");
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
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageProductsMenu(){
        while(true) {
            System.out.println("Please select an option to perform on products:");
            String[] options = new String[]{"add product", "move product", "prints all products of type", "print all damaged products", "mark product as damaged", "print all products", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    addProductFromUser();
                    break;
                case 2:
                    moveProduct();
                    break;
                case 3:
                    printAllProductsOfType();
                    break;
                case 4:
                    printNumberedList(ProductInterface.getAllDamaged());
                    break;
                case 5:
                    System.out.println("please insert product id:");
                    try {
                        ProductInterface.markAsDamaged(reader.nextLine());
                    } catch (Exception e) {
                        System.out.println("error. " + e.getMessage());
                    }
                    break;
                case 6:
                    System.out.println(ProductInterface.stringifyProducts());
                    break;
                case 7:
                    return;
                default:
                    System.out.println("choose an option between 1 to 7");
            }
        }
    }
    private static void printAllProductsOfType() {
        System.out.println("please insert the id of the product type(to print all the products types insert @print):");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        try {
            printNumberedList(ProductInterface.getProductsByType(productId));
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void moveProduct() {
        System.out.println("please insert the id of the product to move:");
        String id = reader.nextLine();
        System.out.println("please insert the new location:");
        String newLocation = reader.nextLine();
        System.out.println("is the new location in the storage?(y for yes, else no):");
        String ans = reader.nextLine();
        try {
            ProductInterface.moveProduct(id, newLocation, ans.equals("y"));
        } catch (IllegalArgumentException e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void addProductFromUser(){
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
            Product product = new Product("storage", idToAssign, true, false, null);
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
            String[] options = new String[]{"add discount", "print discounts of certain type or category", "print current discount percentage of certain type","print pricing history of certain type",  "return to main"};
            printOptions(options);
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
        try {
            System.out.println(DiscountInterface.getDiscountableDiscounts(productId, ans.equals("y"), ans2.equals("y")));
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
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
        try {
            System.out.println("the current discount percentage is: " + DiscountInterface.getProductDiscountPercentage(productId, ans.equals("y")));
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
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
        try {
            System.out.println("the pricing history is: " + DiscountInterface.getProductPricingHistory(productId, ans.equals("y")));
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
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
        try {
            DiscountInterface.addDiscount(discount, productsIds, categoriesIds, retail);
            System.out.println("Done.");
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageCategoriesMenu(){
        while(true) {
            System.out.println("Please select an option to perform on categories:");
            String[] options = new String[]{"add category", "print all categories", "return to main"};
            printOptions(options);
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
        try {
            CategoryInterface.addCategory(category, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }

    }

    private static void manageReportsMenu(){
        while(true) {
            System.out.println("Please select an option to perform on reports:");
            String[] options = new String[]{"add report", "print all reports", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    addReportFromUser();
                    break;
                case 2:
                    System.out.println(ReportInterface.stringifyReports());
                    break;
                case 3:
                    return;
                default:
                    System.out.println("choose an option between 1 to 3");
            }
        }
    }
    private static void addReportFromUser(){
        System.out.println("please insert report id:");
        String id = reader.nextLine();
        System.out.println("please insert employee id:");
        String employeeId = reader.nextLine();
        System.out.println("please insert description:");
        String description = reader.nextLine();
        System.out.println("please select a type of report:");
        Report report = new Report(Report.reportType.Inventory, id, employeeId, description, null);
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
        try {
            ReportInterface.addInventoryReport(categoriesIds, report);
        }
        catch (Exception e){
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageOrdersMenu(){
        while(true) {
            System.out.println("Please select an option to perform on orders of the selected provider: ");
            String[] options = new String[]{"manage provider orders", "print all orders", "print all automatic orders", "return to main"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    manageProviderOrdersMenu();
                    break;
                case 2:
                    printNumberedList(OrdersInterface.getAllOrders());
                    break;
                case 3:
                    printNumberedList(OrdersInterface.getAllAutomaticsOrders());
                    break;
                case 4:
                    return;
                default:
                    System.out.println("choose an option between 1 to 4");
            }
        }

    }
    private static void manageProviderOrdersMenu(){
        String message = "please insert the id of the provider that you want to manage his orders";
        String providerId = selectProvider(message);
        while(true) {
            System.out.println("Please select an option to perform on orders of the selected provider: ");
            String[] options = new String[]{"add order", "add automatic order", "manage order items", "print all orders of provider", "print expanded order(with items)", "select another provider to manage", "return to order manage menu"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
            switch(option){
                case 1:
                    addOrderFromUser(providerId);
                    break;
                case 2:
                    addAutomaticOrderFromUser(providerId);
                    break;
                case 3:
                    manageOrderItemsMenu(providerId);
                    break;
                case 4:
                    printNumberedList(OrdersInterface.getAllProviderOrders(providerId));
                    break;
                case 5:
                    printSpecificOrder(providerId);
                    break;
                case 6:
                    providerId = selectProvider(message);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("choose an option between 1 to 7");
            }
        }
    }

    private static void addOrderFromUser(String providerId){
        System.out.println("please insert the order id: ");
        String orderId = reader.nextLine();
        System.out.println("please insert the order date: ");
        String orderDateStr = reader.nextLine();
        LocalDate orderDate = LocalDate.parse(orderDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        SingleProviderOrder singleProviderOrder = new SingleProviderOrder(null, orderId, orderDate);
        try {
            OrdersInterface.SingleProviderOrderCreator(singleProviderOrder, providerId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void addAutomaticOrderFromUser(String providerId){
        System.out.println("please insert the order id: ");
        String orderId = reader.nextLine();
        System.out.println("please insert the interval between orders(in days):");
        int orderDays = -1;
        System.out.println("please insert the order days(when to auto create the order) as binary string.\nfor example if an order is to be sent every thursday, insert 0000100):");
        while (orderDays == -1) {
            String orderDaysString = reader.nextLine();
            if (orderDaysString.length() != 7) {
                System.out.println("the string size should be seven(a digit for each day)");
                continue;
            }
            try {
                orderDays = Integer.parseInt(orderDaysString, 2);
                if(orderDays == 0 ){
                    System.out.println("you cant add order which never get ordered. please select at least one day");
                    orderDays = -1;
                }
            } catch (Exception e) {
                System.out.println("you inserted invalid binary string. try again:");
            }
        }
        AutomaticOrder automaticOrder = new AutomaticOrder(null, orderId, orderDays);
        try {
            OrdersInterface.SingleProviderOrderCreator(automaticOrder, providerId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void printSpecificOrder(String providerId){
        System.out.println("please insert the order id(to print all the orders of the provider insert @print): ");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            try {
                printNumberedList(OrdersInterface.getAllProviderOrders(providerId));
                System.out.println("now insert the order id: ");
                orderId = reader.nextLine();
            }
            catch (Exception e){
                System.out.println("error. " + e.getMessage());
                return;
            }
        }
        System.out.println(OrdersInterface.printOrderExpanded(providerId, orderId));
    }

    private static void manageOrderItemsMenu(String providerId){
        String orderId = null;
        try {
            orderId = selectOrder(providerId);
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
            switch(option){
                case 1:
                    addItemToOrder(providerId, orderId);
                    break;
                case 2:
                    editItemOfOrder(providerId, orderId);
                    break;
                case 3:
                    removeItemFromOrder(providerId, orderId);
                    break;
                case 4:
                    try {
                        orderId = selectOrder(providerId);
                    }
                    catch (Exception e){
                        System.out.println("error. " + e.getMessage());
                        return;
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("choose an option between 1 to 5");
            }
        }
    }
    private static void addItemToOrder(String providerId, String orderId){
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
        try {
            OrdersInterface.AddToOrder(providerId, orderId, catalogItemId, orderAmount);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void editItemOfOrder(String providerId, String orderId){
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
        try {
            OrdersInterface.EditOrder(providerId, orderId, catalogItemId, orderAmount);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    private static void removeItemFromOrder(String providerId, String orderId){
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
            OrdersInterface.RemoveFromOrder(providerId, orderId, catalogItemId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }


    private static void manageProvidersMenu(){
        while(true) {
            System.out.println("Please select an option to perform on providers:");
            String[] options = new String[]{"add provider", "edit provider details", "manage provider catalog", "print all providers", "return to main"};
            printOptions(options);
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
        System.out.println("is the provider comes in fixed days?(y for yes, else no):");
        boolean fixedDays = reader.nextLine().equals("y");
        int arrivalDays = -1;
        int delayDays = 0;
        if(fixedDays) {
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
        }
        else{
            System.out.println("insert the number of days until the provider provides:");
            delayDays = Integer.parseInt(reader.nextLine());
        }
        System.out.println("please insert a phone to contact with the provider:");
        String phoneNumber = reader.nextLine();
        System.out.println("please insert the adress of the provider:");
        String adress = reader.nextLine();
        Provider provider = new Provider(providerId, creditCardNumber, needTransport, delayDays, arrivalDays, providerName, null);
        CommunicationDetails communicationDetails = new CommunicationDetails(provider, fixedDays, phoneNumber, adress, null);
        communicationDetails.setUpdated(true);
        provider.setCommunicationDetails(communicationDetails);
        try {
            ProviderInterface.ProviderCreator(provider, communicationDetails);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
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
        Integer arrivalDays = -1;
        if(fixedDays != null && fixedDays) {
            System.out.println("please insert the new arrival days of the provider as binary string, or @none for keep it unchanged.\nfor example if a provider comes in sunday, monday and friday insert 0100011):");
            while (arrivalDays == -1) {
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
        try {
            ProviderInterface.editDetails(providerId, needTransport, null, arrivalDays, providerName, fixedDays, phoneNumber, address);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
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
        Double discount = discountStr.equals("@none") ? null : Double.parseDouble(minAmountStr);
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


    private static void loadData(){
        try {
            Category dairyCategory = new Category("1", "dairy products");
            Category meatCategory = new Category("2", "meat products");
            Category fruitsCategory = new Category("3", "fruits");
            Category milkCategory = new Category("4", "milk products");
            Category mediumSize = new Category("5", "1-1.5 litters");

            CategoryInterface.addCategory(dairyCategory, null);
            CategoryInterface.addCategory(meatCategory, null);
            CategoryInterface.addCategory(fruitsCategory, null);
            CategoryInterface.addCategory(milkCategory, "1");
            CategoryInterface.addCategory(mediumSize, "4");

            ProductDetails milkProduct = new ProductDetails("1", "milk 5%", "yotvata", 3.5, 3, null, 5);
            ProductDetails daniProduct = new ProductDetails("2", "dani", "yople", 5, 4, null, 15);
            ProductDetails appleProduct = new ProductDetails("3", "apple", "kfar maimon", 0.1, 6, null, 50);
            ProductDetails hotdogsProduct = new ProductDetails("4", "hotdogs", "zoglobek", 31, 7, null, 4);
            ProductDetails chocholateProduct = new ProductDetails("5", "chocolate", "elit", 5.5, 9, null, 10);

            ProductDetailsInterface.addProductDetails(milkProduct, "5");
            ProductDetailsInterface.addProductDetails(daniProduct, "4");
            ProductDetailsInterface.addProductDetails(appleProduct, "3");
            ProductDetailsInterface.addProductDetails(hotdogsProduct, "2");
            ProductDetailsInterface.addProductDetails(chocholateProduct, "4");

            for(int i = 1;i<=9;i++){
                String storage = i%2 == 0 ? "storage" : "cash register";
                Product product = new Product(storage, "milk"+i, i%2 == 0, i != 5, null);
                ProductInterface.addProduct(product, "1");
            }
            for(int i = 1;i<=10;i++){
                String storage = i%3 == 0 ? "refridgerators" : "storage, shelve 1";
                Product product = new Product(storage, "dani"+i, i%3 != 0, i % 4 == 0, null);
                ProductInterface.addProduct(product, "2");
            }
            for(int i = 1;i <= 20;i++){
                String storage = i%6 == 0? "fruit department" : "storage";
                Product product = new Product(storage, "apple"+i, i%6 != 0,  false, null);
                ProductInterface.addProduct(product, "3");
            }
            for(int i = 1;i <= 5;i++){
                Product product = new Product("refridgerators", "hotdogs"+i, false, i != 5, null);
                ProductInterface.addProduct(product, "4");
            }

            Discount fruitsMealsDiscount = new Discount("discount1",10, LocalDate.now().plusDays(1), LocalDate.now().plusDays(15));
            List<String> firstDiscountCatIds = new ArrayList<>();
            firstDiscountCatIds.add("2");
            firstDiscountCatIds.add("3");
            Discount milkDiscount = new Discount("discount2",20, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
            List<String> secondDiscountProductIds = new ArrayList<>();
            secondDiscountProductIds.add("1");
            DiscountInterface.addDiscount(fruitsMealsDiscount, new ArrayList<>(), firstDiscountCatIds, true);
            DiscountInterface.addDiscount(milkDiscount, secondDiscountProductIds, new ArrayList<>(), true);

            System.out.println("Dummy data loaded.");
            System.out.println("summary:");
            System.out.println("Category that were added:");
            System.out.println(CategoryInterface.stringifyCategories());
            System.out.println("Products types that were added:");
            System.out.println(ProductDetailsInterface.stringifyProduct());
            System.out.println("Products that were added:");
            System.out.println(ProductInterface.stringifyProducts());
            System.out.println("in addition, there is 2 discounts that were added.");
            System.out.println("the first one, of ten percents, from tomorrow and for two weeks, on the fruits and meat category");
            System.out.println("the second one, of twenty percents, from one more week, and for week, on the milk 5% product type");
            System.out.println("............");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String selectOrder(String providerId) throws Exception{
        System.out.println("please insert the id of the order that you want to manage her items(to print all the orders of provider insert @print): ");
        String orderId = reader.nextLine();
        if(orderId.equals("@print")){
            printNumberedList(OrdersInterface.getAllProviderOrders(providerId));
            System.out.println("now insert the id of the provider: ");
            providerId = reader.nextLine();
        }
        return providerId;
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
}
