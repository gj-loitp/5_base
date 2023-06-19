package com.loitp.restApi.flickr

import androidx.annotation.Keep

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class FlickrConst {
    companion object {
        @JvmField
        var API_KEY = "5a0d3d183e5c31fbb27e82a81050db5f"

        @JvmField
        var USER_KEY = "141987004@N02"

        @JvmField
        var METHOD_PHOTOSETS_GETLIST = "flickr.photosets.getList"

        @JvmField
        var METHOD_PHOTOSETS_GETPHOTOS = "flickr.photosets.getPhotos"

        @JvmField
        var PRIMARY_PHOTO_EXTRAS_0 = "url_o,url_m"

        @JvmField
        var PRIMARY_PHOTO_EXTRAS_1 = "url_o,url_m,url_s,%2Cviews"

        @JvmField
        var FORMAT = "json"

        @JvmField
        var NO_JSON_CALLBACK = 1
    }
}
