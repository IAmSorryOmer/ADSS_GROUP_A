package com.company.LogicLayer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsController {
    private static List<ProductDetails> productDetailsList = new ArrayList<>();

    public static void addProductDetails(ProductDetails productDetails, String catId) throws Exception {
        if (getProductDetailsById(productDetails.getId()) != null){
            throw new Exception("A product type with id " + productDetails.getId() + " already exists");
        }
        Category category = CategoryController.getCategoryByID(catId);
        if(category == null){
            throw new IllegalArgumentException("there is no such category id");
        }
        productDetails.setCategory(category);
        productDetailsList.add(productDetails);
    }

    public static ProductDetails getProductDetailsById(String id){
        //return first product with id
        return productDetailsList.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<ProductDetails> getAllMissing() {
        //this function return all the products that in shortage
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
    public static List<ProductDetails> getAllProductsOffCategory(List<String> categorieIds) throws Exception{
        List<Category> categories = new ArrayList<>();
        for(String categoryId : categorieIds){
            Category categoryToAdd = CategoryController.getCategoryByID(categoryId);
            if (categoryToAdd == null){
                throw new IllegalArgumentException("there isnt category with id " + categoryId + ".");
            }
            categories.add(categoryToAdd);
        }
        return productDetailsList.stream().filter(product -> categories.stream().anyMatch(cat -> isInCategory(product, cat))).collect(Collectors.toList());
    }
    public static List<ProductDetails> getProductDetailsList(){
        return productDetailsList;
    }
}