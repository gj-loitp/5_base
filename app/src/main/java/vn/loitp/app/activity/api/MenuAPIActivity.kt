package vn.loitp.app.activity.api

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.restapi.restclient.RestClient
import kotlinx.android.synthetic.main.activity_api_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.activity.CoroutineAPIActivity
import vn.loitp.app.activity.api.galleryAPI.GalleryAPIActivity
import vn.loitp.app.activity.api.retrofit2.TestAPIRetrofit2Activity
import vn.loitp.app.activity.api.truyentranhtuan.TTTAPIMenuActivity

@LogTag("MenuAPIActivity")
@IsFullScreen(false)
class MenuAPIActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews(){
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
                intent = Intent(this, TTTAPIMenuActivity::class.java)
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
