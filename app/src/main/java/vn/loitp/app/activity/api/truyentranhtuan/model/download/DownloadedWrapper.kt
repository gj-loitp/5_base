package vn.loitp.app.activity.api.truyentranhtuan.model.download

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DownloadedWrapper {
    @SerializedName("downloadedComic")
    @Expose
    var downloadedComic = ArrayList<DownloadedComic>()

}
