
package vn.loitp.livestar.corev3.api.model.v3.getposter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImagesPath {

    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("w1080h622")
    @Expose
    private String w1080h622;
    @SerializedName("w720h415")
    @Expose
    private String w720h415;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getW1080h622() {
        return w1080h622;
    }

    public void setW1080h622(String w1080h622) {
        this.w1080h622 = w1080h622;
    }

    public String getW720h415() {
        return w720h415;
    }

    public void setW720h415(String w720h415) {
        this.w720h415 = w720h415;
    }

}
