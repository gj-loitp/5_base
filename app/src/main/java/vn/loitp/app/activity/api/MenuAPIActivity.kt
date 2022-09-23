package vn.loitp.app.activity.api

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.restApi.restClient.RestClient
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
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuAPIActivity::class.java.simpleName
        }
        btCoroutineAPI.setOnClickListener(this)
        btGalleryAPI.setOnClickListener(this)
        btComicAPI.setOnClickListener(this)
        btTestRetrofit2.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btCoroutineAPI -> {
                intent = Intent(this, CoroutineAPIActivity::class.java)
            }
            btGalleryAPI -> {
                RestClient.init(getString(R.string.flickr_URL))
                intent = Intent(this, GalleryAPIActivity::class.java)
            }
            btComicAPI -> {
                intent = Intent(this, MenuTTTAPIActivity::class.java)
            }
            btTestRetrofit2 -> {
                intent = Intent(this, TestAPIRetrofit2Activity::class.java)
            }
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(this)
        }
    }
}
