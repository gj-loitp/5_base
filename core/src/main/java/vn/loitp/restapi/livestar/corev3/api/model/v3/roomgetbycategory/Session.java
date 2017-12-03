package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

/**
 * Created by www.muathu@gmail.com on 8/22/2017.
 */

public class Session extends BaseModel {
    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("view")
    @Expose
    private int view;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
