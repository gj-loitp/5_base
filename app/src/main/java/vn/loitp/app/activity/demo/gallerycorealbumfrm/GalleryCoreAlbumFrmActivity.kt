package vn.loitp.app.activity.demo.gallerycorealbumfrm

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyFrm
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

class GalleryCoreAlbumFrmActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frm = GalleryCorePhotosOnlyFrm()
        val bundle = Bundle()
        bundle.putString(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_MANGA)
        frm.arguments = bundle
        LScreenUtil.addFragment(activity = activity, containerFrameLayoutIdRes = R.id.flContainer, fragment = frm, isAddToBackStack = false)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_gallery_core_album_frm
    }
}
