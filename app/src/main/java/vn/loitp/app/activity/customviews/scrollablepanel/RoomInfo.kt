package vn.loitp.app.activity.customviews.scrollablepanel

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class RoomInfo : BaseModel() {
    var roomType: String? = null
    var roomName: String? = null
    var roomId: Long = 0
}
