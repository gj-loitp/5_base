package vn.loitp.a.cv.iv.kenburn

import android.os.Bundle
import androidx.core.view.isVisible
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_kenburn_view.*
import vn.loitp.R
import vn.loitp.up.app.EmptyActivity

@LogTag("KenburnViewActivity")
@IsFullScreen(false)
class KenburnViewActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_kenburn_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/flavioarfaria/KenBurnsView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = EmptyActivity::class.java.simpleName
        }
        kbv.loadGlide(
            any = URL_IMG,
        )
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
