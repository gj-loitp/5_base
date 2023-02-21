package vn.loitp.up.a.cv.layout.coordinator

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.tranIn
import vn.loitp.databinding.AMenuCoordinatorLayoutBinding

// http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
@LogTag("MenuCoordinatorLayoutActivity")
@IsFullScreen(false)
class MenuCoordinatorLayoutActivity : BaseActivityFont(), View.OnClickListener {

    private lateinit var binding: AMenuCoordinatorLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuCoordinatorLayoutBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuCoordinatorLayoutActivity::class.java.simpleName
        }
        binding.bt0.setOnClickListener(this)
        binding.bt1.setOnClickListener(this)
        binding.bt2.setOnClickListener(this)
        binding.bt3.setOnClickListener(this)
        binding.bt4.setOnClickListener(this)
        binding.bt5.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            binding.bt0 -> intent =
                Intent(this, CoordinatorLayoutWithImageViewActivity::class.java)
            binding.bt1 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_0
                )
            }
            binding.bt2 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_1
                )
            }
            binding.bt3 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_2
                )
            }
            binding.bt4 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_3
                )
            }
            binding.bt5 -> {
                intent = Intent(this, CoordinatorLayoutSampleActivity::class.java)
                intent.putExtra(
                    CoordinatorLayoutSampleActivity.KEY,
                    CoordinatorLayoutSampleActivity.VALUE_4
                )
            }
        }
        intent?.let {
            startActivity(it)
            this.tranIn()
        }
    }
}
