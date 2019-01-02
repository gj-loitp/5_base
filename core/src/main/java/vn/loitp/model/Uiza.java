
package vn.loitp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Uiza {

    @SerializedName("domainApi")
    @Expose
    private String domainApi;
    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("urlLinkPlayEnvironment")
    @Expose
    private String urlLinkPlayEnvironment;
    @SerializedName("metadataId")
    @Expose
    private String metadataId;

    public String getDomainApi() {
        return domainApi;
    }

    public void setDomainApi(String domainApi) {
        this.domainApi = domainApi;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrlLinkPlayEnvironment() {
        return urlLinkPlayEnvironment;
    }

    public void setUrlLinkPlayEnvironment(String urlLinkPlayEnvironment) {
        this.urlLinkPlayEnvironment = urlLinkPlayEnvironment;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

}
