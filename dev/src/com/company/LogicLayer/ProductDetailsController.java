package com.company.LogicLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsController {
    private static List<ProductDetails> productDetailsList = new ArrayList<>();

    public static void addProductDetails(ProductDetails productDetails) throws Exception {
        if (getProductDetailsById(productDetails.getId()) != null  || CategoryController.getCategoryByID(productDetails.getId()) != null){
            throw new Exception("A category or a product with that id already exists");
        }
        productDetailsList.add(productDetails);
    }

    public static ProductDetails getProductDetailsById(String id){
        for ( ProductDetails productDetails: productDetailsList){
            if (productDetails.getId().equals(id)){
                return  productDetails;
            }
        }
        return null;
    }

    public static boolean lowQuantityOfProduct(String id) throws Exception {
        ProductDetails productDetails = getProductDetailsById(id);
        if (productDetails == null){
            throw new Exception("A product with that ID does not exist");
        }
        return productDetails.getMinimumQuantity() >= (productDetails.getQuantityInStorage()+productDetails.getQuantityInShelves());
    }

    public static List<ProductDetails> returnAllMissing() {
        return productDetailsList.stream().filter(product -> product.getMinimumQuantity() >= (product.getQuantityInStorage()+product.getQuantityInShelves())).collect(Collectors.toList());
    }

    public static void changeMinimalQuantity(String id, int newQuantity) throws Exception {
        ProductDetails productDetails = getProductDetailsById(id);
        if (productDetails == null || newQuantity<-1){
            throw new Exception("A product with that ID does not exist or illegal quantity");
        }
        productDetails.setMinimumQuantity(newQuantity);
    }

    public static List<ProductDetails> getProductDetailsByName(String name) {
        return productDetailsList.stream().filter(product -> product.getName().equals(name)).collect(Collectors.toList());
    }

    public static List<ProductDetails> getProductDetailsByStock() {
        return productDetailsList.stream().filter(product -> product.getQuantityInShelves()> 0 || product.getQuantityInStorage()>0).collect(Collectors.toList());
    }

    public static String GetProductsDetails(ProductDetails productDetails){
        return productDetails.toString() + "\nSupplier discounts: " + DiscountController.getDiscountableDiscounts(productDetails, false);
    }
    public static boolean isInCategory(ProductDetails productDetails, Category category){
        Category currCategory = productDetails.getCategory();
        while(currCategory != null){
            if(currCategory.getID().equals(category.getID())){
                return true;
            }
            currCategory = currCategory.getParent();
        }
        return false;
    }
    public static List<ProductDetails> getAllProductsOffCategory(List<Category> categories){
        return productDetailsList.stream().filter(product -> categories.stream().anyMatch(cat -> isInCategory(product, cat))).collect(Collectors.toList());
    }
}