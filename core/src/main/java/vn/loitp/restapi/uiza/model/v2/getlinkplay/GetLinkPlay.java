package vn.loitp.restapi.uiza.model.v2.getlinkplay;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.loitp.restapi.uiza.model.v2.getlinkdownload.Hl;
import vn.loitp.restapi.uiza.model.v2.getlinkdownload.HlsT;
import vn.loitp.restapi.uiza.model.v2.getlinkdownload.Mpd;

/**
 * Created by LENOVO on 4/13/2018.
 */

public class GetLinkPlay {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("hls")
    @Expose
    private List<Hl> hls = null;
    @SerializedName("hls_ts")
    @Expose
    private List<HlsT> hlsTs = null;
    @SerializedName("hevc")
    @Expose
    private List<Object> hevc = null;
    @SerializedName("mpd")
    @Expose
    private List<Mpd> mpd = null;
    @SerializedName("version")
    @Expose
    private int version;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private int code;
    @SerializedName("type")
    @Expose
    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Hl> getHls() {
        return hls;
    }

    public void setHls(List<Hl> hls) {
        this.hls = hls;
    }

    public List<HlsT> getHlsTs() {
        return hlsTs;
    }

    public void setHlsTs(List<HlsT> hlsTs) {
        this.hlsTs = hlsTs;
    }

    public List<Object> getHevc() {
        return hevc;
    }

    public void setHevc(List<Object> hevc) {
        this.hevc = hevc;
    }

    public List<Mpd> getMpd() {
        return mpd;
    }

    public void setMpd(List<Mpd> mpd) {
        this.mpd = mpd;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}