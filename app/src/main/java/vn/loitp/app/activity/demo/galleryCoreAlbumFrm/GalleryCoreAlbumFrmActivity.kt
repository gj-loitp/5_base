package vn.loitp.app.activity.demo.galleryCoreAlbumFrm

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyFrm
import com.loitpcore.core.utilities.LScreenUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_demo_gallery_core_album_frm.*
import vn.loitp.app.R

@LogTag("GalleryCoreAlbumFrmActivity")
@IsFullScreen(false)
class GalleryCoreAlbumFrmActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_gallery_core_album_frm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = GalleryCoreAlbumFrmActivity::class.java.simpleName
        }
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
