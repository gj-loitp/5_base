package vn.puresolutions.livestar.corev3.api.model.old;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/***
 * @author Khanh Le
 * @version 1.0.0
 * @since 11/18/2015
 */
public class RoomDetail extends Room {

    @SerializedName("link_stream")
    private String streamLink;

    @SerializedName("tmp_token")
    private String socketToken;

    @SerializedName("tmp_user")
    private User socketUser;

    private List<Schedule> schedules;

    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(String streamLink) {
        this.streamLink = streamLink;
    }

    public String getSocketToken() {
        return socketToken;
    }

    public void setSocketToken(String socketToken) {
        this.socketToken = socketToken;
    }

    public User getSocketUser() {
        return socketUser;
    }

    public void setSocketUser(User socketUser) {
        this.socketUser = socketUser;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public List<Media> getVideos() {
        return getBroadcaster().getVideos();
    }
}
