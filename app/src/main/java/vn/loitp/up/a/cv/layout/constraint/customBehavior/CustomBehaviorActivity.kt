package vn.loitp.up.a.cv.layout.constraint.customBehavior

import android.os.Bundle
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.ACustomBehaviorBinding

@LogTag("CustomBehaviorActivity")
@IsFullScreen(false)
class CustomBehaviorActivity : BaseActivityFont() {
    private lateinit var binding: ACustomBehaviorBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACustomBehaviorBinding.inflate(layoutInflater)
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
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CustomBehaviorActivity::class.java.simpleName
        }
        binding.fab.setSafeOnClickListener {
            Snackbar.make(
                binding.coordinatorLayout,
                "This is a simple Snackbar", Snackbar.LENGTH_LONG
            )
                .setAction("CLOSE") {
                    // do sth
                }.show()
        }
    }
}
