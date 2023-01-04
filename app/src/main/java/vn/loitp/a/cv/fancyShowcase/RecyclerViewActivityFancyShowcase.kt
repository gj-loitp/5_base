package vn.loitp.a.cv.fancyShowcase

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.a_fancy_showcase_recycler_view.*
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R

class RecyclerViewActivityFancyShowcase : BaseActivityFancyShowcase() {
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
        adapter.setClickListener(View.OnClickListener { v ->
            focus(v)
        })

        recyclerView.adapter = adapter

        recyclerView.layoutManager = layoutManager

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = recyclerView.width
                val height = recyclerView.height
                if (width > 0 && height > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    } else {
                        recyclerView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    }
                }

                focus(layoutManager.findViewByPosition(2)!!.findViewById(R.id.ivIcon))
            }
        })
    }

    private fun focus(v: View) {
        FancyShowCaseView.Builder(this@RecyclerViewActivityFancyShowcase)
            .focusOn(v)
            .title("Focus RecyclerView Items")
            .enableAutoTextPosition()
            .build()
            .show()
    }
}
