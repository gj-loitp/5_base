package vn.puresolutions.livestar.core.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class CoinPackage extends BaseModel {
    private int id;
    private String name;
    private String code;
    private int price;
    private int quantity;
    private String image;
    @SerializedName("bonus_coins")
    private float bonusCoin;
    @SerializedName("bonus_percent")
    private float bonusPercent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getBonusCoin() {
        return bonusCoin;
    }

    public void setBonusCoin(float bonusCoin) {
        this.bonusCoin = bonusCoin;
    }

    public float getBonusPercent() {
        return bonusPercent;
    }

    public void setBonusPercent(float bonusPercent) {
        this.bonusPercent = bonusPercent;
    }
}
