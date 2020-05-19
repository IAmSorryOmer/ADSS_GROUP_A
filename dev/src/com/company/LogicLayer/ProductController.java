package com.company.LogicLayer;

import com.company.Entities.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ProductController {
    private static List<Product> products = new ArrayList<>();
    private static HashMap<ProductDetails, List<Product>> productTypeToProducts = new HashMap<>();

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
        products.add(product);
        productTypeToProducts.putIfAbsent(product.getType(), new ArrayList<>());
        if(product.isInStorage()) {
            product.getType().setQuantityInStorage(product.getType().getQuantityInStorage() + 1);
        }
        else{
            product.getType().setQuantityInShelves(product.getType().getQuantityInShelves() + 1);
        }
        productTypeToProducts.get(product.getType()).add(product);
    }
    public static Product getProductById(String Id) {
        for(Product product: products){
            if (product.getId().equals(Id)){
                return product;
            }
        }
        return null;
    }

    public static List<Product> getProductsByType(String id) throws Exception{
        ProductDetails productDetails = ProductDetailsController.getProductDetailsById(id);
        if(productDetails == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        List<Product> result = productTypeToProducts.get(productDetails);
        return (result == null) ? new ArrayList<>() : result;
    }

    public static void moveProduct(String id, String newLocation, boolean isInStorage) throws IllegalArgumentException{
        Product product = getProductById(id);
        if(product == null){
            throw new IllegalArgumentException("there is no product with that id");
        }
        boolean isLocationChanged = product.isInStorage() ^ isInStorage;
        if (isLocationChanged){
            if (product.isInStorage()){
                product.getType().setQuantityInShelves(product.getType().getQuantityInShelves()+1);
                product.getType().setQuantityInStorage(product.getType().getQuantityInStorage()-1);
            }
            else {
                product.getType().setQuantityInShelves(product.getType().getQuantityInShelves()-1);
                product.getType().setQuantityInStorage(product.getType().getQuantityInStorage()+1);
            }
            product.setInStorage(!product.isInStorage());
        }
        product.setLocation(newLocation);
    }

    public static List<Product> getAllDamaged() {
        return products.stream().filter(Product::isDamaged).collect(Collectors.toList());
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
            product.getType().setQuantityInStorage(product.getType().getQuantityInStorage()-1);
        }
        else {
            product.getType().setQuantityInShelves(product.getType().getQuantityInShelves()-1);
        }
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
        return products;
    }
}
