package vn.loitp.app.activity.function.dragdropsample

import android.content.ClipData
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.View.OnDragListener
import android.widget.ImageView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_func_drag_drop_sample.*
import vn.loitp.app.R

@LogTag("DragDropSampleActivity")
@IsFullScreen(false)
class DragDropSampleActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_drag_drop_sample
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ivPaper.tag = "paper"
        ivTrash.tag = "trash"

        ivTrash.setOnDragListener(
            TrashDragListener(
                enterShape = R.drawable.ic_launcher,
                normalShape = R.drawable.ic_search_black_48dp
            )
        )
        ivTrash.setSafeOnClickListener {
            ivPaper.visibility = View.VISIBLE
        }
        ivPaper.setOnLongClickListener { view: View ->
            val data = ClipData.newPlainText("", "")
            val shadowBuilder = DragShadowBuilder(view)
            view.startDrag(data, shadowBuilder, view, 0)
            view.visibility = View.INVISIBLE
            true
        }
    }

    private class TrashDragListener(private val enterShape: Int, private val normalShape: Int) :
        OnDragListener {
        private var hit = false
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val containerView = v as ImageView
            val draggedView = event.localState as ImageView
            return when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    hit = false
                    true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    containerView.setImageResource(enterShape)
                    true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    containerView.setImageResource(normalShape)
                    true
                }
                DragEvent.ACTION_DROP -> {
                    hit = true
                    draggedView.post {
                        draggedView.visibility = View.GONE
                    }
                    true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    containerView.setImageResource(normalShape)
                    v.setVisibility(View.VISIBLE)
                    if (!hit) {
                        draggedView.post {
                            draggedView.visibility = View.VISIBLE
                        }
                    }
                    true
                }
                else -> true
            }
        }
    }
}
