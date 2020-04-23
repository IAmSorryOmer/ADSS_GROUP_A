package com.company.LogicLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductController {
    private static List<Product> products;
    private static HashMap<ProductDetails, List<Product>> productTypeToProducts = new HashMap<>();

    public static void addProduct(Product product, String typeId) throws Exception{
        if (getProductById(product.getId()) != null){
            throw new Exception("product with that Id already exists");
        }
        ProductDetails productDetails = ProductDetailsController.getProductDetailsById(typeId);
        if(productDetails == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        product.setType(productDetails);
        products.add(product);
        productTypeToProducts.putIfAbsent(product.getType(), new ArrayList<>());
        product.getType().setQuantityInStorage(product.getType().getQuantityInStorage()+1);
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

    public static List<Product> getProductsByType(ProductDetails type){
        List<Product> result = productTypeToProducts.get(type);
        return (result == null) ? new ArrayList<>() : result;
    }

    public static void moveProduct(Product product, String newLocation, boolean isInStorage){
        boolean isLocationChanged = !product.isInStorage() ^ isInStorage;
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

    public static void markAsDamaged(Product product){
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
}
