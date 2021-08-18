package vn.loitp.app.activity.customviews.button.goodview

import android.graphics.Color
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.button.goodview.LGoodView
import kotlinx.android.synthetic.main.activity_button_goodview.*
import vn.loitp.app.R

//https://github.com/venshine/GoodView

@LogTag("GoodViewActivity")
@IsFullScreen(false)
class GoodViewActivity : BaseFontActivity() {

    private var lGoodView: LGoodView? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_button_goodview
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lGoodView = LGoodView(this)
        bt.setOnClickListener {
            lGoodView?.let {
                it.setText("+1")
                it.show(v = bt)
            }
        }
        imageView.setOnClickListener {
            imageView.setColorFilter(Color.TRANSPARENT)
            lGoodView?.apply {
                this.setImage(R.drawable.ic_launcher)
                //this.setDistance(1000)
                //this.setTranslateY(0, 10000)
                //this.setAlpha(0, 0.5f)
                //this.setDuration(3000)
                this.show(it)
            }
        }
    }
}
