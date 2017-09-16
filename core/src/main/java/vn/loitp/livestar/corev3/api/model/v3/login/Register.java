package vn.loitp.livestar.corev3.api.model.v3.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class Register extends BaseModel {
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("expiry")
    @Expose
    private long expiry;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("error")
    @Expose
    private Integer error;

    public void setExpiry(long expiry) {
        this.expiry = expiry;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getExpiry() {
        return expiry;
    }

    public void setExpiry(int expiry) {
        this.expiry = expiry;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
