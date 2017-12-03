package vn.loitp.restapi.livestar.corev3.api.model.v3.sendgift;

/**
 * Created by www.muathu@gmail.com on 8/21/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendGift {

    @SerializedName("status_code")
    @Expose
    private int statusCode;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("jsonObject")
    @Expose
    private Object jsonObject;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("giftLogId")
    @Expose
    private String giftLogId;
    @SerializedName("remainMoney")
    @Expose
    private int remainMoney;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getGiftLogId() {
        return giftLogId;
    }

    public void setGiftLogId(String giftLogId) {
        this.giftLogId = giftLogId;
    }

    public int getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(int remainMoney) {
        this.remainMoney = remainMoney;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}