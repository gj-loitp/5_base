package vn.loitp.app.activity.api

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.restapi.restclient.RestClient
import kotlinx.android.synthetic.main.activity_menu_api.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.activity.CoroutineAPIActivity
import vn.loitp.app.activity.api.galleryAPI.GalleryAPIActivity
import vn.loitp.app.activity.api.retrofit2.TestAPIRetrofit2Activity
import vn.loitp.app.activity.api.truyentranhtuan.TTTAPIMenuActivity

class MenuAPIActivity : BaseFontActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        btCoroutineAPI.setOnClickListener(this)
        btGalleryAPI.setOnClickListener(this)
        btComicAPI.setOnClickListener(this)
        btTestRetrofit2.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_api
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btCoroutineAPI -> {
                intent = Intent(activity, CoroutineAPIActivity::class.java)
            }
            btGalleryAPI -> {
                RestClient.init(getString(R.string.flickr_URL))
                intent = Intent(activity, GalleryAPIActivity::class.java)
            }
            btComicAPI -> {
                intent = Intent(activity, TTTAPIMenuActivity::class.java)
            }
            btTestRetrofit2 -> {
                intent = Intent(activity, TestAPIRetrofit2Activity::class.java)
            }
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(activity)
        }
    }
}
