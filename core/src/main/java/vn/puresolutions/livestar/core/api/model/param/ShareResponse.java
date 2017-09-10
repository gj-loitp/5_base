package vn.puresolutions.livestar.core.api.model.param;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * File created on 7/18/2017.
 *
 * @author Anhdv
 */

public class ShareResponse {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("user_money")
    @Expose
    private Integer userMoney;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getUserMoney() {
        return userMoney;
    }

    public void setUserMoney(Integer userMoney) {
        this.userMoney = userMoney;
    }

}
