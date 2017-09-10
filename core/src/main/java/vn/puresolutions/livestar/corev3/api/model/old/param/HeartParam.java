package vn.puresolutions.livestar.corev3.api.model.old.param;


import com.google.gson.annotations.SerializedName;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class HeartParam {
    private int hearts;
    @SerializedName("room_id")
    private int roomId;

    public HeartParam(int roomId) {
        this.hearts = 1;
        this.roomId = roomId;
    }

    public int getHearts() {
        return hearts;
    }

    public void setHearts(int hearts) {
        this.hearts = hearts;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
