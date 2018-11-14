package vn.loitp.restapi.uiza.model.v3.linkplay.gettokenstreaming;

/**
 * Created by LENOVO on 7/2/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendGetTokenStreaming {
    public final static String STREAM = "stream";
    public final static String STATIC = "static";
    public final static String CATCHUP = "catchup";
    public final static String LIVE = "live";
    @SerializedName("entity_id")
    @Expose
    private String entityId;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("content_type")
    @Expose
    private String contentType;

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

}