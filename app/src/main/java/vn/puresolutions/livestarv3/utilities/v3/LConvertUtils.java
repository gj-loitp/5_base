package vn.puresolutions.livestarv3.utilities.v3;

import vn.puresolutions.livestarv3.activity.homescreen.model.idolroombycategory.ModelIdolRoomByCategory;
import vn.puresolutions.livestarv3.app.LSApplication;
import vn.puresolutions.livestar.core.api.model.Room;

/**
 * Created by www.muathu@gmail.com on 7/27/2017.
 */

public class LConvertUtils {
    public static ModelIdolRoomByCategory convertRoomToModelIdolOnAir(Room room) {
        String json = LSApplication.getInstance().getGson().toJson(room);
        ModelIdolRoomByCategory modelIdolRoomByCategory = LSApplication.getInstance().getGson().fromJson(json, ModelIdolRoomByCategory.class);
        return modelIdolRoomByCategory;
    }
}
