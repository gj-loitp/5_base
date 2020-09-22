package vn.loitp.app.activity.api.truyentranhtuan

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_api_ttt_menu.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_api_ttt_menu)
@LogTag("TTTAPIMenuActivity")
@IsFullScreen(false)
class TTTAPIMenuActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btComicList.setOnClickListener {
            val intent = Intent(this, TTTAPIComicListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btChapList.setOnClickListener {
            val intent = Intent(this, TTTAPIChapListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btPageList.setOnClickListener {
            val intent = Intent(this, TTTAPIPageListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btFavList.setOnClickListener {
            val intent = Intent(this, TTTAPIFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btAddToFavList.setOnClickListener {
            val intent = Intent(this, TTTAPIAddFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btRemoveToFavList.setOnClickListener {
            val intent = Intent(this, TTTAPIRemoveFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }

}
