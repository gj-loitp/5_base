package vn.loitp.app.activity.api

import android.content.Intent
import android.os.Bundle
import android.view.View
import loitp.basemaster.R
import vn.loitp.app.activity.api.galleryAPI.GalleryAPIActivity
import vn.loitp.app.activity.api.retrofit2.TestAPIRetrofit2Activity
import vn.loitp.app.activity.api.truyentranhtuan.TTTAPIMenuActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil
import vn.loitp.restapi.restclient.RestClient

class MenuAPIActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_2).setOnClickListener {
            RestClient.init(getString(R.string.flickr_URL))
            val intent = Intent(activity, GalleryAPIActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt_3).setOnClickListener {
            val intent = Intent(activity, TTTAPIMenuActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        findViewById<View>(R.id.bt_test_retrofit2).setOnClickListener {
            val intent = Intent(activity, TestAPIRetrofit2Activity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
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
}
