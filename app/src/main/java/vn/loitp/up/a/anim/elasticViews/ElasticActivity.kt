package vn.loitp.up.a.anim.elasticViews

import android.os.Bundle
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setOnClickListenerElastic
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AElasticViewBinding

@LogTag("ElasticActivity")
@IsFullScreen(false)
class ElasticActivity : BaseActivityFont() {
    private lateinit var binding: AElasticViewBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AElasticViewBinding.inflate(layoutInflater)
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
            this.tvTitle?.text = ElasticActivity::class.java.simpleName
        }

        // don't remove this code below
        binding.elasticButton.setSafeOnClickListener {
        }
        binding.elasticCheckButton.setSafeOnClickListener {
        }
        binding.elasticImageView.setSafeOnClickListener {
        }
        binding.elasticFloatingActionButton.setSafeOnClickListener {
        }
        binding.elasticCardView.setSafeOnClickListener {
        }
        binding.anyView.setOnClickListenerElastic(
            runnable = {
                showShortInformation("Finish setOnClickListenerElastic")
            }
        )
        binding.anyView2.setSafeOnClickListenerElastic(
            runnable = {
                showShortInformation("Finish setSafeOnClickListenerElastic")
            }
        )
    }
}
