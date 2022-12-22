package vn.loitp.activity.api

import android.os.Bundle
import android.view.View
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitp.restApi.restClient.RestClient
import kotlinx.android.synthetic.main.activity_menu_api.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.activity.CoroutineAPIActivity
import vn.loitp.app.activity.api.galleryAPI.GalleryAPIActivity
import vn.loitp.app.activity.api.retrofit2.TestAPIRetrofit2Activity
import vn.loitp.app.activity.api.truyentranhtuan.MenuTTTAPIActivity

@LogTag("MenuAPIActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuAPIActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_api
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
            this.tvTitle?.text = MenuAPIActivity::class.java.simpleName
        }
        btCoroutineAPI.setOnClickListener(this)
        btGalleryAPI.setOnClickListener(this)
        btComicAPI.setOnClickListener(this)
        btTestRetrofit2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            btCoroutineAPI -> launchActivity(CoroutineAPIActivity::class.java)
            btGalleryAPI -> {
                RestClient.init(baseApiUrl = getString(R.string.flickr_URL))
                launchActivity(GalleryAPIActivity::class.java)
            }
            btComicAPI -> launchActivity(MenuTTTAPIActivity::class.java)
            btTestRetrofit2 -> launchActivity(TestAPIRetrofit2Activity::class.java)
        }
    }
}
