package vn.loitp.app.activity.customviews.imageview.kenburnview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.views.imageview.kenburnview.LKenBurnsView
import com.views.imageview.kenburnview.LTransition
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_kenburn_view.*
import vn.loitp.app.R

//https://github.com/flavioarfaria/KenBurnsView
class KenburnViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(activity, Constants.URL_IMG, kbv)
        kbv.setTransitionListener(object : LKenBurnsView.TransitionListener {
            override fun onTransitionEnd(LTransition: LTransition?) {
                //
            }

            override fun onTransitionStart(LTransition: LTransition?) {
                //
            }
        })
        btPause.setOnClickListener {
            kbv.pause()
        }
        btResume.setOnClickListener {
            kbv.resume()
        }

        tvMenu.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(activity, "https://github.com/flavioarfaria/KenBurnsView")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_kenburn_view
    }
}
