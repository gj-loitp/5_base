
package com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class App {

    @SerializedName("pkg")
    @Expose
    private String pkg;
    @SerializedName("config")
    @Expose
    private Config config;

    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

}
