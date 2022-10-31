package vn.loitp.app.activity.customviews.recyclerview.concatAdapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.adapter.BaseAdapter
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.view_item_user.view.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.concatAdapter.data.model.User

@LogTag("UsersAdapter")
class UsersAdapter(
    private val listUser: ArrayList<User>
) : BaseAdapter() {

    var onClickRootListener: ((User, Int) -> Unit)? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(listUser: ArrayList<User>) {
        this.listUser.clear()
        this.listUser.addAll(listUser)
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            itemView.textViewUserName.text = user.name
            LImageUtil.load(
                context = itemView.imageViewAvatar.context,
                any = user.avatar,
                imageView = itemView.imageViewAvatar
            )
            itemView.layoutRoot.setSafeOnClickListener {
                onClickRootListener?.invoke(user, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.view_item_user, parent,
                false
            )
        )

    override fun getItemCount(): Int = listUser.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is DataViewHolder) {
            holder.bind(listUser[position])
        }
    }
}
