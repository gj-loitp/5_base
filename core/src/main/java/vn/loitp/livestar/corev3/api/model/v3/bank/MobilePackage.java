
package vn.loitp.livestar.corev3.api.model.v3.bank;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

public class MobilePackage extends BaseModel {

    @SerializedName("iap")
    @Expose
    private Iap iap;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public Iap getIap() {
        return iap;
    }

    public void setIap(Iap iap) {
        this.iap = iap;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
