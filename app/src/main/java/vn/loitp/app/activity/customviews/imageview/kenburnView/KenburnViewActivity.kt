package vn.loitp.app.activity.customviews.imageview.kenburnView

import android.os.Bundle
import androidx.core.view.isVisible
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LImageUtil
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_kenburn_view.*
import vn.loitp.app.R
import vn.loitp.app.activity.EmptyActivity

@LogTag("KenburnViewActivity")
@IsFullScreen(false)
class KenburnViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_kenburn_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/flavioarfaria/KenBurnsView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
        LImageUtil.load(context = this, any = Constants.URL_IMG, imageView = kbv)
        kbv.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                //
            }

            override fun onTransitionStart(transition: Transition?) {
                //
            }
        })
        btPause.setSafeOnClickListener {
            kbv.pause()
        }
        btResume.setSafeOnClickListener {
            kbv.resume()
        }
    }
}
