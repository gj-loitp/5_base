package com.loitpcore.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitpcore.core.base.BaseModel

@Keep
class DownloadedWrapper : BaseModel() {
    @SerializedName("downloadedComic")
    @Expose
    var downloadedComic = ArrayList<DownloadedComic>()
}
