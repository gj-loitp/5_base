package vn.loitp.app.activity.customviews.layout.expansionpanel

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_expansion_panel_menu.*
import vn.loitp.app.R

// https://github.com/florent37/ExpansionPanel

@LogTag("ExpansionLayoutMenuActivity")
@IsFullScreen(false)
class ExpansionLayoutMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_expansion_panel_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
