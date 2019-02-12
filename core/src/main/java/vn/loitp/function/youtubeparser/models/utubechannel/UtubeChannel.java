
package vn.loitp.function.youtubeparser.models.utubechannel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UtubeChannel {

    @SerializedName("list")
    @Expose
    private java.util.List<UItem> list = null;

    public java.util.List<UItem> getList() {
        return list;
    }

    public void setList(java.util.List<UItem> list) {
        this.list = list;
    }

}
