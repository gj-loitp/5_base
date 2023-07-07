package vn.loitp.up.a.anim.basicTransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AAnimationBasicTransition0Binding
import vn.loitp.up.common.Constants.Companion.URL_IMG_2

@LogTag("BasicTransition0Activity")
@IsFullScreen(false)
class BasicTransition0Activity : BaseActivityFont() {

    private lateinit var binding: AAnimationBasicTransition0Binding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAnimationBasicTransition0Binding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BasicTransition0Activity::class.java.simpleName
        }
        binding.imageViewItem.loadGlide(
            any = URL_IMG_2,
        )
        binding.imageViewItem.setOnClickListener {
            onClickIv()
        }
    }

    private fun onClickIv() {
        val intent = Intent(this, BasicTransition1Activity::class.java)
        val pair1 = Pair<View, String>(binding.imageViewItem, BasicTransition1Activity.IV)
        val pair2 = Pair<View, String>(binding.textView, BasicTransition1Activity.TV)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2)
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
    }
}
