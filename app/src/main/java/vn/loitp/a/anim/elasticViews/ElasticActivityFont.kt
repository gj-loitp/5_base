package vn.loitp.a.anim.elasticViews

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_elastic_view.*
import vn.loitp.R

@LogTag("ElasticActivity")
@IsFullScreen(false)
class ElasticActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_elastic_view
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
            this.tvTitle?.text = ElasticActivityFont::class.java.simpleName
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
