package vn.loitp.app.a.cv.rv.concatAdapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_item_about_me.view.*
import vn.loitp.R
import vn.loitp.app.a.cv.rv.concatAdapter.data.model.AboutMe

@LogTag("AboutMeAdapter")
class AboutMeAdapter(private val listAboutMe: ArrayList<AboutMe>) : BaseAdapter() {

    var onClickRootListener: ((AboutMe, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(aboutMe: ArrayList<AboutMe>) {
        this.listAboutMe.clear()
        this.listAboutMe.addAll(aboutMe)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(aboutMe: AboutMe) {
            itemView.textViewUser.text = aboutMe.name
            itemView.textViewAboutMe.text = aboutMe.aboutMe

            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(aboutMe, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_about_me, parent,
                false
            )
        )

    override fun getItemCount(): Int = listAboutMe.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(aboutMe = listAboutMe[position])
        }
    }
}
