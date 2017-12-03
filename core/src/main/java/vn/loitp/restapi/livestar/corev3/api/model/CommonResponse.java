package vn.loitp.restapi.livestar.corev3.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * File created on 9/8/2017.
 *
 * @author Muammil
 */
public class CommonResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("error")
    @Expose
    private Integer error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jsonObject")
    @Expose
    private Object jsonObject;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(Object jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}

