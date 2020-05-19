package com.company.LogicLayer;

import com.company.Entities.Category;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class CategoryController {

    private static List<Category> mainCategories = new LinkedList<>();
    private static boolean updated = false;

    public static void addCategory(Category category, String superCatId) throws Exception {
        if (getCategoryByID(category.getId()) != null){
            throw new Exception("A category with id " + category.getID() + " already exists");
        }
        if(superCatId == null){
            getMainCategories().add(category);
        }
        else{
            Category supCat = getCategoryByID(superCatId);
            if(supCat == null){
                throw new Exception("there isnt category with the provided id");
            }
            else{
                supCat.addCategory(category);
                category.setParent(supCat);
            }
        }
    }
    public static List<Category> getCategoriesByName(String name){
        return findCategory(getMainCategories(), (cat) -> cat.getName().equals(name), false);
    }

    public static Category getCategoryByID(String id){
        List<Category> result =  findCategory(getMainCategories(), (cat) -> cat.getId().equals(id), true);
        if (result.size() == 0){
            return null;
        }
        else {
            return result.get(0);
        }
    }

    public static List<Category> getMainCategories(){
        if(!updated){
            //TODO update from database
        }
        return mainCategories;
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
            if(!category.isUpdated()){
                //TODO update sub categories
            }
            findCategory(category.getSubCategories(), predicate, shortcut, toReturn);
            if(shortcut && toReturn.size() == 1){
                return;
            }
        }
    }
}
