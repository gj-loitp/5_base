package vn.loitp.up.a.cv.layout.coordinator

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.addFragment
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ACoordinatorLayoutSampleBinding

// http://karthikraj.net/2016/12/24/scrolling-behavior-for-appbars-in-android/
@LogTag("CoordinatorLayoutSampleActivity")
@IsFullScreen(false)
class CoordinatorLayoutSampleActivity : BaseActivityFont() {

    companion object {
        const val KEY = "KEY"
        const val VALUE_0 = "Standard App bar scrolling with only Toolbar"
        const val VALUE_1 = "App bar scrolling with tabs"
        const val VALUE_2 = "App bar scrolling with Flexible space"
        const val VALUE_3 = "App bar scrolling with overlapping content in Flexible space"
        const val VALUE_4 = "Recyclerview"
    }

    private lateinit var binding: ACoordinatorLayoutSampleBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ACoordinatorLayoutSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
        when (intent.getStringExtra(KEY)) {
            VALUE_0 -> this.addFragment(
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator0(),
                isAddToBackStack = false
            )
            VALUE_1 -> this.addFragment(
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator1(),
                isAddToBackStack = false
            )
            VALUE_2 -> this.addFragment(
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator2(),
                isAddToBackStack = false
            )
            VALUE_3 -> this.addFragment(
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator3(),
                isAddToBackStack = false
            )
            VALUE_4 -> this.addFragment(
                containerFrameLayoutIdRes = R.id.flContainer,
                fragment = FrmCoordinator4(),
                isAddToBackStack = false
            )
        }
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = CoordinatorLayoutSampleActivity::class.java.simpleName
        }
    }
}
