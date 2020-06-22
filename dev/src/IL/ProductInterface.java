package IL;

import Entities.Product;
import BL.*;

import java.util.List;

public class ProductInterface {
    public static void addProduct(Product product, String typeId){
        ProductController.addProduct(product, typeId);
    }

    public static List<Product> getStoreProductsByType(int storeId, String id){
        return ProductController.getStoreProductsByType(id, storeId);
    }
    public static void moveProduct(String id, int storeId, String newLocation, boolean isInStorage){
        ProductController.moveProduct(id, storeId, newLocation, isInStorage);
    }
    public static List<Product> getAllDamagedOfStore(int storeId) {
        return ProductController.getAllDamagedInStore(storeId);
    }

    public static String GetProductsDetails(Product product){
        return ProductController.GetProductsDetails(product);
    }

    public static void markAsDamaged(String id, int storeId){
        ProductController.markAsDamaged(id, storeId);
    }
    public static String stringifyStoreProducts(int storeId){
        StringBuilder stringBuilder = new StringBuilder("Products:\n");

        for(Product product : ProductController.getAllStoreProducts(storeId)){
            stringBuilder.append("-").append(product.toString()).append("\n");
        }
        return stringBuilder.toString();
    }
}
