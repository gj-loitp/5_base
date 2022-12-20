package com.loitpcore.restApi.flickr.model.photoSetGetList

import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.loitp.core.base.BaseModel

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class WrapperPhotosetGetlist : BaseModel() {
    @SerializedName("photosets")
    @Expose
    var photosets: Photosets? = null

    @SerializedName("stat")
    @Expose
    var stat: String? = null
}
