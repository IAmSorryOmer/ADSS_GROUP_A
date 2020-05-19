package com.company.PresentationLayer;

import com.company.Entities.ProductDetails;
import com.company.LogicLayer.*;

import java.util.List;

public class ProductDetailsInterface {
    public static void addProductDetails(ProductDetails productDetails, String catId) throws Exception {
        ProductDetailsController.addProductDetails(productDetails, catId);
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
    public static List<ProductDetails> getAllProducts(){
        return ProductDetailsController.getProductDetailsList();
    }

    public static String stringifyProduct(){
        StringBuilder stringBuilder = new StringBuilder("Products:\n");

        for(ProductDetails productDetails : ProductDetailsController.getProductDetailsList()){
            stringBuilder.append("-").append(productDetails.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
