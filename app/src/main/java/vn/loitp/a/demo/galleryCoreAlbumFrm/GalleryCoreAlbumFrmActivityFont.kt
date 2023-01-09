package vn.loitp.a.demo.galleryCoreAlbumFrm

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.FLICKR_ID_MANGA
import com.loitp.core.common.SK_PHOTOSET_ID
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.helper.gallery.albumOnly.GalleryCorePhotosOnlyFrm
import kotlinx.android.synthetic.main.a_demo_gallery_core_album_frm.*
import vn.loitp.R

@LogTag("GalleryCoreAlbumFrmActivity")
@IsFullScreen(false)
class GalleryCoreAlbumFrmActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_gallery_core_album_frm
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = GalleryCoreAlbumFrmActivityFont::class.java.simpleName
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
        bundle.putString(SK_PHOTOSET_ID, FLICKR_ID_MANGA)
        bundle.putBoolean(GalleryCorePhotosOnlyFrm.IS_SHOW_TITLE, false)
        frm.arguments = bundle
        this.addFragment(
            containerFrameLayoutIdRes = R.id.flContainer,
            fragment = frm,
            isAddToBackStack = false
        )
    }
}
