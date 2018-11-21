
package vn.loitp.restapi.uiza.model.v3.linkplay.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FileName {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("support")
    @Expose
    private String support;
    @SerializedName("codec")
    @Expose
    private List<String> codec = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

}
