package com.company.PresentationLayer;

import com.company.LogicLayer.*;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsInterface {
    public static void addProductDetails(ProductDetails productDetails) throws Exception {
        ProductDetailsController.addProductDetails(productDetails);
    }

    public static ProductDetails getProductDetailsById(String id){
        return ProductDetailsController.getProductDetailsById(id);
    }

    public static List<ProductDetails> getAllMissing(){
        return ProductDetailsController.getAllMissing();
    }

    public static void changeMinimalQuantity(String id, int newQuantity) throws Exception{
        ProductDetailsController.changeMinimalQuantity(id, newQuantity);
    }

    public static List<ProductDetails> getProductDetailsByName(String name){
        return ProductDetailsController.getProductDetailsByName(name);
    }

    public static List<ProductDetails> getProductDetailsByStock(){
        return ProductDetailsController.getProductDetailsByStock();
    }

    public static String GetProductsDetails(ProductDetails productDetails){
        return ProductDetailsController.GetProductsDetails(productDetails);
    }
    public static List<ProductDetails> getAllProductsOffCategory(List<Category> categories){
        return ProductDetailsController.getAllProductsOffCategory(categories);
    }
}
