
package vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data {

    @SerializedName("cdn")
    @Expose
    private List<Cdn> cdn = null;
    @SerializedName("cdn_fallback")
    @Expose
    private List<Object> cdnFallback = null;
    @SerializedName("appId")
    @Expose
    private String appId;
    @SerializedName("entityId")
    @Expose
    private String entityId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("fileName")
    @Expose
    private List<FileName> fileName = null;
    @SerializedName("urls")
    @Expose
    private List<Url> urls = null;
    @SerializedName("allRecord")
    @Expose
    private List<AllRecord> allRecord = null;

    public List<Cdn> getCdn() {
        return cdn;
    }

    public void setCdn(List<Cdn> cdn) {
        this.cdn = cdn;
    }

    public List<Object> getCdnFallback() {
        return cdnFallback;
    }

    public void setCdnFallback(List<Object> cdnFallback) {
        this.cdnFallback = cdnFallback;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<FileName> getFileName() {
        return fileName;
    }

    public void setFileName(List<FileName> fileName) {
        this.fileName = fileName;
    }

    public List<Url> getUrls() {
        return urls;
    }

    public void setUrls(List<Url> urls) {
        this.urls = urls;
    }

    public List<AllRecord> getAllRecord() {
        return allRecord;
    }

    public void setAllRecord(List<AllRecord> allRecord) {
        this.allRecord = allRecord;
    }

}
