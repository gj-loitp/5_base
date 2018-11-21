
package vn.loitp.restapi.uiza.model.v3.livestreaming.retrievealiveevent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LastPullInfo {

    @SerializedName("primaryInputUri")
    @Expose
    private String primaryInputUri;
    @SerializedName("secondaryInputUri")
    @Expose
    private Object secondaryInputUri;

    public String getPrimaryInputUri() {
        return primaryInputUri;
    }

    public void setPrimaryInputUri(String primaryInputUri) {
        this.primaryInputUri = primaryInputUri;
    }

    public Object getSecondaryInputUri() {
        return secondaryInputUri;
    }

    public void setSecondaryInputUri(Object secondaryInputUri) {
        this.secondaryInputUri = secondaryInputUri;
    }

}
