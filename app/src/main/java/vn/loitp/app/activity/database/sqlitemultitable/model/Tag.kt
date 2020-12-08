package vn.loitp.app.activity.database.sqlitemultitable.model

import androidx.annotation.Keep
import com.core.base.BaseModel

@Keep
class Tag : BaseModel {
    var id: Int = 0
    var tagName: String? = null

    constructor()

    constructor(tagName: String) {
        this.tagName = tagName
    }

    constructor(id: Int, tagName: String) {
        this.id = id
        this.tagName = tagName
    }
}
