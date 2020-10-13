package com.restapi.flickr.model.photosetgetlist

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Description : BaseModel() {
    @SerializedName("_content")
    @Expose
    var content: String? = null

}
