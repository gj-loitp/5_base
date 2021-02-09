package vn.loitp.app.activity.demo.gallerycorealbumfrm

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.albumonly.GalleryCorePhotosOnlyFrm
import com.core.utilities.LScreenUtil
import vn.loitp.app.R

@LogTag("GalleryCoreAlbumFrmActivity")
@IsFullScreen(false)
class GalleryCoreAlbumFrmActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_gallery_core_album_frm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val frm = GalleryCorePhotosOnlyFrm(
                onTop = {
                    logD("onTop")
                },
                onBottom = {
                    logD("onBottom")
                },
                onScrolled = { isScrollDown ->
                    logD("onScrolled isScrollDown $isScrollDown")
                }
        )
        val bundle = Bundle()
        bundle.putString(Constants.SK_PHOTOSET_ID, Constants.FLICKR_ID_MANGA)
        bundle.putBoolean(GalleryCorePhotosOnlyFrm.IS_SHOW_TITLE, false)
        frm.arguments = bundle
        LScreenUtil.addFragment(
                activity = this,
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = frm,
                isAddToBackStack = false
        )
    }

}
