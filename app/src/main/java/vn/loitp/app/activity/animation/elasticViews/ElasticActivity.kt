package vn.loitp.app.activity.animation.elasticViews

import android.os.Bundle
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_elastic_view.*
import vn.loitp.app.R

@LogTag("MenuAnimationActivity")
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
