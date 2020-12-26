package vn.loitp.app.activity.demo.firebase.databasesimple

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.utilities.LImageUtil
import de.hdodenhof.circleimageview.CircleImageView
import vn.loitp.app.R
import vn.loitp.app.activity.demo.firebase.databasesimple.UserAdapter.UserViewHolder

class UserAdapter(
        private val context: Context,
        private val userList: List<User>,
        private val callback: Callback?
) : RecyclerView.Adapter<UserViewHolder>() {

    private val logTag = javaClass.simpleName

    interface Callback {
        fun onClick(user: User, position: Int)
        fun onLongClick(user: User, position: Int)
    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvMsg: TextView = view.findViewById(R.id.tvMsg)
        val tvTime: TextView = view.findViewById(R.id.tvTime)
        val avt: CircleImageView = view.findViewById(R.id.avt)
        val rootView: LinearLayout = view.findViewById(R.id.rootView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.tvName.text = user.name
        holder.tvMsg.text = user.msg
        holder.tvTime.text = user.timestamp.toString()

        LImageUtil.load(
                context = context,
                any = user.avt,
                imageView = holder.avt,
                resPlaceHolder = R.color.colorPrimary,
                resError = R.color.colorPrimary,
                transformation = null,
                drawableRequestListener = object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                        return false
                    }
                })
        holder.rootView.setOnClickListener {
            callback?.onClick(user, position)
        }
        holder.rootView.setOnLongClickListener {
            callback?.onLongClick(user, position)
            true
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
