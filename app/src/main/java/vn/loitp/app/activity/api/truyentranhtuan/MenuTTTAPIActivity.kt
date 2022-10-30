package vn.loitp.app.activity.api.truyentranhtuan

import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_ttt_api.*
import vn.loitp.app.R

@LogTag("TTTAPIMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuTTTAPIActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_ttt_api
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
            this.tvTitle?.text = MenuTTTAPIActivity::class.java.simpleName
        }
        btComicList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIComicListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btChapList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIChapListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btPageList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIPageListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btFavList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
