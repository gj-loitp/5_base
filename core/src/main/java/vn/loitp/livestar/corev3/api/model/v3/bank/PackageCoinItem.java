
package vn.loitp.livestar.corev3.api.model.v3.bank;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageCoinItem {

    @SerializedName("totalItems")
    @Expose
    private int totalItems;
    @SerializedName("items")
    @Expose
    private ArrayList<CoinItem> items = null;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public ArrayList<CoinItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CoinItem> items) {
        this.items = items;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
