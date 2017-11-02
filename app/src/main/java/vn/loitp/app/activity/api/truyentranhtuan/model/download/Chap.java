
package vn.loitp.app.activity.api.truyentranhtuan.model.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chap {

    @SerializedName("tit")
    @Expose
    private String tit;
    @SerializedName("size")
    @Expose
    private int size;

    public String getTit() {
        return tit;
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
