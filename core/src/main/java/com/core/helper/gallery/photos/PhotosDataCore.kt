package com.core.helper.gallery.photos

import com.restapi.flickr.model.photosetgetphotos.Photo
import java.util.*

/**
 * Created by www.muathu@gmail.com on 10/8/2017.
 */
class PhotosDataCore private constructor() {
    private val logTag = PhotosDataCore::class.java.simpleName
    private var photoList = ArrayList<Photo>()

    companion object {
        val instance = PhotosDataCore()
    }

    fun getPhotoList(): List<Photo> {
        return photoList
    }

    fun setPhotoList(photoList: ArrayList<Photo>) {
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
