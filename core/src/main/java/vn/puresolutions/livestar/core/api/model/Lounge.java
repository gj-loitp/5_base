package vn.puresolutions.livestar.core.api.model;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Lounge extends BaseModel {
    @SerializedName("lounge")
    private int index;
    private User user;
    private long cost;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
