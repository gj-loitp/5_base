package com.core.helper.ttt.model.download

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class DownloadedWrapper : BaseModel(){
    @SerializedName("downloadedComic")
    @Expose
    var downloadedComic = ArrayList<DownloadedComic>()

}
