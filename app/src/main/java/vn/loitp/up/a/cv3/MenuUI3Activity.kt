package vn.loitp.up.a.cv3

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuCv3Binding

//https://m3.material.io/components
@LogTag("MenuUI3Activity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class MenuUI3Activity : BaseActivityFont() {

    private lateinit var binding: AMenuCv3Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuCv3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://m3.material.io/components"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MenuUI3Activity::class.java.simpleName
        }
        binding.btCommonButton.setOnClickListener {
            binding.layoutCommonButton.root.isVisible = !binding.layoutCommonButton.root.isVisible
        }
    }

}
