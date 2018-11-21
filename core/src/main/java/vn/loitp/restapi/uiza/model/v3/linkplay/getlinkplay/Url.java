
package vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Url {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("support")
    @Expose
    private String support;
    @SerializedName("codec")
    @Expose
    private List<String> codec = null;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("region")
    @Expose
    private String region;
    @SerializedName("priority")
    @Expose
    private Integer priority;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public List<String> getCodec() {
        return codec;
    }

    public void setCodec(List<String> codec) {
        this.codec = codec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

}
