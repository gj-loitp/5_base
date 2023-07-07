package vn.loitp.up.a.cv.iv.kenburn

import android.os.Bundle
import androidx.core.view.isVisible
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.common.URL_IMG
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AKenburnViewBinding
import vn.loitp.up.app.EmptyActivity

@LogTag("KenburnViewActivity")
@IsFullScreen(false)
class KenburnViewActivity : BaseActivityFont() {
    private lateinit var binding: AKenburnViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AKenburnViewBinding.inflate(layoutInflater)
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
        binding.kbv.loadGlide(
            any = URL_IMG,
        )
        binding.kbv.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                //
            }

            override fun onTransitionStart(transition: Transition?) {
                //
            }
        })
        binding.btPause.setSafeOnClickListener {
            binding.kbv.pause()
        }
        binding.btResume.setSafeOnClickListener {
            binding.kbv.resume()
        }
    }
}
