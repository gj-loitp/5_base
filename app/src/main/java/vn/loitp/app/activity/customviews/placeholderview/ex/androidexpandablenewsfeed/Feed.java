package vn.loitp.app.activity.customviews.placeholderview.ex.androidexpandablenewsfeed;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by www.muathu@gmail.com on 9/16/2017.
 */

public class Feed {

    @SerializedName("category")
    @Expose
    private String heading;

    @SerializedName("data")
    @Expose
    private List<Info> infoList;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }
}