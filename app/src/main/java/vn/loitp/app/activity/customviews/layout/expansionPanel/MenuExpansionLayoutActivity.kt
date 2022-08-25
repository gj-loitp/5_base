package vn.loitp.app.activity.customviews.layout.expansionPanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LActivityUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_menu_expansion_panel.*
import vn.loitp.app.R

@LogTag("MenuExpansionLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuExpansionLayoutActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_expansion_panel
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
                    onBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/florent37/ExpansionPanel"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = MenuExpansionLayoutActivity::class.java.simpleName
        }
        btSample.setOnClickListener(this)
        btSampleViewgroup.setOnClickListener(this)
        btProgrammatically.setOnClickListener(this)
        btRecyclerView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        var intent: Intent? = null
        when (v) {
            btSample -> {
                intent = Intent(this, ExpansionPanelSampleActivity::class.java)
            }
            btSampleViewgroup -> {
                intent = Intent(this, ExpansionPanelSampleActivityViewGroup::class.java)
            }
            btProgrammatically -> {
                intent = Intent(this, ExpansionPanelSampleActivityProgrammatically::class.java)
            }
            btRecyclerView -> {
                intent = Intent(this, ExpansionPanelSampleActivityRecycler::class.java)
            }
        }
        intent?.let {
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }
}
