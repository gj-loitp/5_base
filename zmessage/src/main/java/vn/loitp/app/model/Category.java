package vn.loitp.app.model;

import java.io.Serializable;

/**
 * Created by LENOVO on 2/3/2018.
 */

public class Category implements Serializable {
    private int categoryId;
    private String description;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
