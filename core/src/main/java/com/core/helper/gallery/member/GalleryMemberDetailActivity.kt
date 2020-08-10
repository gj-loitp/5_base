package com.core.helper.gallery.member

import android.os.Bundle
import androidx.core.view.ViewCompat
import com.R
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import kotlinx.android.synthetic.main.l_activity_flickr_member_detail.*

class GalleryMemberDetailActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LUIUtil.setTextShadow(tvTitle)
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo)
        ViewCompat.setTransitionName(touchImageView, IV)
        ViewCompat.setTransitionName(tvTitle, TV)
    }

    override fun setFullScreen(): Boolean {
        return true
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_member_detail
    }

    private fun loadItem(photo: Photo) {
        tvTitle.text = photo.title
        LImageUtil.loadNoAmin(context = activity, url = photo.urlO, urlThumbnal = photo.urlS, imageView = touchImageView)
    }

    private fun loadThumbnail(photo: Photo) {
        LImageUtil.loadNoAmin(activity, photo.urlM, touchImageView)
    }

    private fun loadFullSizeImage(photo: Photo) {
        LImageUtil.loadNoAmin(context = activity, url = photo.urlO, urlThumbnal = photo.urlM, imageView = touchImageView, drawableRequestListener = null)
    }

    companion object {
        const val PHOTO = "PHOTO"
        const val IV = "iv"
        const val TV = "tv"
    }
}
