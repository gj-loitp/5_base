
package vn.loitp.app.activity.api.truyentranhtuan.model.comic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Comics {

    @SerializedName("comic")
    @Expose
    private List<Comic> comic = new ArrayList<Comic>();

    /**
     * 
     * @return
     *     The comic
     */
    public List<Comic> getComic() {
        return comic;
    }

    /**
     * 
     * @param comic
     *     The comic
     */
    public void setComic(List<Comic> comic) {
        this.comic = comic;
    }

}
