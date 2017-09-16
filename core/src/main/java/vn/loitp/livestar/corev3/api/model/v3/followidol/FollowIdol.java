
package vn.loitp.livestar.corev3.api.model.v3.followidol;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FollowIdol {

    @SerializedName("status_code")
    @Expose
    private double statusCode;
    @SerializedName("error")
    @Expose
    private double error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public double getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(double statusCode) {
        this.statusCode = statusCode;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
