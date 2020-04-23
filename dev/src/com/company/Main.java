package com.company;

import com.company.LogicLayer.*;
import com.company.PresentationLayer.*;
import com.sun.org.apache.bcel.internal.generic.RET;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {

    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        boolean dummyLoaded = false;
        while(true) {
            System.out.println("Please select a category to manage or operation to perform:");
            String[] options = new String[]{"product types", "products", "discounts", "categories", "reports", "load dummy data", "exit"};
            printOptions(options);
            int option = Integer.parseInt(reader.nextLine());
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
                    if(dummyLoaded){
                        System.out.println("dummy data already loaded");
                    }
                    else {
                        loadData();
                        dummyLoaded = true;
                    }
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
        System.out.println("please insert supplier price:");
        double supplierPrice = Double.parseDouble(reader.nextLine());
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
            Product product = new Product("storage", idToAssign, true, localDate, false, null);
            try {
                ProductInterface.addProduct(product, productId);
            } catch (Exception e) {
                System.out.println("error. " + e.getMessage());
            }
        }
    }

    private static void manageDiscounts(){
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
        double percentage = Double.parseDouble(reader.nextLine());
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
                report.setReportType(Report.reportType.Missings);
                ReportInterface.addMissingReport(report);
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
            ProductDetails appleProduct = new ProductDetails("3", "apple", "kfar maimon", 0.1, 0.05, null, 50);
            ProductDetails hotdogsProduct = new ProductDetails("4", "hotdogs", "zoglobek", 31, 24, null, 4);
            ProductDetails chocholateProduct = new ProductDetails("5", "chocolate", "elit", 5.5, 4, null, 10);

            ProductDetailsInterface.addProductDetails(milkProduct, "5");
            ProductDetailsInterface.addProductDetails(daniProduct, "4");
            ProductDetailsInterface.addProductDetails(appleProduct, "3");
            ProductDetailsInterface.addProductDetails(hotdogsProduct, "2");
            ProductDetailsInterface.addProductDetails(chocholateProduct, "4");

            for(int i = 1;i<=9;i++){
                String storage = i%2 == 0 ? "storage" : "cash register";
                Product product = new Product(storage, "milk"+i, i%2 == 0, LocalDate.now().plusDays(7), i != 5, null);
                ProductInterface.addProduct(product, "1");
            }
            for(int i = 1;i<=10;i++){
                String storage = i%3 == 0 ? "refridgerators" : "storage, shelve 1";
                Product product = new Product(storage, "dani"+i, i%3 != 0, LocalDate.now().plusDays(14), i % 4 == 0, null);
                ProductInterface.addProduct(product, "2");
            }
            for(int i = 1;i <= 20;i++){
                String storage = i%6 == 0? "fruit department" : "storage";
                Product product = new Product(storage, "apple"+i, i%6 != 0, LocalDate.now().plusDays(6), false, null);
                ProductInterface.addProduct(product, "3");
            }
            for(int i = 1;i <= 5;i++){
                Product product = new Product("refridgerators", "hotdogs"+i, false, LocalDate.now().plusDays(15), i != 5, null);
                ProductInterface.addProduct(product, "4");
            }

            Discount fruitsMealsDiscount = new Discount(10, LocalDate.now().plusDays(1), LocalDate.now().plusDays(15));
            List<String> firstDiscountCatIds = new ArrayList<>();
            firstDiscountCatIds.add("2");
            firstDiscountCatIds.add("3");
            Discount milkDiscount = new Discount(20, LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));
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
