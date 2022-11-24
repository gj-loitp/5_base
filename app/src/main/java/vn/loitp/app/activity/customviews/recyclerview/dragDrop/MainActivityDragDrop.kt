package vn.loitp.app.activity.customviews.recyclerview.dragDrop

import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_main_drag_drop.*
import vn.loitp.app.R

@LogTag("MainActivityDragDrop")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MainActivityDragDrop : BaseFontActivity(), CustomListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_main_drag_drop
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recycler_view_1.init(listOf("A", "B", "C"), empty_list_text_view_1)
        recycler_view_2.init(listOf("1", "2", "3"), empty_list_text_view_2)
    }

    private fun RecyclerView.init(list: List<String>, emptyTextView: TextView) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CustomAdapter(list, this@MainActivityDragDrop)
        this.adapter = adapter
        emptyTextView.setOnDragListener(adapter.dragInstance)
        this.setOnDragListener(adapter.dragInstance)
    }

    override fun setEmptyList(visibility: Int, recyclerView: Int, emptyTextView: Int) {
        findViewById<RecyclerView>(recyclerView).visibility = visibility
        findViewById<TextView>(emptyTextView).visibility = visibility
    }
}

