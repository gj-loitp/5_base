package vn.loitp.a.cv.fancyShowcase

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFancyShowcaseFont
import kotlinx.android.synthetic.main.a_fancy_showcase_recycler_view.*
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R
import vn.loitp.a.cv.fancyShowcase.adt.MyModel
import vn.loitp.a.cv.fancyShowcase.adt.MyRecyclerViewAdapter

@LogTag("RecyclerViewActivityFontFancyShowcase")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RecyclerViewActivityFontFancyShowcase : BaseActivityFancyShowcaseFont() {
    override fun setLayoutResourceId(): Int {
        return R.layout.a_fancy_showcase_recycler_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val modelList = ArrayList<MyModel>()
        for (i in 0..24) {
            modelList.add(MyModel("Item $i"))
        }

        val layoutManager = LinearLayoutManager(this)
        val adapter = MyRecyclerViewAdapter(modelList)
        adapter.setClickListener { v ->
            focus(v)
        }

        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = recyclerView.width
                val height = recyclerView.height
                if (width > 0 && height > 0) {
                    recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }

                focus(layoutManager.findViewByPosition(2)!!.findViewById(R.id.ivIcon))
            }
        })
    }

    private fun focus(v: View) {
        FancyShowCaseView.Builder(this@RecyclerViewActivityFontFancyShowcase)
            .focusOn(v)
            .title("Focus RecyclerView Items")
            .enableAutoTextPosition()
            .build()
            .show()
    }
}
