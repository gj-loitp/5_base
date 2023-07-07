package vn.loitp.up.a.cv.androidRibbon

import android.graphics.Color
import android.os.Bundle
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.skydoves.androidribbon.RibbonView
import com.skydoves.androidribbon.ribbonView
import vn.loitp.R
import vn.loitp.databinding.ARibbonSecondBinding

@LogTag("SecondActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class SecondActivity : BaseActivityFont() {
    private lateinit var binding: ARibbonSecondBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ARibbonSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item1")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.colorPrimaryDark)
                setRibbonRadius(10f)
            }
        )
        binding.ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item2")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.purple)
            }
        )
        binding.ribbonRecyclerView.addRibbon(
            ribbonView = ribbonView(this@SecondActivity) {
                setText("item3")
                setTextColor(Color.WHITE)
                setRibbonBackgroundColorResource(R.color.deepPink)
            }
        )
        binding.ribbonRecyclerView.addRibbon(
            ribbonView = RibbonView.Builder(this@SecondActivity)
                .setText("Item4")
                .setTextColor(Color.WHITE)
                .setRibbonBackgroundColorResource(R.color.darkCyan)
                .build()
        )
    }
}
