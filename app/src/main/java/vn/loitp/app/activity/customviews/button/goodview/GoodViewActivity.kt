package vn.loitp.app.activity.customviews.button.goodview

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.button.goodview.LGoodView
import kotlinx.android.synthetic.main.activity_button_goodview.*
import vn.loitp.app.R

//https://github.com/venshine/GoodView

@LayoutId(R.layout.activity_button_goodview)
@LogTag("GoodViewActivity")
@IsFullScreen(false)
class GoodViewActivity : BaseFontActivity() {
    private var lGoodView: LGoodView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lGoodView = LGoodView(this)
        bt.setOnClickListener { v: View? ->
            lGoodView?.let {
                it.setText("+1")
                it.show(v)
            }
        }
        imageView.setOnClickListener { v: View? ->
            imageView.setColorFilter(Color.TRANSPARENT)
            lGoodView?.let {
                it.setImage(R.mipmap.ic_launcher)
                //it.setDistance(1000)
                //it.setTranslateY(0, 10000)
                //it.setAlpha(0, 0.5f)
                //it.setDuration(3000)
                it.show(v)
            }
        }
    }
}
