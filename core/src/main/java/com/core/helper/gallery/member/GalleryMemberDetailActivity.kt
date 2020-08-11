package com.core.helper.gallery.member

import android.os.Bundle
import com.R
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_activity_flickr_member_detail.*

class GalleryMemberDetailActivity : BaseFontActivity() {

    companion object {
        const val PHOTO = "PHOTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LUIUtil.setTextShadow(textView = tvTitle)
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo = photo)

        LImageUtil.setImageViewZoom(iv = imageView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_member_detail
    }

    private fun loadItem(photo: Photo) {
        tvTitle.text = photo.title
        LImageUtil.load(context = activity, url = photo.urlO, imageView = imageView)
    }

    private fun loadThumbnail(photo: Photo) {
        LImageUtil.loadNoAmin(context = activity, url = photo.urlM, imageView = imageView)
    }

    private fun loadFullSizeImage(photo: Photo) {
        LImageUtil.loadNoAmin(context = activity, url = photo.urlO, urlThumbnal = photo.urlM, imageView = imageView, drawableRequestListener = null)
    }
}
