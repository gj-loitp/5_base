package vn.loitp.app.activity.database.sqlitemultitableadvance.model

class Action {
    companion object {
        val ACTION_CREATE = 0
        val ACTION_EDIT = 1
        val ACTION_SEND_TO_VENDOR = 2
        val ACTION_DELETE = 3
        val ACTION_CONFIRM = 4
        val ACTION_DENY = 5
    }

    var id: Int = 0
    var actionType: Int? = null
    var inspection: Inspection? = null
}