package vn.puresolutions.livestarv3.activity.homescreen.model.idolfollow;

import vn.puresolutions.livestar.corev3.api.model.v3.roomgetbyvieworfollow.RoomFollowOrByView;
import vn.puresolutions.livestarv3.activity.homescreen.model.ModelBase;

/**
 * Created by loitp
 */
public class ModelIdolFollowOrSuggest extends ModelBase {
    private RoomFollowOrByView roomFollowOrByView;

    public RoomFollowOrByView getRoomFollowOrByView() {
        return roomFollowOrByView;
    }

    public void setRoomFollowOrByView(RoomFollowOrByView roomFollowOrByView) {
        this.roomFollowOrByView = roomFollowOrByView;
    }
}
