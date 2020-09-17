package vn.loitp.app.activity.demo.gallerycorealbumfrm

import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyFrm
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_gallery_core_album_frm)
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

}
