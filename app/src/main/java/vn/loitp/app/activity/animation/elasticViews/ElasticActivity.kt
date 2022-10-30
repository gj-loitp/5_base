package vn.loitp.app.activity.animation.elasticViews

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_elastic_view.*
import vn.loitp.app.R

@LogTag("ElasticActivity")
@IsFullScreen(false)
class ElasticActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_elastic_view
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = ElasticActivity::class.java.simpleName
        }

        // don't remove this code below
        elasticButton.setSafeOnClickListener {
        }
        elasticCheckButton.setSafeOnClickListener {
        }
        elasticImageView.setSafeOnClickListener {
        }
        elasticFloatingActionButton.setSafeOnClickListener {
        }
        elasticCardView.setSafeOnClickListener {
        }
        LUIUtil.setOnClickListenerElastic(
            view = anyView,
            runnable = {
                showShortInformation("Finish setOnClickListenerElastic")
            }
        )
        LUIUtil.setSafeOnClickListenerElastic(
            view = anyView2,
            runnable = {
                showShortInformation("Finish setSafeOnClickListenerElastic")
            }
        )
    }
}
