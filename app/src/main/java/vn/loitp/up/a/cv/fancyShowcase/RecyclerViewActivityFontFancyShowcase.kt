package vn.loitp.up.a.cv.fancyShowcase

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFancyShowcase
import com.loitp.core.common.NOT_FOUND
import me.toptas.fancyshowcase.FancyShowCaseView
import vn.loitp.R
import vn.loitp.databinding.AFancyShowcaseRecyclerViewBinding
import vn.loitp.up.a.cv.fancyShowcase.adt.MyModel
import vn.loitp.up.a.cv.fancyShowcase.adt.MyRecyclerViewAdapter

@LogTag("RecyclerViewActivityFontFancyShowcase")
@IsFullScreen(false)
@IsAutoAnimation(false)
class RecyclerViewActivityFontFancyShowcase : BaseActivityFancyShowcase() {

    private lateinit var binding: AFancyShowcaseRecyclerViewBinding

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AFancyShowcaseRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modelList = ArrayList<MyModel>()
        for (i in 0..24) {
            modelList.add(MyModel("Item $i"))
        }

        val layoutManager = LinearLayoutManager(this)
        val adapter = MyRecyclerViewAdapter(modelList)
        adapter.setClickListener { v ->
            focus(v)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val width = binding.recyclerView.width
                val height = binding.recyclerView.height
                if (width > 0 && height > 0) {
                    binding.recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
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
