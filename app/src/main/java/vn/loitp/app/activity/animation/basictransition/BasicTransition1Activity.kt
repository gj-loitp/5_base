package vn.loitp.app.activity.animation.basictransition

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

import androidx.core.view.ViewCompat

import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil

import loitp.basemaster.R

class BasicTransition1Activity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val iv = findViewById<ImageView>(R.id.imageview_item)
        val tv = findViewById<TextView>(R.id.tv)
        LImageUtil.load(activity, Constants.URL_IMG_2, iv)
        ViewCompat.setTransitionName(iv, IV)
        ViewCompat.setTransitionName(tv, TV)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_basic_transition_1
    }

    companion object {
        const val IV = "iv"
        const val TV = "tv"
    }
}
