package vn.loitp.app.activity.api.truyentranhtuan

import android.content.Intent
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_api_ttt_menu.*
import vn.loitp.app.R

class TTTAPIMenuActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btComicList.setOnClickListener {
            val intent = Intent(activity, TTTAPIComicListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btChapList.setOnClickListener {
            val intent = Intent(activity, TTTAPIChapListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btPageList.setOnClickListener {
            val intent = Intent(activity, TTTAPIPageListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btFavList.setOnClickListener {
            val intent = Intent(activity, TTTAPIFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btAddToFavList.setOnClickListener {
            val intent = Intent(activity, TTTAPIAddFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
        btRemoveToFavList.setOnClickListener {
            val intent = Intent(activity, TTTAPIRemoveFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_menu
    }
}
