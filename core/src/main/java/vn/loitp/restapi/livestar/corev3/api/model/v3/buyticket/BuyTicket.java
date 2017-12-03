
package vn.loitp.restapi.livestar.corev3.api.model.v3.buyticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyTicket {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jsonObject")
    @Expose
    private Object jsonObject;
    @SerializedName("error@context")
    @Expose
    private ErrorContext errorContext;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("remainMoney")
    @Expose
    private double remainMoney;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    public ErrorContext getErrorContext() {
        return errorContext;
    }

    public void setErrorContext(ErrorContext errorContext) {
        this.errorContext = errorContext;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

}
