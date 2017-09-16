package vn.loitp.livestar.corev3.api.model.v3.endlive;

/**
 * Created by www.muathu@gmail.com on 8/28/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EndLive {

    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("total_view")
    @Expose
    private int totalView;
    @SerializedName("total_heart")
    @Expose
    private int totalHeart;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public int getTotalView() {
        return totalView;
    }

    public void setTotalView(int totalView) {
        this.totalView = totalView;
    }

    public int getTotalHeart() {
        return totalHeart;
    }

    public void setTotalHeart(int totalHeart) {
        this.totalHeart = totalHeart;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}