package vn.loitp.app.activity.customviews.layout.constraintlayout.demo

import android.os.Bundle
import android.view.View
import android.widget.Button

import com.core.base.BaseFontActivity

import loitp.basemaster.R

class ConstraintlayoutDemoActivity : BaseFontActivity() {
    private var button: Button? = null
    private var bt0: Button? = null
    private var bt1: Button? = null
    private var bt2: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        button = findViewById(R.id.button)
        bt0 = findViewById(R.id.bt_0)
        bt1 = findViewById(R.id.bt_1)
        bt2 = findViewById(R.id.bt_2)
        button?.setOnClickListener { v -> button?.visibility = View.GONE }
        bt0?.setOnClickListener { v -> bt2?.visibility = View.GONE }
        bt1?.setOnClickListener { v -> bt2?.visibility = View.VISIBLE }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_constraintlayout_demo
    }
}
