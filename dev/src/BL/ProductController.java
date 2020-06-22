package BL;

import DAL.ProductDAL;
import Entities.*;

import java.time.LocalDate;
import java.util.*;


public class ProductController {

    public static void addProduct(Product product, String typeId){
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
        ProductDAL.insertProduct(product);
    }

    public static Product getProductById(String Id) {
        return ProductDAL.getProductById(Id);
    }

    public static List<Product> getStoreProductsByType(String id, int storeId){
        ProductDetails productDetails = ProductDetailsController.getProductDetailsById(id);
        if(productDetails == null){
            throw new IllegalArgumentException("there is no type with that id");
        }
        return ProductDAL.getStoreProductsByType(id, storeId);
    }

    public static void moveProduct(String id, int storeId, String newLocation, boolean isInStorage) throws IllegalArgumentException{
        Product product = getProductById(id);
        if(product == null || product.getStoreId() != storeId){
            throw new IllegalArgumentException("there is no product with that id for store number " + storeId);
        }
        product.setLocation(newLocation);
        product.setInStorage(isInStorage);
        ProductDAL.editProduct(product);
    }

    public static List<Product> getAllDamagedInStore(int storeId) {
        return ProductDAL.getStoreDamagedProducts(storeId);
    }

    public static String GetProductsDetails(Product product){
        return product.toString() + "\nSupplier discounts: " + DiscountController.getDiscountableDiscounts(product.getType(), false);
    }

    public static void markAsDamaged(String id, int storeId){
        Product product = getProductById(id);
        if(product == null || product.getStoreId() != storeId){
            throw new IllegalArgumentException("there is no product with this id");
        }
        if(product.isDamaged()){
            return;
        }
        product.setDamaged(true);
        ProductDAL.editProduct(product);
    }
    /*public void handleOrder(SingleProviderOrder singleProviderOrder){
        for(Map.Entry<CatalogItem, Integer> entry : singleProviderOrder.getOrderItems().entrySet()){
            String id = UUID.randomUUID().toString();//generate random id for the catalog items
            ProductDetails productDetails = entry.getKey().getProductDetails();
            for(int i = 1; i <= entry.getValue(); i++){
                Product product = new Product("storage", id + i, true, false, null);
                ProductController.addProductWithObject(product, productDetails);
                System.out.println("added " + product.toString());
            }
        }
    }*/
    public static List<Product> getAllStoreProducts(int storeId){
        return ProductDAL.loadStoreAll(storeId);
    }
}
