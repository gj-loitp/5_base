
package vn.loitp.restapi.uiza.model.v3.authentication.gettoken;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("expired")
    @Expose
    private long expired;
    @SerializedName("appId")
    @Expose
    private String appId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public long getExpired() {
        return expired;
    }

    public void setExpired(long expired) {
        this.expired = expired;
    }
}
