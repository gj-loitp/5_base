package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerview

import android.os.Bundle

import androidx.recyclerview.widget.RecyclerView

import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil

import vn.loitp.app.R

class ParallaxRecyclerViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        recyclerView.adapter = ParallaxAdapter(this)

        LUIUtil.setPullLikeIOSVertical(recyclerView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_parallax_recycler_view
    }
}
