package vn.loitp.up.a.cv.fancyShowcase.adt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.errorprone.annotations.Keep
import vn.loitp.R

/**
 * Activity for RecyclerView sample
 */
class MyRecyclerViewAdapter(private val mMyModelList: List<MyModel>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private var mClickListener: View.OnClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.i_fancy_showcase_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val myModel = mMyModelList[position]
        holder.tvMain.text = myModel.title
        holder.tvMain.setOnClickListener { v ->
            mClickListener?.onClick(v)
        }
        holder.ivIcon.setOnClickListener { v ->
            mClickListener?.onClick(v)
        }
        holder.ivIcon.visibility = if (position % 5 == 2) View.VISIBLE else View.GONE

    }

    override fun getItemCount(): Int {
        return mMyModelList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvMain: TextView = view.findViewById(R.id.tvMain)
        var ivIcon: ImageView = view.findViewById(R.id.ivIcon)
    }

    fun setClickListener(clickListener: View.OnClickListener) {
        mClickListener = clickListener
    }
}

@Keep
data class MyModel(val title: String)
