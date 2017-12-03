
package vn.loitp.restapi.livestar.corev3.api.model.v3.gifthistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GiftHistory {

    @SerializedName("totalItems")
    @Expose
    private int totalItems;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;
    @SerializedName("totalCoins")
    @Expose
    private int totalCoins;
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

    public int getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(int totalCoins) {
        this.totalCoins = totalCoins;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
