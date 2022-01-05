package vn.loitp.app.activity.customviews.lcardview

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LAnimationUtil
import com.core.utilities.LScreenUtil
import com.daimajia.androidanimations.library.Techniques
import com.views.card.LCardView
import kotlinx.android.synthetic.main.activity_card_view_l.*
import vn.loitp.app.R

@LogTag("LCardViewActivity")
@IsFullScreen(false)
class LCardViewActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_card_view_l
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lCardView0.apply {
            callback = object : LCardView.Callback {
                override fun onClickRoot(v: View) {
                    // LAnimationUtil.play(v, Techniques.Pulse)
                }

                override fun onLongClickRoot(v: View) {
                    // LAnimationUtil.play(v, Techniques.Pulse)
                }

                override fun onClickText(v: View) {
                    LAnimationUtil.play(view = v, techniques = Techniques.Pulse)
                }

                override fun onLongClickText(v: View) {
                    LAnimationUtil.play(view = v, techniques = Techniques.Pulse)
                }
            }
            setText(System.currentTimeMillis().toString() + "")
            height = 300
            width = LScreenUtil.screenWidth * 3 / 4
            setRadius(30f)
            setCardElevation(10f)
            setImg(Constants.URL_IMG_2)
        }

        lCardView1.apply {
            setText(System.currentTimeMillis().toString() + "")
            height = LScreenUtil.screenHeight * 3 / 2
            setRadius(50f)
            setCardElevation(20f)
            setImg(Constants.URL_IMG_4)
        }
    }
}
