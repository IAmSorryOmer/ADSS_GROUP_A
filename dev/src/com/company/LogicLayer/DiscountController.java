package com.company.LogicLayer;

import com.company.Entities.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DiscountController {

    private static List<Discount> discounts = new ArrayList<>();;
    private static HashMap<Discountable, List<Discount>> retailDiscounts = new HashMap<>();
    private static HashMap<Discountable, List<Discount>> supplierDiscounts = new HashMap<>();
    public static void addDiscount(Discount discount, List<String> productsIds, List<String> categoriesIds, boolean retail) throws Exception{
        if (getDiscountById(discount.getId()) != null){
            throw new Exception("A discount with id " + discount.getId() + " already exists");
        }
        discounts.add(discount);
        HashMap<Discountable, List<Discount>> map = retail? retailDiscounts:supplierDiscounts;
        if(productsIds != null) {
            for (String productId : productsIds) {
                ProductDetails productDetails = ProductDetailsController.getProductDetailsById(productId);
                if (productDetails == null){
                    throw new IllegalArgumentException("there is no type with id " + productId);
                }
                map.putIfAbsent(productDetails, new ArrayList<>());
                map.get(productDetails).add(discount);
            }
        }
        if(categoriesIds != null) {
            for (String categoryId : categoriesIds) {
                Category category = CategoryController.getCategoryByID(categoryId);
                if (category == null){
                    throw new IllegalArgumentException("there is no category with id " + categoryId);
                }
                map.putIfAbsent(category, new ArrayList<>());
                map.get(category).add(discount);
            }
        }
    }
    private static Discount getDiscountById(String id){
        for(Discount discount: discounts){
            if (discount.getId().equals(id)){
                return discount;
            }
        }
        return null;
    }
    public static String getDiscountableDiscounts(String id, boolean productDetails, boolean retail) throws Exception{
        Discountable discountable = productDetails ? ProductDetailsController.getProductDetailsById(id) : CategoryController.getCategoryByID(id);
        if(discountable == null){
            throw new IllegalArgumentException("there is no discountable with that id");
        }
        List<Discount> discounts = getDiscountableDiscounts(discountable, retail);
        StringBuilder toReturn = new StringBuilder();
        if(productDetails){
            toReturn.append("Original price: ").append(((ProductDetails)discountable).getRetailPrice()).append("\n");
        }
        for(Discount discount: discounts){
            toReturn.append("a discount of: ").append(discount.getPercentage()).append(" from date: ").append(discount.getFromDate().toString()).
                    append(" to date: ").append(discount.getToDate().toString()).append("\n");
        }
        return toReturn.toString();
    }
    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean retail){
        return retail? retailDiscounts.get(discountable):supplierDiscounts.get(discountable);
    }

    private static List<Discount> getAllProductDiscounts(ProductDetails product, boolean retail) throws Exception{
        List<Discount> discounts = getDiscountableDiscounts(product, retail);
        if(discounts == null){
            discounts = new ArrayList<>();
        }
        Discountable discountable = product.getCategory();
        while(discountable != null){
            List<Discount> toAdd = getDiscountableDiscounts(discountable, retail);
            if(toAdd != null) {
                discounts.addAll(toAdd);
            }
            discountable = discountable.getParent();
        }
        return discounts;
    }

    public static double getProductDiscountPercentage(String id, boolean retail) throws Exception{
        ProductDetails product = ProductDetailsController.getProductDetailsById(id);
        if(product == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        List<Discount> discounts = getAllProductDiscounts(product, retail);
        double max = discounts.stream()
                .reduce(0.0, (accumulatedDouble, discount) -> {
                    if(LocalDate.now().compareTo(discount.getFromDate()) >= 0 && LocalDate.now().compareTo(discount.getToDate()) <= 0) {
                        return Math.max(accumulatedDouble, discount.getPercentage());
                    }
                    return accumulatedDouble;
                }
                , Math::max);
        return max;
    }

    public static List<Double> getProductPricingHistory(String id, boolean retail) throws Exception{
        ProductDetails product = ProductDetailsController.getProductDetailsById(id);
        if(product == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        List<Discount> discounts = getAllProductDiscounts(product, retail);
        discounts.sort(Comparator.comparing(Discount::getFromDate));
        List<Double> priceHistory = discounts.stream().map(discount -> product.getRetailPrice()* (1-discount.getPercentage()/100)).collect(Collectors.toList());
        return priceHistory;
    }

    public static void editDiscount(Discount discount, LocalDate fromDate, LocalDate toDate, double percantage){
        discount.setFromDate(fromDate);
        discount.setToDate(toDate);
        discount.setPercentage(percantage);
    }

    public static void removeDiscount(Discount discount){
        discounts.remove(discount);
        for(Map.Entry<Discountable, List<Discount>> entry : retailDiscounts.entrySet()){
            entry.getValue().remove(discount);
        }
        for(Map.Entry<Discountable, List<Discount>> entry : supplierDiscounts.entrySet()){
            entry.getValue().remove(discount);
        }
    }
}
