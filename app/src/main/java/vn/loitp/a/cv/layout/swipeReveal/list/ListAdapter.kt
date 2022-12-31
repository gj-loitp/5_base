package vn.loitp.a.cv.layout.swipeReveal.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.loitp.views.layout.swipeReveal.LSwipeRevealLayout
import com.loitp.views.layout.swipeReveal.ViewBinderHelper
import com.loitp.views.toast.LToast.showShortInformation
import vn.loitp.R

class ListAdapter(
    context: Context,
    objects: List<String>
) : ArrayAdapter<String>(
    context,
    R.layout.i_swipe_reveal_layout_list,
    objects
) {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private val binderHelper: ViewBinderHelper = ViewBinderHelper()

    @Suppress("NAME_SHADOWING")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder

        if (convertView == null) {
            convertView =
                mInflater.inflate(R.layout.i_swipe_reveal_layout_list, parent, false)
            holder = ViewHolder()
            holder.swipeLayout = convertView.findViewById(R.id.swipeLayout)
            holder.layoutFront = convertView.findViewById(R.id.layoutFront)
            holder.layoutDelete = convertView.findViewById(R.id.layoutDelete)
            holder.text = convertView.findViewById(R.id.text)
            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }
        val item = getItem(position)
        if (item != null) {
            holder.swipeLayout?.let {
                binderHelper.bind(it, item)
            }
            holder.text?.text = item
            holder.layoutDelete?.setOnClickListener {
                remove(item)
            }
            holder.layoutFront?.setOnClickListener {
                val displayText = "$item clicked"
                showShortInformation(displayText, true)
            }
        }
        return convertView!!
    }

    fun saveStates(outState: Bundle?) {
        binderHelper.saveStates(outState)
    }

    fun restoreStates(inState: Bundle?) {
        binderHelper.restoreStates(inState)
    }

    private class ViewHolder {
        var swipeLayout: LSwipeRevealLayout? = null
        var layoutFront: View? = null
        var layoutDelete: View? = null
        var text: TextView? = null
    }

    init {
        // uncomment if you want to open only one row at a time
        binderHelper.setOpenOnlyOne(true)
    }
}
