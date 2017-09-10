package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Action extends BaseModel {
    private long id;
    private String name;
    private String image;
    private int price;
    private float discount;
    private int percent;
    private int voted;
    @SerializedName("max_vote")
    private int maxVoted;

    public Action() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public int getVoted() {
        return voted;
    }

    public void setVoted(int voted) {
        this.voted = voted;
    }

    public int getMaxVoted() {
        return maxVoted;
    }

    public void setMaxVoted(int maxVoted) {
        this.maxVoted = maxVoted;
    }
}
