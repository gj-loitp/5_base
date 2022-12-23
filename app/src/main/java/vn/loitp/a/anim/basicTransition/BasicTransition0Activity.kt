package vn.loitp.a.anim.basicTransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.common.Constants
import com.loitp.core.utilities.LImageUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_animation_basic_transition_0.*
import vn.loitp.R

@LogTag("BasicTransition0Activity")
@IsFullScreen(false)
class BasicTransition0Activity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_basic_transition_0
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
            this.tvTitle?.text = BasicTransition0Activity::class.java.simpleName
        }
        LImageUtil.load(
            context = this,
            any = Constants.URL_IMG_2,
            imageView = imageViewItem,
        )
        imageViewItem.setOnClickListener {
            onClickIv()
        }
    }

    private fun onClickIv() {
        val intent = Intent(this, BasicTransition1Activity::class.java)
        val pair1 = Pair<View, String>(imageViewItem, BasicTransition1Activity.IV)
        val pair2 = Pair<View, String>(textView, BasicTransition1Activity.TV)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2)
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
    }
}