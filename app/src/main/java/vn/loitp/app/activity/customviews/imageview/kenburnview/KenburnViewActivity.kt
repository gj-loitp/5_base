package vn.loitp.app.activity.customviews.imageview.kenburnview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.views.imageview.kenburnview.LKenBurnsView
import com.views.imageview.kenburnview.LTransition
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_imageview_kenburn_view.*
import vn.loitp.app.R

//https://github.com/flavioarfaria/KenBurnsView

@LayoutId(R.layout.activity_imageview_kenburn_view)
@LogTag("KenburnViewActivity")
@IsFullScreen(false)
class KenburnViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LImageUtil.load(context = this, url = Constants.URL_IMG, imageView = kbv)
        kbv.setTransitionListener(object : LKenBurnsView.TransitionListener {
            override fun onTransitionEnd(LTransition: LTransition?) {
                //
            }

            override fun onTransitionStart(LTransition: LTransition?) {
                //
            }
        })
        btPause.setSafeOnClickListener {
            kbv.pause()
        }
        btResume.setSafeOnClickListener {
            kbv.resume()
        }

        tvMenu.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(context = this, url = "https://github.com/flavioarfaria/KenBurnsView")
        }
    }

}
