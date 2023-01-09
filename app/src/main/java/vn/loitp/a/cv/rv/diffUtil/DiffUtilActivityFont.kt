package vn.loitp.a.cv.rv.diffUtil

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG_ANDROID
import com.loitp.core.ext.setSafeOnClickListener
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.a_rv_diff_util.*
import vn.loitp.R
import java.util.*

@LogTag("DiffUtilActivity")
@IsFullScreen(false)
class DiffUtilActivityFont : BaseActivityFont() {

    private var items: MutableList<Content> = mutableListOf()

    private fun generate(): List<Content> {
        val rand = Random(System.currentTimeMillis())
        return items.filter { rand.nextBoolean() }
    }

    fun add(): List<Content> {
        items.add(
            Content(
                id = 1,
                text = "Loitp ${System.currentTimeMillis()}",
                image = URL_IMG_ANDROID
            )
        )
//        adapter.notifyDataSetChanged()
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        return items
    }

    private fun shuffle(): List<Content> {
        items.shuffle()
        return items
    }

    val adapter = ContentAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_rv_diff_util
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
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
            items.add(Content(i, "Item $i", URL_IMG_ANDROID))
        }
        adapter.items = items
        bt.setSafeOnClickListener {
            adapter.items = generate()
        }
        btAdd.setSafeOnClickListener {
            adapter.items = add()
        }
        btShuffle.setSafeOnClickListener {
            adapter.items = shuffle()
        }
    }
}
