package vn.loitp.up.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import vn.loitp.databinding.IUserBinding
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.User

@LogTag("UsersAdapter")
class UsersAdapter(
    private val listUser: ArrayList<User>
) : BaseAdapter() {

    var onClickRootListener: ((User, Int) -> Unit)? = null

    fun setData(listUser: ArrayList<User>) {
//        this.listUser.clear()
//        this.listUser.addAll(listUser)
//        notifyDataSetChanged()

        this.listUser.clear()
        listUser.forEachIndexed { index, user ->
            this.listUser.add(user)
            notifyItemInserted(index)
        }

    }

    inner class DataViewHolder(val binding: IUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.textViewUserName.text = user.name
            binding.imageViewAvatar.loadGlide(
                any = user.avatar,
            )
            binding.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(user, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = IUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (holder is DataViewHolder) {
            holder.bind(listUser[position])
        }
    }
}
