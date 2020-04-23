package com.company;

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
            String[] options = new String[]{"product types", "products", "discounts", "categories", "reports", "load dummy data"};
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
                default:
                    System.out.println("choose an option between 1 to 6");
            }
        }
    }
    public static void manageProductdTypes(){
        while(true) {
            System.out.println("Please select an option to perform on products types:");
            String[] options = new String[]{"product types", "products", "discounts", "categories", "reports", "load dummy data"};
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
                default:
                    System.out.println("choose an option between 1 to 6");
            }
        }
    }
    public static void manageProducts(){

    }
    public static void manageDiscounts(){

    }
    public static void manageCategories(){

    }
    public static void manageReports(){

    }
    public static void loadData(){

    }
    private static void printOptions(String[] array){
        int i = 1;
        for(String str: array)
            System.out.println(i++ + ") " + str);
    }
}
