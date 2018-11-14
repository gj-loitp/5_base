
package vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import vn.loitp.restapi.uiza.model.v2.listallentity.Metadata;
import vn.loitp.restapi.uiza.model.v3.metadata.getdetailofmetadata.Data;

public class ResultRetrieveALiveEvent {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("version")
    @Expose
    private long version;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("policy")
    @Expose
    private String policy;
    @SerializedName("requestId")
    @Expose
    private String requestId;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("code")
    @Expose
    private long code;
    @SerializedName("type")
    @Expose
    private String type;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
