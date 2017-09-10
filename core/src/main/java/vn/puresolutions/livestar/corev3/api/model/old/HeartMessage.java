package vn.puresolutions.livestar.corev3.api.model.old;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class HeartMessage extends Message {
    @SerializedName("bct_hearts")
    private int bctHearts;
    @SerializedName("user_hearts")
    private int userHeart;

    public int getBctHearts() {
        return bctHearts;
    }

    public void setBctHearts(int bctHearts) {
        this.bctHearts = bctHearts;
    }

    public int getUserHeart() {
        return userHeart;
    }

    public void setUserHeart(int userHeart) {
        this.userHeart = userHeart;
    }
}
