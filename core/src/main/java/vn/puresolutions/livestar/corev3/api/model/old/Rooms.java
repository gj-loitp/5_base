package vn.puresolutions.livestar.corev3.api.model.old;

import java.util.List;

/**
 * @author hoangphu
 * @since 7/21/16
 */
public class Rooms extends BaseModel {
    private int totalPage;
    private List<Room> rooms;

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
