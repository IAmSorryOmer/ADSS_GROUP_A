package com.company.PresentationLayer;

import com.company.Entities.Category;
import com.company.LogicLayer.CategoryController;

import java.util.List;

public class CategoryInterface {
    public static void addCategory(Category category, String superCatId) throws Exception{
        CategoryController.addCategory(category, superCatId);
    }
    public static List<Category> getCategoriesByName(String name){
        return CategoryController.getCategoriesByName(name);
    }
    public static Category getCategoryByID(String id){
        return CategoryController.getCategoryByID(id);
    }
    public static String stringifyCategories(){
        return CategoryController.stringifyCategories();
    }

}
