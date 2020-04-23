package com.company.PresentationLayer;

import com.company.LogicLayer.*;

import java.time.LocalDate;
import java.util.List;

public class DiscountInterface {
    public static void addDiscount(Discount discount, List<String> productsIds, List<String> categoriesIds, boolean retail) throws Exception{
        DiscountController.addDiscount(discount, productsIds, categoriesIds, retail);
    }
    public static List<Discount> getDiscountableDiscounts(String id, boolean productDetails, boolean retail) throws Exception{
        return DiscountController.getDiscountableDiscounts(id, productDetails, retail);
    }

    public static double getProductDiscountPercentage(String id, boolean retail) throws Exception{
        return DiscountController.getProductDiscountPercentage(id, retail);
    }

    public static List<Double> getProductPricingHistory(String id, boolean retail) throws Exception{
        return DiscountController.getProductPricingHistory(id, retail);
    }
}
