package vn.loitp.livestar.corev3.api.model.v3.error;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * File created on 8/14/2017.
 *
 * @author anhdv
 */

public class ErrorReponse extends BaseModel {
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("error")
    @Expose
    private int error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("jsonObject")
    @Expose
    private Object jsonObject;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
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
