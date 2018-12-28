
package vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LastPushInfo implements Serializable {

    @SerializedName("streamUrl")
    @Expose
    private String streamUrl;
    @SerializedName("streamKey")
    @Expose
    private String streamKey;

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getStreamKey() {
        return streamKey;
    }

    public void setStreamKey(String streamKey) {
        this.streamKey = streamKey;
    }

}
