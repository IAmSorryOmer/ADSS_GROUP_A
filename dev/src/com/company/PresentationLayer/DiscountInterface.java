package com.company.PresentationLayer;

import com.company.LogicLayer.Discount;
import com.company.LogicLayer.DiscountController;
import com.company.LogicLayer.Discountable;
import com.company.LogicLayer.ProductDetails;

import java.time.LocalDate;
import java.util.List;

public class DiscountInterface {
    public static void addDiscount(Discount discount, List<Discountable> discountables, boolean retail){
        DiscountController.addDiscount(discount, discountables, retail);
    }
    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean retail){
        return DiscountController.getDiscountableDiscounts(discountable, retail);
    }
    public static double getProductDiscountPercentage(ProductDetails product, boolean retail){
        return DiscountController.getProductDiscountPercentage(product, retail);
    }

    public static List<Double> getProductPricingHistory(ProductDetails product, boolean retail){
        return DiscountController.getProductPricingHistory(product, retail);
    }

    public static void editDiscount(Discount discount, LocalDate fromDate, LocalDate toDate, double percantage){
        DiscountController.editDiscount(discount, fromDate, toDate, percantage);
    }

    public static void removeDiscount(Discount discount){
        DiscountController.removeDiscount(discount);
    }
}
