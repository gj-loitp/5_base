package vn.loitp.app.activity.api.coroutine.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.core.utilities.LImageUtil
import com.views.setSafeOnClickListener
import vn.loitp.app.R
import vn.loitp.app.activity.api.coroutine.model.UserTest

class UserListAdapter(private val context: Context,
                      private val callback: (Int, UserTest) -> Unit) : RecyclerView.Adapter<UserListAdapter.UserTestViewHolder>() {
    private val TAG = javaClass.simpleName

    private var userTestList = ArrayList<UserTest>()

    inner class UserTestViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rootView: RelativeLayout = view.findViewById(R.id.rootView)
        var ivAvt: ImageView = view.findViewById(R.id.ivAvt)
        var tvEmail: TextView = view.findViewById(R.id.tvEmail)
        var tvFirstName: TextView = view.findViewById(R.id.tvFirstName)
        var tvId: TextView = view.findViewById(R.id.tvId)
        var tvLastName: TextView = view.findViewById(R.id.tvLastName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user_1, parent, false)
        return UserTestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserTestViewHolder, position: Int) {
        val userTest = userTestList[position]
        userTest.avatar?.let {
            LImageUtil.load(context, it, holder.ivAvt)
        }
        holder.tvEmail.text = userTest.email
        holder.tvFirstName.text = userTest.firstName
        holder.tvId.text = "Id ${userTest.id}"
        holder.tvLastName.text = userTest.lastName
        holder.rootView.setSafeOnClickListener {
            callback.invoke(position, userTest)
        }
    }

    override fun getItemCount(): Int {
        return userTestList.size
    }

    fun setList(userTestList: ArrayList<UserTest>?) {
        if (userTestList.isNullOrEmpty()) {
            this.userTestList.clear()
        } else {
            this.userTestList = userTestList
        }
        notifyDataSetChanged()
    }
}
