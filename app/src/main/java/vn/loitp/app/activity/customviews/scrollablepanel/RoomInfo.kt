package vn.loitp.app.activity.customviews.scrollablepanel

import androidx.annotation.Keep
import com.core.base.BaseModel

/**
 * Created by kelin on 16-11-18.
 */

@Keep
class RoomInfo : BaseModel() {
    var roomType: String? = null
    var roomName: String? = null
    var roomId: Long = 0
}
