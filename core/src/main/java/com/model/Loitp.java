
package com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
