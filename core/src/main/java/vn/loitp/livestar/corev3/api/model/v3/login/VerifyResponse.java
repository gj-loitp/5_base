package vn.loitp.livestar.corev3.api.model.v3.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class VerifyResponse extends BaseModel {
    @SerializedName("verify")
    @Expose
    private boolean verify;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("error")
    @Expose
    private Integer error;

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public boolean isVerify() {
        return verify;
    }

    public void setVerify(boolean verify) {
        this.verify = verify;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
