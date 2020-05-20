package com.company.LogicLayer;
import com.company.DataAccessLayer.ProductDetailsDAL;
import com.company.Entities.Category;
import com.company.Entities.ProductDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsController {

    public static void addProductDetails(ProductDetails productDetails, String catId) throws Exception {
        if (getProductDetailsById(productDetails.getId()) != null){
            throw new Exception("A product type with id " + productDetails.getId() + " already exists");
        }
        Category category = CategoryController.getCategoryByID(catId);
        if(category == null){
            throw new IllegalArgumentException("there is no such category id");
        }
        productDetails.setCategory(category);
        ProductDetailsDAL.insertProductDetails(productDetails);
    }

    public static ProductDetails getProductDetailsById(String id){
        return ProductDetailsDAL.getProductDetailsById(id);
    }

    public static List<ProductDetails> getAllMissing() {
        return ProductDetailsDAL.getMissingProducts();
    }

    public static void changeMinimalQuantity(String id, int newQuantity) throws Exception {
        ProductDetails productDetails = getProductDetailsById(id);
        if(productDetails == null || newQuantity < -1){
            throw new Exception("A product with that ID does not exist or illegal quantity");
        }
        productDetails.setMinimumQuantity(newQuantity);
        ProductDetailsDAL.editProductDetails(productDetails);
    }

    public static List<ProductDetails> getProductDetailsByName(String name) {
        return ProductDetailsDAL.getProductDetailsByName(name);
    }

    public static List<ProductDetails> getProductDetailsByStock() {
        return ProductDetailsDAL.getProductDetailsInStock();
    }

    public static String GetProductsDetails(ProductDetails productDetails){
        return productDetails.toString() + "\nSupplier discounts: " + DiscountController.getDiscountableDiscounts(productDetails, false);
    }

    private static boolean isInCategory(ProductDetails productDetails, Category category){
        Category currCategory = productDetails.getCategory();
        while(currCategory != null){
            if(currCategory.getID().equals(category.getID())){
                return true;
            }
            currCategory = currCategory.getParent();
        }
        return false;
    }
    public static void updateQuantity(ProductDetails productDetails, int toAdd, boolean storage){
        if(storage){
            productDetails.setQuantityInStorage(productDetails.getQuantityInStorage() + toAdd);
        }
        else{
            productDetails.setQuantityInShelves(productDetails.getQuantityInShelves() + toAdd);
        }
        ProductDetailsDAL.editProductDetails(productDetails);
    }

    public static List<ProductDetails> getAllProductsOffCategory(List<String> categorieIds) throws Exception{
        List<Category> categories = new ArrayList<>();
        for(String categoryId : categorieIds){
            Category categoryToAdd = CategoryController.getCategoryByID(categoryId);
            if (categoryToAdd == null){
                throw new IllegalArgumentException("there isnt category with id " + categoryId + ".");
            }
            categories.add(categoryToAdd);
        }
        return getProductDetailsList().stream().filter(product -> categories.stream().anyMatch(cat -> isInCategory(product, cat))).collect(Collectors.toList());
    }
    public static List<ProductDetails> getProductDetailsList(){
        return ProductDetailsDAL.loadAll();
    }
}