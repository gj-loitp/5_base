package vn.loitp.a.api.truyentranhtuan

import android.content.Intent
import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_ttt_api.*
import vn.loitp.R

@LogTag("TTTAPIMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuTTTAPIActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_ttt_api
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
            this.tvTitle?.text = MenuTTTAPIActivityFont::class.java.simpleName
        }
        btComicList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIComicListActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btChapList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIChapListActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btPageList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIPageListActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
        btFavList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIFavListActivityFont::class.java)
            startActivity(intent)
            this.tranIn()
        }
    }
}
