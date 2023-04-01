package vn.loitp.up.a.cv.bt.goodView

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.bt.goodView.LGoodView
import vn.loitp.R
import vn.loitp.databinding.AGoodViewBinding

@LogTag("GoodViewActivity")
@IsFullScreen(false)
class GoodViewActivity : BaseActivityFont() {
    private lateinit var binding: AGoodViewBinding
    private var lGoodView: LGoodView? = null

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AGoodViewBinding.inflate(layoutInflater)
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
                            url = "https://github.com/venshine/GoodView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = GoodViewActivity::class.java.simpleName
        }

        lGoodView = LGoodView(this)
        binding.bt.setOnClickListener {
            lGoodView?.let {
                it.setText("+1")
                it.show(v = binding.bt)
            }
        }
        binding.imageView.setOnClickListener {
            binding.imageView.setColorFilter(Color.TRANSPARENT)
            lGoodView?.apply {
                this.setImage(R.drawable.ic_account_circle_black_48dp)
                // this.setDistance(1000)
                // this.setTranslateY(0, 10000)
                // this.setAlpha(0, 0.5f)
                // this.setDuration(3000)
                this.show(it)
            }
        }
    }
}
