package vn.loitp.app.activity.customviews.recyclerview.recyclerTabLayout

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

@Keep
class ColorItem : BaseModel() {
    var name: String = ""
    var hex: String? = null
    var color: Int = 0
}
