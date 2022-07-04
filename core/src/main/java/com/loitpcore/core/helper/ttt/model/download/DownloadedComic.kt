package com.loitpcore.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

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
    var chaps = ArrayList<Chap>()
}
