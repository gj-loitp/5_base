package com.restapi.flickr.model.photosetgetlist

import androidx.annotation.Keep
import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
class Title : BaseModel() {
    @SerializedName("_content")
    @Expose
    var content: String = ""
}
