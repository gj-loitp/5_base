package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Payment extends BaseModel {
    private long id;
    @SerializedName("created_at")
    private Date createdDate;
    private String name;
    private String thumb;
    private int quantity;
    private long cost;
    @SerializedName("total_cost")
    private long totalCost;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public long getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(long totalCost) {
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}