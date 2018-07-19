package vn.loitp.app.activity.demo.film.group0;

import java.io.Serializable;

/**
 * Created by loitp on 7/19/2018.
 */

public class Page implements Serializable {
    private int color;
    private String name;
    private String urlImg;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlImg() {
        return urlImg;
    }

    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }
}
