
package vn.loitp.livestar.corev3.api.model.v3.roomgetbycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.categoryget.Category;
import vn.loitp.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Attr;

public class RoomGetByCategory {

    @SerializedName("category")
    @Expose
    private Category category;
    @SerializedName("attr")
    @Expose
    private Attr attr;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
