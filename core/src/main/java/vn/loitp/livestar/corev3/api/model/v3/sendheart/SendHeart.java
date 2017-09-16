package vn.loitp.livestar.corev3.api.model.v3.sendheart;

/**
 * Created by www.muathu@gmail.com on 8/18/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SendHeart {

    @SerializedName("total_heart")
    @Expose
    private double totalHeart;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public double getTotalHeart() {
        return totalHeart;
    }

    public void setTotalHeart(double totalHeart) {
        this.totalHeart = totalHeart;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}