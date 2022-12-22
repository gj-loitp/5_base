package vn.loitp.app.a.customviews.scrollablePanel

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class RoomInfo : BaseModel() {
    var roomType: String? = null
    var roomName: String? = null
    var roomId: Long = 0
}
