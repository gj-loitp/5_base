package com.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Uiza {

    @SerializedName("domainApi")
    @Expose
    var domainApi: String? = null
    @SerializedName("appId")
    @Expose
    var appId: String? = null
    @SerializedName("token")
    @Expose
    var token: String? = null
    @SerializedName("urlLinkPlayEnvironment")
    @Expose
    var urlLinkPlayEnvironment: String? = null
    @SerializedName("metadataId")
    @Expose
    var metadataId: String? = null

}
