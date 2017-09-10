package vn.puresolutions.livestarv3.activity.homescreen.model.idolcategory;

import java.util.ArrayList;

import vn.puresolutions.livestarv3.activity.homescreen.model.ModelBase;

/**
 * Created by loitp
 */
public class ModelIdolCategory extends ModelBase {
    private ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList;

    public ModelIdolCategory() {
    }

    public ModelIdolCategory(ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList) {
        this.modelIdolCategoryDetailArrayList = modelIdolCategoryDetailArrayList;
    }

    public ArrayList<ModelIdolCategoryDetail> getModelIdolCategoryDetailArrayList() {
        return modelIdolCategoryDetailArrayList;
    }

    public void setModelIdolCategoryDetailArrayList(ArrayList<ModelIdolCategoryDetail> modelIdolCategoryDetailArrayList) {
        this.modelIdolCategoryDetailArrayList = modelIdolCategoryDetailArrayList;
    }
}
