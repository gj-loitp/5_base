package com.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Description {
    @SerializedName("_content")
    @Expose
    var content: String? = null

}
