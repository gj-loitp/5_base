package vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory;

import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbycategory.RoomGetByCategory;
import vn.puresolutions.livestarv3.activity.homescreen.model.ModelBase;

/**
 * Created by www.muathu@gmail.com on 7/25/2017.
 */

public class ModelIdolRoomByCategory extends ModelBase {
    private RoomGetByCategory roomGetByCategory;

    public RoomGetByCategory getRoomGetByCategory() {
        return roomGetByCategory;
    }

    public void setRoomGetByCategory(RoomGetByCategory roomGetByCategory) {
        this.roomGetByCategory = roomGetByCategory;
    }
}
