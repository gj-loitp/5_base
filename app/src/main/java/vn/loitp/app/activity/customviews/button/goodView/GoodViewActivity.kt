package vn.loitp.app.activity.customviews.button.goodView

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.button.goodView.LGoodView
import kotlinx.android.synthetic.main.activity_good_view.*
import vn.loitp.app.R

@LogTag("GoodViewActivity")
@IsFullScreen(false)
class GoodViewActivity : BaseFontActivity() {

    private var lGoodView: LGoodView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_good_view
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
                            url = "https://github.com/venshine/GoodView"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = GoodViewActivity::class.java.simpleName
        }

        lGoodView = LGoodView(this)
        bt.setOnClickListener {
            lGoodView?.let {
                it.setText("+1")
                it.show(v = bt)
            }
        }
        imageView.setOnClickListener {
            imageView.setColorFilter(Color.TRANSPARENT)
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
