package vn.loitp.restapi.livestar.corev3.api.model.v3.audiences;
import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbycategory.User;
import vn.loitp.restapi.livestar.corev3.api.model.v3.search.Attr;

/**
 * File created on 9/6/2017.
 *
 * @author Muammil
 */

public class Audiences {

    @SerializedName("attr")
    @Expose
    private Attr attr;
    @SerializedName("items")
    @Expose
    private ArrayList<User> items = null;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public ArrayList<User> getItems() {
        return items;
    }

    public void setItems(ArrayList<User> items) {
        this.items = items;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
