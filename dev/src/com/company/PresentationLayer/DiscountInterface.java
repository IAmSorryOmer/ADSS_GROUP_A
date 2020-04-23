package com.company.PresentationLayer;

import com.company.LogicLayer.Discount;
import com.company.LogicLayer.Discountable;
import com.company.LogicLayer.ProductDetails;

import java.time.LocalDate;
import java.util.List;

public class DiscountInterface {
    public static void addDiscount(Discount discount, List<Discountable> discountables, boolean retail){
        DiscountInterface.addDiscount(discount, discountables, retail);
    }
    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean retail){
        return DiscountInterface.getDiscountableDiscounts(discountable, retail);
    }
    private static List<Discount> getAllProductDiscounts(ProductDetails product, boolean retail){
        return DiscountInterface.getAllProductDiscounts(product, retail);
    }
    public static double getProductDiscountPercentage(ProductDetails product, boolean retail){
        return DiscountInterface.getProductDiscountPercentage(product, retail);
    }

    public static List<Double> getProductPricingHistory(ProductDetails product, boolean retail){
        return DiscountInterface.getProductPricingHistory(product, retail);
    }

    public static void editDiscount(Discount discount, LocalDate fromDate, LocalDate toDate, double percantage){
        DiscountInterface.editDiscount(discount, fromDate, toDate, percantage);
    }

    public static void removeDiscount(Discount discount){
        DiscountInterface.removeDiscount(discount);
    }
}
