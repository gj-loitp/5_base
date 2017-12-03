
package vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomFollowOrByView {

    @SerializedName("rooms")
    @Expose
    private List<Room> rooms = null;
    @SerializedName("attr")
    @Expose
    private Attr attr;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public Attr getAttr() {
        return attr;
    }

    public void setAttr(Attr attr) {
        this.attr = attr;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}
