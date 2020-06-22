package Entities;

import java.util.ArrayList;
import java.util.List;

public class Category implements Discountable {

    private String ID;
    private String name;
    private Category parent;
    private List<Category> subCategories;
    private boolean updated;

    public Category(String ID, String name) {
        this.ID = ID;
        this.name = name;
        this.parent = null;
        this.subCategories = new ArrayList<>();
        this.updated = false;
    }

    public Category(String ID, String name, Category parent) {
        this.ID = ID;
        this.name = name;
        this.parent = parent;
        this.subCategories = new ArrayList<>();
        this.updated = false;
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

    public void addCategory(Category category){
        subCategories.add(category);
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }

    public boolean isUpdated(){
        return updated;
    }
    public void setUpdated(boolean val){
        this.updated = val;
    }
    public List<Category> getSubCategories(){
        return subCategories;
    }
    @Override
    public String toString() {
        return "name: " + name + ", Id:" + ID;
    }
}
