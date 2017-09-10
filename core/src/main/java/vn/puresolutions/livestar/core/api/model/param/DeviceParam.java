package vn.puresolutions.livestar.core.api.model.param;

import com.google.gson.annotations.SerializedName;

/**
 * Created by khanh on 7/25/16.
 */
public class DeviceParam {
    @SerializedName("device_token")
    private String deviceToken;
    @SerializedName("device_id")
    private String deviceId;
    private String type = "android";

    public DeviceParam(String deviceToken, String deviceId) {
        this.deviceToken = deviceToken;
        this.deviceId = deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
