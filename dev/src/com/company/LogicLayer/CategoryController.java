package com.company.LogicLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class CategoryController {
    private static List<Category> mainCategories = new ArrayList<>();

    public static void addCategory(Category category, String superCatId) throws Exception {
        if (getCategoryByID(category.getId()) != null || ProductDetailsController.getProductDetailsById(category.getId()) != null){
            throw new Exception("A category or a product with that id already exists");
        }
        if(superCatId == null){
            mainCategories.add(category);
        }
        else{
            List<Category> parent = findCategory(mainCategories, (cat) -> cat.getID().equals(superCatId), true);
            if(parent.size() == 1){
                parent.get(0).addCategory(category);
                category.setParent(parent.get(0));
            }
            else{
                throw new Exception("there isnt category with the provided id");
            }
        }
    }
    public static List<Category> getCategoriesByName(String name){
        return findCategory(mainCategories, (cat) -> cat.getName().equals(name), false);
    }

    public static Category getCategoryByID(String id){
         List<Category> result =  findCategory(mainCategories, (cat) -> cat.getId().equals(id), true);
        if (result.size() == 0){
            return null;
        }
        else {
            return result.get(0);
        }
    }

    private static List<Category> findCategory(List<Category> categories, Predicate<Category> predicate, boolean shortcut){
        List<Category> toReturn = new ArrayList<>();
        findCategory(categories, predicate, shortcut, toReturn);
        return toReturn;
    }
    private static void findCategory(List<Category> categories, Predicate<Category> predicate, boolean shortcut, List<Category> toReturn){
        for(Category category : categories){
            if(predicate.test(category)){
                toReturn.add(category);
                if(shortcut)
                    return;
            }
            findCategory(category.getSubCategories(), predicate, shortcut, toReturn);
            if(shortcut && toReturn.size() == 1){
                return;
            }
        }
    }
}
