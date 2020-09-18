package vn.loitp.app.activity.customviews.layout.constraintlayout.demo

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.annotation.LayoutId
import com.annotation.LogTag

import com.core.base.BaseFontActivity

import vn.loitp.app.R

@LayoutId(R.layout.activity_constraintlayout_demo)
@LogTag("ConstraintlayoutDemoActivity")
class ConstraintlayoutDemoActivity : BaseFontActivity() {
    private var button: Button? = null
    private var bt0: Button? = null
    private var bt1: Button? = null
    private var bt2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = findViewById(R.id.button)
        bt0 = findViewById(R.id.bt0)
        bt1 = findViewById(R.id.bt1)
        bt2 = findViewById(R.id.bt2)
        button?.setOnClickListener { _ -> button?.visibility = View.GONE }
        bt0?.setOnClickListener { _ -> bt2?.visibility = View.GONE }
        bt1?.setOnClickListener { _ -> bt2?.visibility = View.VISIBLE }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
