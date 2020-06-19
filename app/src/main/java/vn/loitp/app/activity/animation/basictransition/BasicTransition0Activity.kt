package vn.loitp.app.activity.animation.basictransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_animation_basic_transition_0.*
import vn.loitp.app.R

class BasicTransition0Activity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(context = activity, url = Constants.URL_IMG_2, imageView = imageViewItem)
        imageViewItem.setOnClickListener {
            onClickIv()
        }
    }

    private fun onClickIv() {
        val intent = Intent(activity, BasicTransition1Activity::class.java)
        val pair1 = Pair<View, String>(imageViewItem, BasicTransition1Activity.IV)
        val pair2 = Pair<View, String>(textView, BasicTransition1Activity.TV)
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair1, pair2)
        ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_basic_transition_0
    }
}
