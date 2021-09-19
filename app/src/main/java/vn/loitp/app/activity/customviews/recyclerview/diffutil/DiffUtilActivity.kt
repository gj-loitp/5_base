package vn.loitp.app.activity.customviews.recyclerview.diffutil

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_recycler_view_diff_util.*
import vn.loitp.app.R
import java.util.*

@LogTag("DiffUtilActivity")
@IsFullScreen(false)
class DiffUtilActivity : BaseFontActivity() {

    private var items: MutableList<Content> = mutableListOf()

    private fun generate(): List<Content> {
        val rand = Random(System.currentTimeMillis())
        return items.filter { rand.nextBoolean() }
    }

    fun add(): List<Content> {
        items.add(Content(1, "Loitp ${System.currentTimeMillis()}", Constants.URL_IMG_ANDROID))
        adapter.notifyDataSetChanged()
        return items
    }

    private fun shuffle(): List<Content> {
        items.shuffle()
        return items
    }

    val adapter = ContentAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_diff_util
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rv.layoutManager = GridLayoutManager(this, 3)

//        rv.adapter = adapter

        val scaleAdapter = AlphaInAnimationAdapter(adapter)
//        val scaleAdapter = ScaleInAnimationAdapter(it)
//        val scaleAdapter = SlideInBottomAnimationAdapter(it)
//        val scaleAdapter = SlideInLeftAnimationAdapter(it)
//        val scaleAdapter = SlideInRightAnimationAdapter(it)

        scaleAdapter.setDuration(1000)
        scaleAdapter.setInterpolator(OvershootInterpolator())
        scaleAdapter.setFirstOnly(true)
        rv.adapter = scaleAdapter

        for (i in 1..10) {
            items.add(Content(i, "Item $i", Constants.URL_IMG_ANDROID))
        }
        adapter.items = items
        bt.setOnClickListener {
            adapter.items = generate()
        }
        btAdd.setOnClickListener {
            adapter.items = add()
        }
        btShuffle.setOnClickListener {
            adapter.items = shuffle()
        }
    }
}
