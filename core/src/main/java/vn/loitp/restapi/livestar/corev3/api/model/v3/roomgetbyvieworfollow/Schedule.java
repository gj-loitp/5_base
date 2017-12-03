
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.BaseModel;

public class Schedule extends BaseModel {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("start")
    @Expose
    private String start;
    @SerializedName("end")
    @Expose
    private Object end;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Object getEnd() {
        return end;
    }

    public void setEnd(Object end) {
        this.end = end;
    }

}
