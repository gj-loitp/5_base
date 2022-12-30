package vn.loitp.a.cv.rv.concatAdapter.adt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.annotation.LogTag
import com.loitp.core.adapter.BaseAdapter
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.i_user.view.*
import vn.loitp.R
import vn.loitp.a.cv.rv.concatAdapter.data.model.User

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                /* resource = */ R.layout.i_user,
                /* root = */ parent,
                /* attachToRoot = */ false
            )
        )

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
