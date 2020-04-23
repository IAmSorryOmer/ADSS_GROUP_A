package com.company;

import com.company.LogicLayer.Product;
import com.company.LogicLayer.ProductDetails;
import com.company.LogicLayer.ProductDetailsController;
import com.company.PresentationLayer.CategoryInterface;
import com.company.PresentationLayer.ProductDetailsInterface;
import com.company.PresentationLayer.ProductInterface;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static Scanner reader = new Scanner(System.in);
    public static void main(String[] args) {
        /*Discount discount1 = new Discount(80, null, null);
        Discount discount2 = new Discount(95.5, null, null);
        Discount discount3 = new Discount(90, null, null);
        Discount discount4 = new Discount(70, null, null);
        List<Discount> discounts = new LinkedList<>();
        discounts.add(discount1);
        discounts.add(discount2);
        discounts.add(discount3);
        discounts.add(discount4);
        double max = discounts.stream()
                .reduce(0.0, (accumulatedDouble, discount) -> Math.max(accumulatedDouble, discount.getPercentage()), Math::max);
        System.out.println(max);*/
        loop();
    }
    public static void loop(){
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
    public static void manageProductdTypes(){
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
                    String id = reader.next();
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
                    String name = reader.next();
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
    public static void addProductDetailsFromUser(){
        System.out.println("please insert id:");
        String id = reader.next();
        System.out.println("please insert name:");
        String name = reader.next();
        System.out.println("please insert manufacturer:");
        String manufacturer = reader.next();
        System.out.println("please insert retail price:");
        double retailPrice = reader.nextDouble();
        System.out.println("please insert supplier price:");
        double supplierPrice = reader.nextDouble();
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = reader.nextInt();
        //TODO category
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print): ");
        String catId = reader.next();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.next();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, supplierPrice, null, minimumQuantity);
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    public static void manageProducts(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"add product", "prints all products of type", "print all damaged products", "mark product as damaged", "print all products", "return to main"};
            printOptions(options);
            int option = reader.nextInt();
            switch(option){
                case 1:
                    addProductFromUser();
                    break;
                case 2:
                    printNumberedList(ProductDetailsInterface.getAllMissing());
                    break;
                case 3:
                    System.out.println("please insert product type id:");
                    String id = reader.next();
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
                    String name = reader.next();
                    printNumberedList(ProductDetailsInterface.getProductDetailsByName(name));
                    break;
                case 5:
                    printNumberedList(ProductDetailsInterface.getProductDetailsByStock());
                    break;
                case 6:
                    return;
                default:
                    System.out.println("choose an option between 1 to 6");
            }
        }
    }
    public static void addProductFromUser(){
        System.out.println("please insert id:");
        String id = reader.next();
        System.out.println("please insert expiration date(format in YYYY-MM-DD):");
        String date = reader.next();
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("please insert the id of the product type which this product is a type of(to print all the products types insert @print): ");
        String productId = reader.next();
        if(productId.equals("@print")){
            String stringifiedTypes = ProductDetailsInterface.stringifyProduct();
            System.out.println(stringifiedTypes);
            System.out.println("now insert the id:");
            productId = reader.next();
        }
        Product product = new Product("storag", id, true, localDate, false, null);
        try {
            ProductInterface.addProduct(product, productId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    public static void manageDiscounts(){

    }
    public static void addDiscountFromUser(){
        System.out.println("please insert id:");
        String id = reader.next();
        System.out.println("please insert name:");
        String name = reader.next();
        System.out.println("please insert manufacturer:");
        String manufacturer = reader.next();
        System.out.println("please insert retail price:");
        double retailPrice = reader.nextDouble();
        System.out.println("please insert supplier price:");
        double supplierPrice = reader.nextDouble();
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = reader.nextInt();
        //TODO category
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print): ");
        String catId = reader.next();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.next();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, supplierPrice, null, minimumQuantity);
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    public static void manageCategories(){

    }
    public static void addCategoryFromUser(){
        System.out.println("please insert id:");
        String id = reader.next();
        System.out.println("please insert name:");
        String name = reader.next();
        System.out.println("please insert manufacturer:");
        String manufacturer = reader.next();
        System.out.println("please insert retail price:");
        double retailPrice = reader.nextDouble();
        System.out.println("please insert supplier price:");
        double supplierPrice = reader.nextDouble();
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = reader.nextInt();
        //TODO category
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print): ");
        String catId = reader.next();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.next();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, supplierPrice, null, minimumQuantity);
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    public static void manageReports(){

    }
    public static void addReportFromUser(){
        System.out.println("please insert id:");
        String id = reader.next();
        System.out.println("please insert name:");
        String name = reader.next();
        System.out.println("please insert manufacturer:");
        String manufacturer = reader.next();
        System.out.println("please insert retail price:");
        double retailPrice = reader.nextDouble();
        System.out.println("please insert supplier price:");
        double supplierPrice = reader.nextDouble();
        System.out.println("please insert minimum quantity(-1 means no minimum quantity):");
        int minimumQuantity = reader.nextInt();
        //TODO category
        System.out.println("please insert the id of the category of the product(to print all the categories insert @print): ");
        String catId = reader.next();
        if(catId.equals("@print")){
            String stringifiedCategories = CategoryInterface.stringifyCategories();
            System.out.println(stringifiedCategories);
            System.out.println("now insert the id:");
            catId = reader.next();
        }
        ProductDetails productDetails = new ProductDetails(id, name, manufacturer, retailPrice, supplierPrice, null, minimumQuantity);
        try {
            ProductDetailsInterface.addProductDetails(productDetails, catId);
        } catch (Exception e) {
            System.out.println("error. " + e.getMessage());
        }
    }
    public static void loadData(){

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
