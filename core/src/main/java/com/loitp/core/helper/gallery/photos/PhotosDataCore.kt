package com.loitp.core.helper.gallery.photos

import androidx.annotation.Keep
import com.loitp.restApi.flickr.model.photoSetGetPhotos.Photo

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
@Keep
class PhotosDataCore private constructor() {
    private var photoList = ArrayList<Photo>()

    companion object {
        val instance = PhotosDataCore()
    }

    fun getPhotoList(): List<Photo> {
        return photoList
    }

    private fun setPhotoList(photoList: ArrayList<Photo>) {
        this.photoList = photoList
    }

    fun addPhoto(photoList: ArrayList<Photo>) {
        if (this.photoList.isEmpty()) {
            setPhotoList(photoList)
            return
        }
        if (photoList.size > 300) {
            this.photoList.clear()
        }
        this.photoList.addAll(photoList)
    }

    fun clearData() {
        photoList.clear()
    }

    val size: Int
        get() = if (photoList.isEmpty()) {
            0
        } else photoList.size

    fun getPhoto(position: Int): Photo? {
        return if (photoList.isEmpty() || position < 0 || position > photoList.size) {
            null
        } else photoList[position]
    }

    fun getPosition(photoID: String): Int {
        for (i in photoList.indices) {
            if (photoID == photoList[i].id) {
                return i
            }
        }
        return -1
    }
}
