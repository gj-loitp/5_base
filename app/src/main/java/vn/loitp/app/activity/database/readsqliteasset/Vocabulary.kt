package vn.loitp.app.activity.database.readsqliteasset

/**
 * Created by Loitp on 5/2/2017.
 */

class Vocabulary {
    var isClose: Boolean = false
    var id: Int = 0
    var sword: String? = null
    var sphonetic: String? = null
    var smeanings: String? = null
    var ssummary: String? = null
    var sisoxfordlist: Int = 0

    constructor() {

    }

    constructor(id: Int, sword: String, sphonetic: String, smeanings: String, ssummary: String, sisoxfordlist: Int, isClose: Boolean) {
        this.id = id
        this.sword = sword
        this.sphonetic = sphonetic
        this.smeanings = smeanings
        this.ssummary = ssummary
        this.sisoxfordlist = sisoxfordlist
        this.isClose = isClose
    }
}
