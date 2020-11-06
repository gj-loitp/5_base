package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.*
import vn.loitp.app.R

@LogTag("TTTAPIFavListActivity")
@IsFullScreen(false)
class TTTAPIFavListActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_fav_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btAdd.setSafeOnClickListener {

        }
        btRemove.setSafeOnClickListener {

        }
    }

//    btAddVuongPhongLoi.setOnClickListener {
//        val comic = Comic()
//        comic.date = "29.07.2014"
//        comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
//        comic.title = "Vương Phong Lôi I"
//        addComic(comic)
//    }
}