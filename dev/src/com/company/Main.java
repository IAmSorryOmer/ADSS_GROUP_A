package com.company;

import com.company.LogicLayer.*;
import com.company.PresentationLayer.*;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        while(true) {
            System.out.println("Please select a category to manage or operation to perform:");
            String[] options = new String[]{"product types", "products", "discounts", "categories", "reports", "load dummy data", "exit"};
            printOptions(options);
            int option = reader.nextInt();
            switch(option){
                case 1:
                    manageProductdTypes();
                    break;
                case 2:
                    manageProducts();
                    break;
                case 3:
                    manageDiscounts();
                    break;
                case 4:
                    manageCategories();
                    break;
                case 5:
                    manageReports();
                    break;
                case 6:
                    loadData();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("choose an option between 1 to 7");
            }
        }
    }

    private static void manageProductdTypes(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"add type", "print all missings products", "modify minimum quantity", "print all products with name", "print all products within storage","print all products", "return to main"};
            printOptions(options);
            int option = reader.nextInt();
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
                    int newQuantity = reader.nextInt();
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
        double retailPrice = reader.nextDouble();
        System.out.println("please insert supplier price:");
        double supplierPrice = reader.nextDouble();
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = reader.nextInt();
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print):");
        String catId = reader.nextLine();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.nextLine();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, supplierPrice, null, minimumQuantity);
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageProducts(){
        while(true) {
            System.out.println("Please select an option to perform on products:");
            String[] options = new String[]{"add product", "move product", "prints all products of type", "print all damaged products", "mark product as damaged", "print all products", "return to main"};
            printOptions(options);
            int option = reader.nextInt();
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
                    System.out.println("choose an option between 1 to 6");
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
        System.out.println("please insert expiration date(format in YYYY-MM-DD):");
        String date = reader.nextLine();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("please insert the id of the product type which this product is a type of(to print all the products types insert @print): ");
        String productId = reader.nextLine();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.nextLine();
        }
        Product product = new Product("storage", id, true, localDate, false, null);
        try {
            ProductInterface.addProduct(product, productId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }

    private static void manageDiscounts(){
        while(true) {
            System.out.println("Please select an option to perform on discounts:");
            String[] options = new String[]{"add discount", "print discounts of certain type or category", "print current discount percentage of certain type","print pricing history of certain type",  "return to main"};
            printOptions(options);
            int option = reader.nextInt();
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
            printNumberedList(DiscountInterface.getDiscountableDiscounts(productId, ans.equals("y"), ans2.equals("y") ));
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
        System.out.println("please insert the percentage of the discount:");
        double percentage = reader.nextDouble();
        System.out.println("please insert the starting date of the discount(format like YYYY-MM-DD):");
        String startDateStr = reader.nextLine();
        LocalDate startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("please insert the ending date of the discount(format like YYYY-MM-DD):");
        String endDateStr = reader.nextLine();
        LocalDate endDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);

        Discount discount = new Discount(percentage, startDate, endDate);
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

    private static void manageCategories(){
        while(true) {
            System.out.println("Please select an option to perform on categories:");
            String[] options = new String[]{"add category", "print all categories", "return to main"};
            printOptions(options);
            int option = reader.nextInt();
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
        try {
            CategoryInterface.addCategory(category, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }

    }

    private static void manageReports(){
        while(true) {
            System.out.println("Please select an option to perform on reports:");
            String[] options = new String[]{"add report", "print all reports", "return to main"};
            printOptions(options);
            int option = reader.nextInt();
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
        int option = reader.nextInt();
        switch (option){
            case 1:
                createInventoryReportFromUser(report);
                break;
            case 2:
                report.setReportType(Report.reportType.Damaged);
                ReportInterface.addDamagedReport(report);
                break;
            case 3:
                report.setReportType(Report.reportType.Missings);
                ReportInterface.addMissingReport(report);
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
    private static void loadData(){

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
