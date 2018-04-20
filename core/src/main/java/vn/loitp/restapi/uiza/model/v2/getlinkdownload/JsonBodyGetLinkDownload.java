package vn.loitp.restapi.uiza.model.v2.getlinkdownload;

/**
 * Created by LENOVO on 2/23/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JsonBodyGetLinkDownload {

    @SerializedName("listEntityIds")
    @Expose
    private List<String> listEntityIds = null;

    public List<String> getListEntityIds() {
        return listEntityIds;
    }

    public void setListEntityIds(List<String> listEntityIds) {
        this.listEntityIds = listEntityIds;
    }
}