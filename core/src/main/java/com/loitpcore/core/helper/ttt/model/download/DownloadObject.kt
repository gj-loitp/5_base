package com.loitpcore.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.loitpcore.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
@Suppress("unused")
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
