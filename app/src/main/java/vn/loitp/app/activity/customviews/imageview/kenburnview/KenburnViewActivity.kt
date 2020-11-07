package vn.loitp.app.activity.customviews.imageview.kenburnview

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_imageview_kenburn_view.*
import vn.loitp.app.R

//https://github.com/flavioarfaria/KenBurnsView

@LogTag("KenburnViewActivity")
@IsFullScreen(false)
class KenburnViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_kenburn_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        LImageUtil.load(context = this, any = Constants.URL_IMG, imageView = kbv)
        kbv.setTransitionListener(object : KenBurnsView.TransitionListener {
            override fun onTransitionEnd(transition: Transition?) {
                //
            }

            override fun onTransitionStart(transition: Transition?) {
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
