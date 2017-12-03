
package vn.loitp.restapi.livestar.corev3.api.model.v3.listgift;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListGift {

    @SerializedName("totalItems")
    @Expose
    private int totalItems;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
