package vn.loitp.app.a.cv.vp.easyFlip

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.R

class ScreenSlideRecyclerAdapter(private val itemsList: ArrayList<Int>) :
    RecyclerView.Adapter<ScreenSlideRecyclerAdapter.ScreenSlideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenSlideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_dummy_layout, parent, false)
        return ScreenSlideViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScreenSlideViewHolder, position: Int) {
        holder.bind(itemsList[position], position)
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Int>) {
        itemsList.clear()
        itemsList.addAll(items)
        notifyDataSetChanged()
    }

    inner class ScreenSlideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView? = null

        init {
            textView = view.findViewById(R.id.textView)
        }

        fun bind(color: Int, position: Int) {
            textView?.setBackgroundColor(color)
            textView?.text = position.toString()
        }
    }
}
