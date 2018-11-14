
package vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cdn {

    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("priority")
    @Expose
    private Integer priority;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("app_id")
    @Expose
    private String appId;
    @SerializedName("entity_id")
    @Expose
    private String entityId;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

}
