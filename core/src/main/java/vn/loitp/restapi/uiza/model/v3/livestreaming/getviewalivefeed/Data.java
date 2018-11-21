
package vn.loitp.restapi.uiza.model.v3.livestreaming.getviewalivefeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("watchnow")
    @Expose
    private long watchnow;

    public long getWatchnow() {
        return watchnow;
    }

    public void setWatchnow(long watchnow) {
        this.watchnow = watchnow;
    }

}
