
package vn.loitp.app.activity.api.truyentranhtuan.model.chap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Chaps {
    @SerializedName("chap")
    @Expose
    private List<Chap> chap = new ArrayList<Chap>();

    /**
     * @return The chap
     */
    public List<Chap> getChap() {
        return chap;
    }

    /**
     * @param chap The chap
     */
    public void setChap(List<Chap> chap) {
        this.chap = chap;
    }
}
