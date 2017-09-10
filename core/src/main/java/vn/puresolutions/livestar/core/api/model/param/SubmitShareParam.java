package vn.puresolutions.livestar.core.api.model.param;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 12/30/16
 */

public class SubmitShareParam {

    @SerializedName("device_id")
    private String deviceId;
    @SerializedName("room_id")
    private int roomId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }


    /*
    @SerializedName("accessToken")
    private String accessToken;
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    @SerializedName("post_id")
    private String postId;
    public String getPostId() {
        return postId;
    }
    public void setPostId(String postId) {
        this.postId = postId;
    }
    */

}
