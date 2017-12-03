package vn.loitp.restapi.livestar.corev3.api.model.v3.startlive;

/**
 * Created by www.muathu@gmail.com on 8/28/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StartLive {

    @SerializedName("broadcastUrl")
    @Expose
    private String broadcastUrl;
    @SerializedName("sessionId")
    @Expose
    private String sessionId;
    @SerializedName("roomId")
    @Expose
    private String roomId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public String getBroadcastUrl() {
        return broadcastUrl;
    }

    public void setBroadcastUrl(String broadcastUrl) {
        this.broadcastUrl = broadcastUrl;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}