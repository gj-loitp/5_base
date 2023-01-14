package vn.loitp.a.cv.androidRibbon

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.skydoves.androidribbon.RibbonView
import com.skydoves.androidribbon.ribbonView
import kotlinx.android.synthetic.main.a_ribbon_second.*
import vn.loitp.R

@LogTag("SecondActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SecondActivity : BaseActivityFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_ribbon_second
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item1")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.colorPrimaryDark)
                setRibbonRadius(10f)
            }
        )
        ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item2")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.purple)
            }
        )
        ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item3")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.deepPink)
            }
        )
        ribbonRecyclerView.addRibbon(
            ribbonView = RibbonView.Builder(this@SecondActivity)
                .setText("Item4")
                .setTextColor(Color.WHITE)
                .setRibbonBackgroundColorResource(R.color.darkCyan)
                .build()
        )
    }
}
