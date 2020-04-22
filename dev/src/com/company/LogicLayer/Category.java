package com.company.LogicLayer;

import java.util.ArrayList;
import java.util.List;

public class Category implements Discountable{
    private String ID;
    private String name;
    private Category parent;
    private List<Category> subCategories;

    public Category(String ID, String name) {
        this.ID = ID;
        this.name = name;
        this.parent = null;
        this.subCategories = new ArrayList<>();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return ID;
    }


    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }
    public void addCategory(Category category){
        subCategories.add(category);
    }
}
