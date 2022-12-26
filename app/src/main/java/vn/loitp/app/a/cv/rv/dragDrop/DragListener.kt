package vn.loitp.app.a.cv.rv.dragDrop

import android.annotation.SuppressLint
import android.view.DragEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R

class DragListener internal constructor(private val listener: CustomListener) :
    View.OnDragListener {
    private var isDropped = false

    @SuppressLint("NotifyDataSetChanged")
    override fun onDrag(v: View, event: DragEvent): Boolean {
        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource = event.localState as View?
                val viewId = v.id
                val frameLayoutItem = R.id.frame_layout_item
                val emptyTextView1 = R.id.tvEmptyList1
                val emptyTextView2 = R.id.tvEmptyList2
                val recyclerView1 = R.id.recyclerView1
                val recyclerView2 = R.id.recyclerView2
                when (viewId) {
                    frameLayoutItem, emptyTextView1, emptyTextView2, recyclerView1, recyclerView2 -> {
                        val target: RecyclerView
                        when (viewId) {
                            emptyTextView1, recyclerView1 -> target =
                                v.rootView.findViewById<View>(recyclerView1) as RecyclerView
                            emptyTextView2, recyclerView2 -> target =
                                v.rootView.findViewById<View>(recyclerView2) as RecyclerView
                            else -> {
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        if (viewSource != null) {
                            val source = viewSource.parent as RecyclerView
                            val adapterSource = source.adapter as CustomAdapter?
                            val positionSource = viewSource.tag as Int
                            val list: String? = adapterSource?.getList()?.get(positionSource)
                            val listSource = adapterSource?.getList()?.apply {
                                removeAt(positionSource)
                            }
                            listSource?.let { adapterSource.updateList(it) }
                            adapterSource?.notifyDataSetChanged()
                            val adapterTarget = target.adapter as CustomAdapter?
                            val customListTarget = adapterTarget?.getList()
                            if (positionTarget >= 0) {
                                list?.let { customListTarget?.add(positionTarget, it) }
                            } else {
                                list?.let { customListTarget?.add(it) }
                            }
                            customListTarget?.let { adapterTarget.updateList(it) }
                            adapterTarget?.notifyDataSetChanged()
                            if (source.id == recyclerView2 && (adapterSource?.itemCount ?: 0) < 1) {
                                listener.setEmptyList(
                                    visibility = View.VISIBLE,
                                    recyclerView = recyclerView2,
                                    emptyTextView = emptyTextView2
                                )
                            }
                            if (viewId == emptyTextView2) {
                                listener.setEmptyList(
                                    visibility = View.GONE,
                                    recyclerView = recyclerView2,
                                    emptyTextView = emptyTextView2
                                )
                            }
                            if (source.id == recyclerView1 && (adapterSource?.itemCount ?: 0) < 1) {
                                listener.setEmptyList(
                                    visibility = View.VISIBLE,
                                    recyclerView = recyclerView1,
                                    emptyTextView = emptyTextView1
                                )
                            }
                            if (viewId == emptyTextView1) {
                                listener.setEmptyList(
                                    visibility = View.GONE,
                                    recyclerView = recyclerView1,
                                    emptyTextView = emptyTextView1
                                )
                            }
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }
}
