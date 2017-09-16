package vn.loitp.livestar.corev3.api.model.v3.roomgetbycategory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.livestar.corev3.api.model.v3.BaseModel;

/**
 * Created by www.muathu@gmail.com on 8/22/2017.
 */

public class Level extends BaseModel {

    @SerializedName("level")
    @Expose
    private int level;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
