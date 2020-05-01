package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_row_item_layout_about_me.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.AboutMe

class AboutMeAdapter(private val listAboutMe: ArrayList<AboutMe>) : RecyclerView.Adapter<AboutMeAdapter.DataViewHolder>() {

    fun setData(aboutMe: ArrayList<AboutMe>) {
        this.listAboutMe.clear()
        this.listAboutMe.addAll(aboutMe)
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(aboutMe: AboutMe) {
            itemView.textViewUser.text = aboutMe.name
            itemView.textViewAboutMe.text = aboutMe.aboutMe
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_item_layout_about_me, parent,
                    false
            ))

    override fun getItemCount(): Int = listAboutMe.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) = holder.bind(listAboutMe[position])

}
