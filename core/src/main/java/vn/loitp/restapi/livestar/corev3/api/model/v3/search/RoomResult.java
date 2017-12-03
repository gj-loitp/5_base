
package vn.loitp.restapi.livestar.corev3.api.model.v3.search;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Attr;
import vn.loitp.restapi.livestar.corev3.api.model.v3.roomgetbyvieworfollow.Room;

public class RoomResult {

    @SerializedName("rooms")
    @Expose
    private ArrayList<Room> rooms = null;
    @SerializedName("attr")
    @Expose
    private Attr attr;
    @SerializedName("requestId")
    @Expose
    private String requestId;

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
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
