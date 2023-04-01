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
import vn.loitp.up.a.cv.bt.autoSize.AutoSizeButtonActivity
import vn.loitp.up.a.cv.bt.circularImageClick.CircularImageClickActivity
import vn.loitp.up.a.cv.bt.circularProgress.CPBActivity
import vn.loitp.up.a.cv.bt.fab.FabActivity
import vn.loitp.up.a.cv.bt.fit.FitButtonActivity
import vn.loitp.up.a.cv.bt.goodView.GoodViewActivity
import vn.loitp.up.a.cv.bt.l.LButtonActivity
import vn.loitp.up.a.cv.bt.q.QButtonActivity
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
            binding.btCircularImageClick -> launchActivity(CircularImageClickActivity::class.java)
            binding.btGoodView -> launchActivity(GoodViewActivity::class.java)
            binding.btlButton -> launchActivity(LButtonActivity::class.java)
            binding.btAutoSizeButton -> launchActivity(AutoSizeButtonActivity::class.java)
            binding.btQButton -> launchActivity(QButtonActivity::class.java)
            binding.btFab -> launchActivity(FabActivity::class.java)
            binding.btFitButtonActivity -> launchActivity(FitButtonActivity::class.java)
            binding.btCircularProgressButton -> launchActivity(CPBActivity::class.java)
        }
    }
}
