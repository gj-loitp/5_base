package vn.loitp.up.a.cv.layout.constraint

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.databinding.AMenuConstraintLayoutBinding
import vn.loitp.up.a.cv.layout.constraint.constraintSet.ConstraintSetActivityFont
import vn.loitp.up.a.cv.layout.constraint.customBehavior.CustomBehaviorActivityFont
import vn.loitp.up.a.cv.layout.constraint.demo.ConstraintLayoutDemoActivityFont
import vn.loitp.up.a.cv.layout.constraint.fabAndSnackbar.FabAndSnackBarActivity
import vn.loitp.up.a.cv.layout.constraint.fabFollowsWidget.FabFollowWidgetActivity

@LogTag("MenuConstraintlayoutActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuConstraintLayoutActivity : BaseActivityFont() {
    private lateinit var binding: AMenuConstraintLayoutBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuConstraintLayoutBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuConstraintLayoutActivity::class.java.simpleName
        }
        binding.btConstraintSet.setSafeOnClickListener {
            launchActivity(ConstraintSetActivityFont::class.java)
        }
        binding.btDemo.setSafeOnClickListener {
            launchActivity(ConstraintLayoutDemoActivityFont::class.java)
        }
        binding.btFabAndSnackBar.setSafeOnClickListener {
            launchActivity(FabAndSnackBarActivity::class.java)
        }
        binding.btFabFollowWidget.setSafeOnClickListener {
            launchActivity(FabFollowWidgetActivity::class.java)
        }
        binding.btCustomBehavior.setSafeOnClickListener {
            launchActivity(CustomBehaviorActivityFont::class.java)
        }
    }

}
