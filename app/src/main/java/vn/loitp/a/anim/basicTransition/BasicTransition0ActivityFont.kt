package vn.loitp.a.anim.basicTransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListenerElastic
import kotlinx.android.synthetic.main.a_animation_basic_transition_0.*
import vn.loitp.R
import vn.loitp.up.common.Constants.Companion.URL_IMG_2

@LogTag("BasicTransition0Activity")
@IsFullScreen(false)
class BasicTransition0ActivityFont : BaseActivityFont() {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_animation_basic_transition_0
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
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = BasicTransition0ActivityFont::class.java.simpleName
        }
        imageViewItem.loadGlide(
            any = URL_IMG_2,
        )
        imageViewItem.setOnClickListener {
            onClickIv()
        }
    }

    private fun onClickIv() {
        val intent = Intent(this, BasicTransition1ActivityFont::class.java)
        val pair1 = Pair<View, String>(imageViewItem, BasicTransition1ActivityFont.IV)
        val pair2 = Pair<View, String>(textView, BasicTransition1ActivityFont.TV)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair1, pair2)
        ActivityCompat.startActivity(this, intent, activityOptions.toBundle())
    }
}
