package com.core.helper.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.core.adapter.AnimationAdapter
import com.core.helper.girl.model.GirlTopUser
import com.core.helper.girl.view.ViewGirlTopUser
import com.core.utilities.LImageUtil
import com.core.utilities.LLog
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_girl_top_user.view.*
import kotlinx.android.synthetic.main.view_row_girl_top_user.view.*

class GirlTopUserAdapter : AnimationAdapter() {
    private val logTag = "loitpp" + javaClass.simpleName

    private val listGirlTopUser = ArrayList<GirlTopUser>()
    var onClickRootView: ((GirlTopUser) -> Unit?)? = null

    fun setListGirlTopUser(listGirlTopUser: ArrayList<GirlTopUser>) {
        this.listGirlTopUser.clear()
        this.listGirlTopUser.addAll(listGirlTopUser)
//        LLog.d(logTag, "setListGirlTopUser " + Gson().toJson(listGirlTopUser))
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            LLog.d(logTag, "bind $bindingAdapterPosition")
            itemView.layoutHorizontal.removeAllViews()
            listGirlTopUser.forEach { girlTopUser ->
                val viewGirlTopUser = ViewGirlTopUser(itemView.context)
                LImageUtil.load(context = viewGirlTopUser.imageView.context, url = girlTopUser.avatar, imageView = viewGirlTopUser.imageView)
                viewGirlTopUser.tv.text = girlTopUser.name
                viewGirlTopUser.layoutRootView.setSafeOnClickListener {
                    onClickRootView?.invoke(girlTopUser)
                }
                itemView.layoutHorizontal.addView(viewGirlTopUser)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_top_user, parent,
                    false
            ))

    override fun getItemCount(): Int = if (listGirlTopUser.isEmpty()) 0 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

}
