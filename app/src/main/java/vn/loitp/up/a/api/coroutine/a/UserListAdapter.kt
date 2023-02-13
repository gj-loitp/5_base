package vn.loitp.up.a.api.coroutine.a

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.loitp.core.ext.loadGlide
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.sv.model.UserTest
import vn.loitp.databinding.ViewItemUser1Binding

class UserListAdapter(
    private val callback: (Int, UserTest) -> Unit,
) :
    RecyclerView.Adapter<UserListAdapter.UserTestViewHolder>() {

    private var userTestList = ArrayList<UserTest>()

    inner class UserTestViewHolder(val binding: ViewItemUser1Binding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(userTest: UserTest) {
            userTest.avatar?.let {
                binding.ivAvt.loadGlide(
                    any = it,
                )
            }
            binding.tvEmail.text = userTest.email
            binding.tvFirstName.text = userTest.firstName
            binding.tvId.text = "Id ${userTest.id}"
            binding.tvLastName.text = userTest.lastName
            binding.rootView.setSafeOnClickListener {
                callback.invoke(bindingAdapterPosition, userTest)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserTestViewHolder {
        val binding =
            ViewItemUser1Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserTestViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserTestViewHolder,
        position: Int
    ) {
        val userTest = userTestList[position]
        holder.bind(userTest)
    }

    override fun getItemCount(): Int {
        return userTestList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(userTestList: ArrayList<UserTest>?) {
        if (userTestList.isNullOrEmpty()) {
            this.userTestList.clear()
        } else {
            this.userTestList = userTestList
        }
        notifyDataSetChanged()
    }
}
