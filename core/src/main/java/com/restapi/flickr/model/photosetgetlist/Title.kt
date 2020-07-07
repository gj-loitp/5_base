package com.restapi.flickr.model.photosetgetlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Title {
    @SerializedName("_content")
    @Expose
    var content: String = ""

}
