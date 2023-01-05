package vn.loitp.a.cv.rv.dragDrop

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_main_drag_drop.*
import vn.loitp.R

@LogTag("MainActivityDragDrop")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MainActivityDragDropFont : BaseActivityFont(), CustomListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.a_main_drag_drop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MainActivityDragDropFont::class.java.simpleName
        }
        recyclerView1.init(listOf("A", "B", "C"), tvEmptyList1)
        recyclerView2.init(listOf("1", "2", "3"), tvEmptyList2)
    }

    private fun RecyclerView.init(
        list: List<String>,
        emptyTextView: TextView
    ) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CustomAdapter(list, this@MainActivityDragDropFont)
        this.adapter = adapter
        emptyTextView.setOnDragListener(adapter.dragInstance)
        this.setOnDragListener(adapter.dragInstance)
    }

    override fun setEmptyList(
        visibility: Int,
        recyclerView: Int,
        emptyTextView: Int
    ) {
        findViewById<RecyclerView>(recyclerView).visibility = visibility
        findViewById<TextView>(emptyTextView).visibility = visibility
    }
}
