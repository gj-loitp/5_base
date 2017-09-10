package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 12/21/16
 */

public class AppUpdate extends BaseModel {
    @SerializedName("max_version")
    private String latestVersion;
    @SerializedName("force_update")
    private boolean forceUpdate;

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
}
