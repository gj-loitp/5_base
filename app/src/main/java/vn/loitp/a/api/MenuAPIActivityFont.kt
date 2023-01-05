package vn.loitp.a.api

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import com.loitp.restApi.restClient.RestClient
import kotlinx.android.synthetic.main.a_menu_api.*
import vn.loitp.R
import vn.loitp.a.api.coroutine.a.CoroutineAPIActivityFont
import vn.loitp.a.api.galleryAPI.GalleryAPIActivityFont
import vn.loitp.a.api.retrofit2.TestAPIRetrofit2ActivityFont
import vn.loitp.a.api.truyentranhtuan.MenuTTTAPIActivityFont

@LogTag("MenuAPIActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAPIActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_api
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuAPIActivityFont::class.java.simpleName
        }
        btCoroutineAPI.setOnClickListener(this)
        btGalleryAPI.setOnClickListener(this)
        btComicAPI.setOnClickListener(this)
        btTestRetrofit2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btCoroutineAPI -> launchActivity(CoroutineAPIActivityFont::class.java)
            btGalleryAPI -> {
                RestClient.init(baseApiUrl = getString(R.string.flickr_URL))
                launchActivity(GalleryAPIActivityFont::class.java)
            }
            btComicAPI -> launchActivity(MenuTTTAPIActivityFont::class.java)
            btTestRetrofit2 -> launchActivity(TestAPIRetrofit2ActivityFont::class.java)
        }
    }
}
