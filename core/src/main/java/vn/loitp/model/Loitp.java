
package vn.loitp.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Loitp {

    @SerializedName("loitp")
    @Expose
    private List<App> loitp = null;

    public List<App> getLoitp() {
        return loitp;
    }

    public void setLoitp(List<App> loitp) {
        this.loitp = loitp;
    }

}
