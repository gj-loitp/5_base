package vn.loitp.up.a.anim.activityTransition

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.AAnimation2Binding

@LogTag("Animation2Activity")
@IsFullScreen(false)
class Animation2Activity : BaseActivityFont() {

    private lateinit var binding: AAnimation2Binding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimation2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.tvExit.setSafeOnClickListener {
            onBaseBackPressed()
        }
    }
}
