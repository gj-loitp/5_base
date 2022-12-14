package vn.loitp.app.activity.customviews.recyclerview.dragDrop

import android.os.Bundle
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_main_drag_drop.*
import kotlinx.android.synthetic.main.activity_main_drag_drop.lActionBar
import kotlinx.android.synthetic.main.activity_menu_recycler_view.*
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

        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = MainActivityDragDrop::class.java.simpleName
        }
        recyclerView1.init(listOf("A", "B", "C"), tvEmptyList1)
        recyclerView2.init(listOf("1", "2", "3"), tvEmptyList2)
    }

    private fun RecyclerView.init(
        list: List<String>,
        emptyTextView: TextView
    ) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        val adapter = CustomAdapter(list, this@MainActivityDragDrop)
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
