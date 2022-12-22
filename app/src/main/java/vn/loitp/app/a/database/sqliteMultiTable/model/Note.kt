package vn.loitp.app.a.database.sqliteMultiTable.model

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 15.09.2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */

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
