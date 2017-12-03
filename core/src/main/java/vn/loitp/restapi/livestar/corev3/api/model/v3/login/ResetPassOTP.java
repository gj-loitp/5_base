package vn.loitp.restapi.livestar.corev3.api.model.v3.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/15/2017.
 *
 * @author anhdv
 */

public class ResetPassOTP extends BaseModel {
    @SerializedName("code")
    @Expose
    private String code;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
