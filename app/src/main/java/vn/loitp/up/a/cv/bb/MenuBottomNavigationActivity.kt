package vn.loitp.up.a.cv.bb

import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AMenuBottomNavigationBarBinding
import vn.loitp.up.a.cv.bb.bottomBar.BottomBarActivity
import vn.loitp.up.a.cv.bb.expandable.ShowCaseActivity

@LogTag("BottomNavigationMenuActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MenuBottomNavigationActivity : BaseActivityFont() {
    private lateinit var binding: AMenuBottomNavigationBarBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AMenuBottomNavigationBarBinding.inflate(layoutInflater)
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuBottomNavigationActivity::class.java.simpleName
        }
        binding.btBottomBarBlur.setSafeOnClickListener {
            launchActivity(BottomBarActivity::class.java)
        }
        binding.btExpandableBottomBar.setSafeOnClickListener {
            launchActivity(ShowCaseActivity::class.java)
        }
    }
}
