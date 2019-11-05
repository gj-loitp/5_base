package vn.loitp.app.activity.database.sqlitemultitableadvance.model

class Tag {
    var id: Int = 0
    var tagName: String? = null

    constructor() {

    }

    constructor(tagName: String) {
        this.tagName = tagName
    }

    constructor(id: Int, tagName: String) {
        this.id = id
        this.tagName = tagName
    }
}