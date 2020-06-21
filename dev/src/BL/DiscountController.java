package BL;

import com.company.DataAccessLayer.DiscountDAL;
import com.company.Entities.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DiscountController {

    public static void addDiscount(Discount discount, List<String> productsIds, List<String> categoriesIds) throws Exception{
        if (getDiscountById(discount.getId()) != null){
            throw new Exception("A discount with id " + discount.getId() + " already exists");
        }
        DiscountDAL.insertDiscount(discount);
        if(productsIds != null) {
            for (String productId : productsIds) {
                ProductDetails productDetails = ProductDetailsController.getProductDetailsById(productId);
                if (productDetails == null){
                    throw new IllegalArgumentException("there is no type with id " + productId);
                }
                DiscountDAL.insertDiscountRelation(discount, productDetails);
            }
        }
        if(categoriesIds != null) {
            for (String categoryId : categoriesIds) {
                Category category = CategoryController.getCategoryByID(categoryId);
                if (category == null){
                    throw new IllegalArgumentException("there is no category with id " + categoryId);
                }
                DiscountDAL.insertDiscountRelation(discount, category);
            }
        }
    }

    private static Discount getDiscountById(String id){
        return DiscountDAL.getDiscountById(id);
    }
    public static String getDiscountableDiscounts(String id, boolean productDetails, boolean retail) throws Exception{
        Discountable discountable = productDetails ? ProductDetailsController.getProductDetailsById(id) : CategoryController.getCategoryByID(id);
        if(discountable == null){
            throw new IllegalArgumentException("there is no discountable with that id");
        }
        List<Discount> discounts = getDiscountableDiscounts(discountable, productDetails);
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
    public static List<Discount> getDiscountableDiscounts(Discountable discountable, boolean isProductDetails){
        return DiscountDAL.getDisccountableDiscounts(discountable, isProductDetails);
    }

    private static List<Discount> getAllProductDiscounts(ProductDetails product, boolean retail) throws Exception{
        List<Discount> discounts = getDiscountableDiscounts(product, true);
        Discountable discountable = product.getCategory();
        while(discountable != null){
            List<Discount> toAdd = getDiscountableDiscounts(discountable, false);
            discounts.addAll(toAdd);
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
        DiscountDAL.editItem(discount);
    }

    public static void removeDiscount(Discount discount){
        //TODO maybe delete
    }
}
