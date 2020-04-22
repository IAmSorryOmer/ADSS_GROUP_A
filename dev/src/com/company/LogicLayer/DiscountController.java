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
    public static void addDiscount(Discount discount, List<Discountable> discountables, boolean retail){
        discounts.add(discount);
        HashMap<Discountable, List<Discount>> map = retail? retailDiscounts:supplierDiscounts;
        for(Discountable discountable:discountables){
            map.putIfAbsent(discountable, new ArrayList<>());
            map.get(discountable).add(discount);
        }
    }

    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean retail){
        return retail? retailDiscounts.get(discountable):supplierDiscounts.get(discountable);
    }


    private static List<Discount> getAllProductDiscounts(ProductDetails product, boolean retail){
        List<Discount> discounts = getDiscountableDiscounts(product, retail);
        Discountable discountable = product.getCategory();
        while(discountable != null){
            discounts.addAll(getDiscountableDiscounts(discountable, retail));
            discountable = discountable.getParent();
        }
        return discounts;
    }

    public static double getProductDiscount(ProductDetails product, boolean retail){
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

    public static List<Double> getPricingHistory(ProductDetails product, boolean retail){
        List<Discount> discounts = getAllProductDiscounts(product, retail);
        discounts.sort(Comparator.comparing(Discount::getFromDate));
        List<Double> priceHistory = discounts.stream().map(discount -> (retail?product.getRetailPrice():product.getSupplierPrice())* discount.getPercentage()/100).collect(Collectors.toList());
        return priceHistory;
    }

}
