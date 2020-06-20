package com.company.LogicLayer;

import com.company.DataAccessLayer.ProductDAL;
import com.company.Entities.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductController {

    public static void addProduct(Product product, String typeId) throws Exception{
        ProductDetails productDetails = ProductDetailsController.getProductDetailsById(typeId);
        if(productDetails == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        addProductWithObject(product, productDetails);
    }

    public static void addProductWithObject(Product product, ProductDetails productDetails){
        if (getProductById(product.getId()) != null){
            throw new IllegalArgumentException("product with that id " + product.getId() + " already exists");
        }
        product.setType(productDetails);
        product.setExpirationDate(LocalDate.now().plusDays(productDetails.getDaysToExpiration()));
        ProductDetailsController.updateQuantity(product.getType(), 1, product.isInStorage());
        ProductDAL.insertProduct(product);
    }

    public static Product getProductById(String Id) {
        return ProductDAL.getProductById(Id);
    }

    public static List<Product> getProductsByType(String id) throws Exception{
        ProductDetails productDetails = ProductDetailsController.getProductDetailsById(id);
        if(productDetails == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        return ProductDAL.getProductByType(id);
    }

    public static void moveProduct(String id, String newLocation, boolean isInStorage) throws IllegalArgumentException{
        Product product = getProductById(id);
        if(product == null){
            throw new IllegalArgumentException("there is no product with that id");
        }
        boolean isLocationChanged = product.isInStorage() ^ isInStorage;
        if (isLocationChanged){
            if (product.isInStorage()){
                ProductDetailsController.updateQuantity(product.getType(), 1, false);
                ProductDetailsController.updateQuantity(product.getType(), -1, true);
            }
            else {
                ProductDetailsController.updateQuantity(product.getType(), 1, true);
                ProductDetailsController.updateQuantity(product.getType(), -1, false);
            }
            product.setInStorage(!product.isInStorage());
        }
        product.setLocation(newLocation);
        ProductDAL.editProduct(product);
    }

    public static List<Product> getAllDamaged() {
        return ProductDAL.getDamagedProducts();
    }

    public static String GetProductsDetails(Product product){
        return product.toString() + "\nSupplier discounts: " + DiscountController.getDiscountableDiscounts(product.getType(), false);
    }

    public static void markAsDamaged(String id) throws Exception{
        Product product = getProductById(id);
        if(product == null){
            throw new Exception("there is no product with this id");
        }
        if(product.isDamaged()){
            return;
        }
        product.setDamaged(true);
        if (product.isInStorage()){
            ProductDetailsController.updateQuantity(product.getType(), -1, true);
        }
        else {
            ProductDetailsController.updateQuantity(product.getType(), -1, false);
        }
        ProductDAL.editProduct(product);
    }
    public static void handleOrder(SingleProviderOrder singleProviderOrder){
        for(Map.Entry<CatalogItem, Integer> entry : singleProviderOrder.getOrderItems().entrySet()){
            String id = UUID.randomUUID().toString();//generate random id for the catalog items
            ProductDetails productDetails = entry.getKey().getProductDetails();
            for(int i = 1; i <= entry.getValue(); i++){
                Product product = new Product("storage", id + i, true, false, null);
                ProductController.addProductWithObject(product, productDetails);
                System.out.println("added " + product.toString());
            }
        }
    }
    public static List<Product> getAllProducts(){
        return ProductDAL.loadAll();
    }
}
