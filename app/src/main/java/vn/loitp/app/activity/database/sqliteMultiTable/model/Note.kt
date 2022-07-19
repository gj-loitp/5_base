package vn.loitp.app.activity.database.sqliteMultiTable.model

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

@Keep
class Note : BaseModel {
    var id: Int = 0
    var note: String? = null
    var status: Int = 0
    var createdAt: String? = null

    constructor()

    constructor(note: String, status: Int) {
        this.note = note
        this.status = status
    }

    constructor(id: Int, note: String, status: Int) {
        this.id = id
        this.note = note
        this.status = status
    }
}