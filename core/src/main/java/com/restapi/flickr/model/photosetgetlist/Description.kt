package com.restapi.flickr.model.photosetgetlist

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Description {
    @SerializedName("_content")
    @Expose
    var content: String? = null

}
