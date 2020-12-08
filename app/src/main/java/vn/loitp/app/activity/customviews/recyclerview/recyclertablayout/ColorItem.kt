package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class ColorItem : BaseModel() {
    var name: String = ""
    var hex: String? = null
    var color: Int = 0
}
