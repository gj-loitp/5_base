package vn.loitp.livestar.corev3.api.model.v3.joinroom;

/**
 * Created by www.muathu@gmail.com on 8/31/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JoinRoom {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("linkPlayLive")
    @Expose
    private String linkPlayLive;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLinkPlayLive() {
        return linkPlayLive;
    }

    public void setLinkPlayLive(String linkPlayLive) {
        this.linkPlayLive = linkPlayLive;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}