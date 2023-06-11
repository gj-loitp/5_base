package vn.loitp.up.a.cv.rv.gv

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import vn.loitp.R

class GridViewAdapter(
    private val list: ArrayList<String>
) : BaseAdapter() {

    private class ViewHolder {
        var tv: TextView? = null
        var iv: ImageView? = null
    }

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(i: Int): Any {
        return list[i]
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    override fun getView(
        position: Int, convertView: View?, parent: ViewGroup
    ): View? {
        Log.d("loitpp", "getView position $position")
        var view = convertView
        val viewHolder: ViewHolder
        if (view == null) {
            val inflater =
                parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.i_gv, parent, false)
            viewHolder = ViewHolder()
            viewHolder.tv = view.findViewById(R.id.tv)
            viewHolder.iv = view.findViewById(R.id.iv)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.tv?.text = list[position]
        viewHolder.iv?.setImageResource(R.drawable.ic_launcher)
        return view
    }
}
