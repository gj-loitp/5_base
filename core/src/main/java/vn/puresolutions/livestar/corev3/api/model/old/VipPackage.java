package vn.puresolutions.livestar.corev3.api.model.old;


import com.google.gson.annotations.SerializedName;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/23/2015
 */
public class VipPackage extends BaseModel {
    private long id;
    private String code;
    @SerializedName("no_day")
    private String day;
    private double price;
    private double discount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
