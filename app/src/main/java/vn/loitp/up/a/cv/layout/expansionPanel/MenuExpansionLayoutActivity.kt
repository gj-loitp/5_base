package vn.loitp.up.a.cv.layout.expansionPanel

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuExpansionPanelBinding

@LogTag("MenuExpansionLayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuExpansionLayoutActivity : BaseActivityFont() {
    private lateinit var binding: AMenuExpansionPanelBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuExpansionPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/florent37/ExpansionPanel"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MenuExpansionLayoutActivity::class.java.simpleName
        }
        binding.btSample.setSafeOnClickListener {
            launchActivity(ExpansionPanelSampleActivity::class.java)
        }
        binding.btSampleViewgroup.setSafeOnClickListener {
            launchActivity(ExpansionPanelSampleActivityViewGroup::class.java)
        }
        binding.btProgrammatically.setSafeOnClickListener {
            launchActivity(ExpansionPanelSampleActivityProgrammatically::class.java)
        }
        binding.btRecyclerView.setSafeOnClickListener {
            launchActivity(ExpansionPanelSampleActivityRecycler::class.java)
        }
    }
}
