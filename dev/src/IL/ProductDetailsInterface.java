package IL;

import Entities.ProductDetails;
import BL.*;

import java.util.List;

public class ProductDetailsInterface {
    public static void addProductDetails(ProductDetails productDetails, String catId) {
        ProductDetailsController.addProductDetails(productDetails, catId);
    }

    public static ProductDetails getProductDetailsById(String id){
        return ProductDetailsController.getProductDetailsById(id);
    }

    public static List<ProductDetails> getAllMissingsOfStore(int storeId){
        return ProductDetailsController.getAllStoreMissing(storeId);
    }

    public static void changeMinimalQuantity(String id, int newQuantity){
        ProductDetailsController.changeMinimalQuantity(id, newQuantity);
    }

    public static List<ProductDetails> getProductDetailsByName(String name){
        return ProductDetailsController.getProductDetailsByName(name);
    }

    public static List<ProductDetails> getStoreProductDetailsByStock(int storeId){
        return ProductDetailsController.getStoreProductDetailsByStock(storeId);
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
