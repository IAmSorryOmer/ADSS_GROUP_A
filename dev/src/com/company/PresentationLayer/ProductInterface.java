package com.company.PresentationLayer;

import com.company.LogicLayer.DiscountController;
import com.company.LogicLayer.Product;
import com.company.LogicLayer.ProductController;
import com.company.LogicLayer.ProductDetails;

import java.util.List;

public class ProductInterface {
    public static void addProduct(Product product, String typeId) throws Exception {
        ProductController.addProduct(product, typeId);
    }

    public static Product getProductById(String Id){
        return ProductController.getProductById(Id);
    }

    public static List<Product> getProductsByType(ProductDetails type){
        return ProductController.getProductsByType(type);
    }
    public static void moveProduct(Product product, String newLocation, boolean isInStorage){
        ProductController.moveProduct(product, newLocation, isInStorage);
    }
    public static List<Product> getAllDamaged() {
        return ProductController.getAllDamaged();
    }

    public static String GetProductsDetails(Product product){
        return ProductController.GetProductsDetails(product);
    }

    public static void markAsDamaged(Product product){
        ProductController.markAsDamaged(product);
    }
}
