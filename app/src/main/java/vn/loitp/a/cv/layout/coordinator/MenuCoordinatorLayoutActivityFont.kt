package vn.loitp.a.cv.layout.coordinator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.tranIn
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_menu_coordinator_layout.*
import vn.loitp.R

// http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
@LogTag("MenuCoordinatorLayoutActivity")
@IsFullScreen(false)
class MenuCoordinatorLayoutActivityFont : BaseActivityFont(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_menu_coordinator_layout
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MenuCoordinatorLayoutActivityFont::class.java.simpleName
        }
        bt0.setOnClickListener(this)
        bt1.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        bt4.setOnClickListener(this)
        bt5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            bt0 -> intent = Intent(this, CoordinatorLayoutWithImageViewActivityFont::class.java)
            bt1 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivityFont::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivityFont.KEY,
                    CoordinatorLayoutSampleActivityFont.VALUE_0
                )
            }
            bt2 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivityFont::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivityFont.KEY,
                    CoordinatorLayoutSampleActivityFont.VALUE_1
                )
            }
            bt3 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivityFont::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivityFont.KEY,
                    CoordinatorLayoutSampleActivityFont.VALUE_2
                )
            }
            bt4 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivityFont::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivityFont.KEY,
                    CoordinatorLayoutSampleActivityFont.VALUE_3
                )
            }
            bt5 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivityFont::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivityFont.KEY,
                    CoordinatorLayoutSampleActivityFont.VALUE_4
                )
            }
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
