package com.loitpcore.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class DownloadedComic : BaseModel() {
    @SerializedName("urlImg")
    @Expose
    var urlImg: String = ""

    @SerializedName("tit")
    @Expose
    var tit: String = ""

    @SerializedName("chaps")
    @Expose
    @Suppress("unused")
    var chaps = ArrayList<Chap>()
}
