package com.company.DataAccessLayer;

import com.company.Entities.Category;

import java.util.HashMap;

public class CategoryDAL {

    public static HashMap<String, Category> mapper = new HashMap<>();
    public static Category getCategoryById(String id){
        if(mapper.containsKey(id)){
            return mapper.get(id);
        }
        else{
            //load category
        }
        return null;
    }

}
