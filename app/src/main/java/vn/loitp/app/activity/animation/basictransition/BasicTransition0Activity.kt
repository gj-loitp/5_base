package vn.loitp.app.activity.animation.basictransition

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair

import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil

import vn.loitp.app.R

class BasicTransition0Activity : BaseFontActivity() {
    private var tv: TextView? = null
    private var iv: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iv = findViewById(R.id.imageview_item)
        tv = findViewById(R.id.tv)
        LImageUtil.load(activity, Constants.URL_IMG_2, iv!!)
        findViewById<View>(R.id.imageview_item).setOnClickListener { onClickIv() }
    }

    private fun onClickIv() {
        val intent = Intent(activity, BasicTransition1Activity::class.java)
        val pair1 = Pair<View, String>(iv, BasicTransition1Activity.IV)
        val pair2 = Pair<View, String>(tv, BasicTransition1Activity.TV)
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
        return R.layout.activity_basic_transition_0
    }
}
