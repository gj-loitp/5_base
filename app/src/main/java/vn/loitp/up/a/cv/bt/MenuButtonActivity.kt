package vn.loitp.up.a.cv.bt

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.a.cv.bt.autoSize.AutoSizeButtonActivityFont
import vn.loitp.a.cv.bt.circularImageClick.CircularImageClickActivityFont
import vn.loitp.a.cv.bt.circularProgress.CPBActivityFont
import vn.loitp.a.cv.bt.fab.FabActivityFont
import vn.loitp.a.cv.bt.fit.FitButtonActivityFont
import vn.loitp.a.cv.bt.goodView.GoodViewActivityFont
import vn.loitp.a.cv.bt.l.LButtonActivityFont
import vn.loitp.a.cv.bt.q.QButtonActivityFont
import vn.loitp.up.a.cv.bt.shine.ShineButtonActivity
import vn.loitp.databinding.ActivityMenuButtonBinding

@LogTag("MenuButtonActivity")
@IsFullScreen(false)
class MenuButtonActivity : BaseActivityFont(), OnClickListener {
    private lateinit var binding: ActivityMenuButtonBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuButtonBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = MenuButtonActivity::class.java.simpleName
        }
        binding.btShineButton.setOnClickListener(this)
        binding.btCircularImageClick.setOnClickListener(this)
        binding.btGoodView.setOnClickListener(this)
        binding.btlButton.setOnClickListener(this)
        binding.btAutoSizeButton.setOnClickListener(this)
        binding.btQButton.setOnClickListener(this)
        binding.btFab.setOnClickListener(this)
        binding.btFitButtonActivity.setOnClickListener(this)
        binding.btCircularProgressButton.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            binding.btShineButton -> launchActivity(ShineButtonActivity::class.java)
            binding.btCircularImageClick -> launchActivity(CircularImageClickActivityFont::class.java)
            binding.btGoodView -> launchActivity(GoodViewActivityFont::class.java)
            binding.btlButton -> launchActivity(LButtonActivityFont::class.java)
            binding.btAutoSizeButton -> launchActivity(AutoSizeButtonActivityFont::class.java)
            binding.btQButton -> launchActivity(QButtonActivityFont::class.java)
            binding.btFab -> launchActivity(FabActivityFont::class.java)
            binding.btFitButtonActivity -> launchActivity(FitButtonActivityFont::class.java)
            binding.btCircularProgressButton -> launchActivity(CPBActivityFont::class.java)
        }
    }
}
