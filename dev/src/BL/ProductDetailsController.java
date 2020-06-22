package BL;
import DAL.ProductDetailsDAL;
import Entities.Category;
import Entities.ProductDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDetailsController {

    public static void addProductDetails(ProductDetails productDetails, String catId) {
        if (getProductDetailsById(productDetails.getId()) != null){
            throw new IllegalArgumentException("A product type with id " + productDetails.getId() + " already exists");
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

    public static List<ProductDetails> getAllStoreMissing(int storeId) {
        return ProductDetailsDAL.getStoreMissingProducts(storeId);
    }

    public static void changeMinimalQuantity(String id, int newQuantity) {
        ProductDetails productDetails = getProductDetailsById(id);
        if(productDetails == null || newQuantity < -1){
            throw new IllegalArgumentException("A product with that ID does not exist or illegal quantity");
        }
        productDetails.setMinimumQuantity(newQuantity);
        ProductDetailsDAL.editProductDetails(productDetails);
    }

    public static List<ProductDetails> getProductDetailsByName(String name) {
        return ProductDetailsDAL.getProductDetailsByName(name);
    }

    public static List<ProductDetails> getStoreProductDetailsByStock(int storeId) {
        return ProductDetailsDAL.getStoreProductDetailsInStock(storeId);
    }

    public static int getProductDetailsQuantityInStore(int storeId, String productDetailsId){
        return ProductDetailsDAL.getStoreProductDetailsQuantity(storeId, productDetailsId);
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

    public static List<ProductDetails> getAllProductsOffCategory(List<String> categorieIds){
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