package com.company.PresentationLayer;

import com.company.LogicLayer.*;

import java.security.PrivilegedActionException;
import java.util.List;

public class ProductInterface {
    public static void addProduct(Product product, String typeId) throws Exception {
        ProductController.addProduct(product, typeId);
    }

    public static Product getProductById(String Id){
        return ProductController.getProductById(Id);
    }

    public static List<Product> getProductsByType(String id) throws Exception{
        return ProductController.getProductsByType(id);
    }
    public static void moveProduct(String id, String newLocation, boolean isInStorage) throws IllegalArgumentException {
        ProductController.moveProduct(id, newLocation, isInStorage);
    }
    public static List<Product> getAllDamaged() {
        return ProductController.getAllDamaged();
    }

    public static String GetProductsDetails(Product product){
        return ProductController.GetProductsDetails(product);
    }

    public static void markAsDamaged(String id) throws Exception{
        ProductController.markAsDamaged(id);
    }
    public static String stringifyProducts(){
        StringBuilder stringBuilder = new StringBuilder("Products:\n");

        for(Product product : ProductController.getAllProducts()){
            stringBuilder.append("-").append(product.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
