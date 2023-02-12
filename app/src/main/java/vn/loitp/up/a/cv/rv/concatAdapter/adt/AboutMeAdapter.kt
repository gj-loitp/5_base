package vn.loitp.up.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.IAboutMeBinding
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.AboutMe

@LogTag("AboutMeAdapter")
class AboutMeAdapter(private val listAboutMe: ArrayList<AboutMe>) : BaseAdapter() {

    var onClickRootListener: ((AboutMe, Int) -> Unit)? = null

    fun setData(aboutMe: ArrayList<AboutMe>) {
        aboutMe.forEachIndexed { index, item ->
            this.listAboutMe.add(item)
            notifyItemInserted(index)
        }
//        this.listAboutMe.addAll(aboutMe)
//        notifyDataSetChanged()
    }

    inner class DataViewHolder(val binding: IAboutMeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(aboutMe: AboutMe) {
            binding.textViewUser.text = aboutMe.name
            binding.textViewAboutMe.text = aboutMe.aboutMe

            binding.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(aboutMe, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IAboutMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listAboutMe.size

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(aboutMe = listAboutMe[position])
        }
    }
}
