package com.company.LogicLayer;

import javax.security.auth.callback.Callback;
import java.lang.invoke.CallSite;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DiscountController {

    private static List<Discount> discounts = new ArrayList<>();;
    private static HashMap<Discountable, List<Discount>> retailDiscounts;
    private static HashMap<Discountable, List<Discount>> supplierDiscounts;
    public static void addDiscount(Discount discount, List<String> productsIds, List<String> categoriesIds, boolean retail) throws Exception{
        discounts.add(discount);
        HashMap<Discountable, List<Discount>> map = retail? retailDiscounts:supplierDiscounts;
        for(String productId : productsIds){
            ProductDetails productDetails = ProductDetailsController.getProductDetailsById(productId);
            map.putIfAbsent(productDetails, new ArrayList<>());
            map.get(productDetails).add(discount);
        }
        for(String categoryId:categoriesIds){
            Category category = CategoryController.getCategoryByID(categoryId);
            map.putIfAbsent(category, new ArrayList<>());
            map.get(category).add(discount);
        }
    }

    public static List<Discount> getDiscountableDiscounts(String id, boolean productDetails, boolean retail) throws Exception{
        Discountable discountable = productDetails ? ProductDetailsController.getProductDetailsById(id) : CategoryController.getCategoryByID(id);
        if(discountable == null){
            throw new IllegalArgumentException("there is no discountable with that id");
        }
        return getDiscountableDiscounts(discountable, retail);
    }
    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean retail){
        return retail? retailDiscounts.get(discountable):supplierDiscounts.get(discountable);
    }

    private static List<Discount> getAllProductDiscounts(ProductDetails product, boolean retail) throws Exception{
        List<Discount> discounts = getDiscountableDiscounts(product, retail);
        Discountable discountable = product.getCategory();
        while(discountable != null){
            discounts.addAll(getDiscountableDiscounts(product, retail));
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
        //TODO check
        List<Double> priceHistory = discounts.stream().map(discount -> (retail?product.getRetailPrice():product.getSupplierPrice())* (1-discount.getPercentage()/100)).collect(Collectors.toList());
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
