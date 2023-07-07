package vn.loitp.up.a.cv.layout.constraint.demo

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AConstraintLayoutDemoBinding

@LogTag("ConstraintLayoutDemoActivity")
@IsFullScreen(false)
class ConstraintLayoutDemoActivity : BaseActivityFont() {

    private lateinit var binding: AConstraintLayoutDemoBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AConstraintLayoutDemoBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = ConstraintLayoutDemoActivity::class.java.simpleName
        }
        binding.button.setSafeOnClickListener {
            it.visibility = View.GONE
        }
        binding.bt0.setSafeOnClickListener {
            binding.bt2.visibility = View.GONE
        }
        binding.bt1.setSafeOnClickListener {
            binding.bt2.visibility = View.VISIBLE
        }
    }
}
