package com.company.Tests;

import com.company.Entities.Category;
import com.company.Entities.Discount;
import com.company.Entities.Product;
import com.company.Entities.ProductDetails;
import com.company.LogicLayer.*;
import org.junit.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class StorageTests {
    static Category category;
    static ProductDetails productDetails;
    static ProductDetails productDetails2;
    static Product product;
    static Product product2;
    static Product product3;
    static Discount discount;
    static Discount discount2;
    static List<String> discList;
    static List<String> discList2;
    static List<String> discList3;


    @BeforeClass
    public static void setup() {
        category = new Category("2", "Snacks");
        productDetails = new ProductDetails("1", "Bamba", "Osem", 10.00, 5, category, 20);
        productDetails2 = new ProductDetails("2", "Bisli", "Osem", 10.00, 54, category, 20);
        product3 = new Product("Storage", "3", true,false, productDetails2);
        product = new Product("Storage", "1", true, false, productDetails);
        product2 = new Product("Shelf", "2", false, false, productDetails);
        discList = new ArrayList<>();
        discList2 = new ArrayList<>();
        discList3 = new ArrayList<>();
        discList.add(productDetails.getId());
        discList2.add(productDetails2.getId());
        discList3.add(category.getID());

        try{
            CategoryController.addCategory(category,null);
            ProductDetailsController.addProductDetails(productDetails,"2");
            ProductDetailsController.addProductDetails(productDetails2,"2");
            ProductController.addProduct(product, "1");
            ProductController.addProduct(product2, "1");
            ProductController.addProduct(product3,"2");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void moveProductToShelfTest() {
        try {
            ProductController.moveProduct(product.getId(),"Shelf",false);
            Assert.assertFalse("Product has moved to store",product.isInStorage());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void moveProductToStorageTest(){
        try {
            ProductController.moveProduct(product.getId(),"Storage",true);
            Assert.assertTrue("Product has moved to storage",product.isInStorage());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void discountOnProductTest(){
        try {
            discount = new Discount("disc1",15, LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 29));
            DiscountController.addDiscount(discount, discList, new ArrayList<>(), true);
            double priceAfterDiscount = productDetails.getRetailPrice() * (1 - (DiscountController.getProductDiscountPercentage(productDetails.getId(), true) / 100));
            Assert.assertEquals("Discount given on product succeeded", 8.5, priceAfterDiscount, 0);
            DiscountController.removeDiscount(discount);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void editDiscountOnProductTest(){
        try {
            discount = new Discount("disc3",20, LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 29));
            DiscountController.addDiscount(discount, discList, new ArrayList<>(), true);
            DiscountController.editDiscount(discount, LocalDate.of(2020, 4, 23), LocalDate.of(2020, 4, 27), 30);
            double priceAfterDiscount = productDetails.getRetailPrice() * (1 - (DiscountController.getProductDiscountPercentage(productDetails.getId(), true) / 100));
            Assert.assertEquals("Discount given on product succeeded", 7, priceAfterDiscount, 0);
            DiscountController.removeDiscount(discount);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void multipleDiscountsTest(){
        try {
            discount = new Discount("disc4",15, LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 29));
            discount2 = new Discount("disc5", 20, LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 29));
            DiscountController.addDiscount(discount, discList2, new ArrayList<>(), true);
            DiscountController.addDiscount(discount2, discList2, new ArrayList<>(), true);
            double priceAfterDiscount = productDetails2.getRetailPrice() * (1 - (DiscountController.getProductDiscountPercentage(productDetails2.getId(), true) / 100));
            Assert.assertEquals("Selected max between given discounts", 8, priceAfterDiscount, 0);
            DiscountController.removeDiscount(discount);
            DiscountController.removeDiscount(discount2);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void removeDiscountOnProductTest(){
        try {
            discount = new Discount("disc6", 20, LocalDate.of(2020, 4, 22), LocalDate.of(2020, 4, 29));
            DiscountController.addDiscount(discount, discList, new ArrayList<>(), true);
            DiscountController.removeDiscount(discount);
            double priceAfterDiscount = productDetails.getRetailPrice() * (1 - (DiscountController.getProductDiscountPercentage(productDetails.getId(), true) / 100));
            Assert.assertEquals("Price returns to original after discount removal", 10, priceAfterDiscount, 0);
            DiscountController.removeDiscount(discount);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void damagedProductTest(){
        try {
            ProductController.markAsDamaged(product3.getId());
            Assert.assertTrue(product3.isDamaged());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void minimumQuantityTest(){
        int minProductAmount = productDetails.getMinimumQuantity();
        Assert.assertFalse("Store has shortage of the product",
                productDetails.getQuantityInStorage()+productDetails.getQuantityInShelves()>= minProductAmount);
    }

    @Test
    public void existingAmountOfProdTest(){
        try {
            List<Product> existingProducts = ProductController.getProductsByType(productDetails.getId());
            Assert.assertEquals("All products were saved", 2, existingProducts.size());
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void existCategoryTest(){
        List<Category> categorys = CategoryController.getCategoriesByName("Snacks");
        Assert.assertEquals("Existing category was found", category.getName(), categorys.get(0).getName());
    }

}