package vn.loitp.app.activity.customviews.layout.draggablepanelfree

import android.os.Bundle
import android.view.View
import android.widget.TextView

import com.core.base.BaseFontActivity
import com.views.layout.draggablepanelfree.DraggablePanelFreeLayout

import loitp.basemaster.R

class DraggablePanelFreeActivity : BaseFontActivity() {
    private var dpfl: DraggablePanelFreeLayout? = null
    private var tvState: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dpfl = findViewById(R.id.dpfl)
        tvState = findViewById(R.id.tv_state)
        findViewById<View>(R.id.bt_maximize).setOnClickListener { view -> dpfl!!.maximize() }
        findViewById<View>(R.id.bt_minimize).setOnClickListener { view -> dpfl!!.minimize() }
        dpfl!!.setCallback { state -> tvState!!.text = "onStateChange " + state.name }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_draggable_panel_free
    }
}
