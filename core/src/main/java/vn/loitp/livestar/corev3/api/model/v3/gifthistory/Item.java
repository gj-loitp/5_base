
package vn.loitp.livestar.corev3.api.model.v3.gifthistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fromUser")
    @Expose
    private String fromUser;
    @SerializedName("fromUsername")
    @Expose
    private String fromUsername;
    @SerializedName("toUser")
    @Expose
    private String toUser;
    @SerializedName("toUsername")
    @Expose
    private String toUsername;
    @SerializedName("giftId")
    @Expose
    private String giftId;
    @SerializedName("quantity")
    @Expose
    private int quantity;
    @SerializedName("discount")
    @Expose
    private int discount;
    @SerializedName("totalCost")
    @Expose
    private int totalCost;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("Gifts")
    @Expose
    private Gifts gifts;
    @SerializedName("costWithoutDiscount")
    @Expose
    private int costWithoutDiscount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Gifts getGifts() {
        return gifts;
    }

    public void setGifts(Gifts gifts) {
        this.gifts = gifts;
    }

    public int getCostWithoutDiscount() {
        return costWithoutDiscount;
    }

    public void setCostWithoutDiscount(int costWithoutDiscount) {
        this.costWithoutDiscount = costWithoutDiscount;
    }

}
