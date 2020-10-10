package vn.loitp.app.activity.api.truyentranhtuan.model.download

import com.core.base.BaseModel

/**
 * Created by Loitp on 4/8/2017.
 */
class DownloadObject : BaseModel() {
    var url: String = ""
    var tit: String = ""
    var status: String = ""
    var progress = 0

    companion object {
        var STATUS_IS_WAITING = "Đang chờ"
        var STATUS_IS_DOWNLOADING = "Đang tải"
        var STATUS_DOWNLOADED_SUCCESS = "Đã tải thành công"
        var STATUS_DOWNLOADED_FAIL = "Đã tải thất bại"
    }
}
