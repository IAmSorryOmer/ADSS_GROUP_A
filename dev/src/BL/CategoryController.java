package BL;

import DAL.CategoryDAL;
import Entities.Category;

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
            //if not updated then it will be overwritten so no point in add
            if(updated) {
                getMainCategories().add(category);
            }
        }
        else{
            Category supCat = getCategoryByID(superCatId);
            if(supCat == null){
                throw new Exception("there isnt category with the provided id");
            }
            else{
                if(supCat.isUpdated())
                    supCat.addCategory(category);
                category.setParent(supCat);
            }
        }
        CategoryDAL.insertCategory(category);
    }
    public static List<Category> getCategoriesByName(String name){
        return findCategory(getMainCategories(), (cat) -> cat.getName().equals(name), false);
    }

    public static Category getCategoryByID(String id){
        return CategoryDAL.getCategoryById(id);
    }

    public static List<Category> getMainCategories(){
        if(!updated){
            mainCategories = CategoryDAL.loadMainCategories();
            updated = true;
        }
        return mainCategories;
    }
    public static List<Category> getSubCategories(Category category){
        if(!category.isUpdated()){
            CategoryDAL.loadCategorySubs(category);
            category.setUpdated(true);
        }
        return category.getSubCategories();
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
            findCategory(getSubCategories(category), predicate, shortcut, toReturn);
            if(shortcut && toReturn.size() == 1){
                return;
            }
        }
    }
    public static String stringifyCategories(){
        StringBuilder stringBuilder = new StringBuilder("Categories: \n");
        CategoryController.stringifyCategories(stringBuilder, CategoryController.getMainCategories(), "");
        return stringBuilder.toString();
    }
    public static void stringifyCategories(StringBuilder stringBuilder, List<Category> categories, String spaces){
        for(Category category: categories){
            stringBuilder.append(spaces).append(category.toString()).append("\n");
            stringifyCategories(stringBuilder, getSubCategories(category), spaces + "  ");
        }
    }

}
