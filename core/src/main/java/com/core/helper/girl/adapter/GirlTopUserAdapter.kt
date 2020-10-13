package com.core.helper.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.LogTag
import com.core.adapter.AnimationAdapter
import com.core.common.Constants
import com.core.helper.girl.model.GirlTopUser
import com.core.helper.girl.view.ViewGirlTopUser
import com.core.utilities.LAnimationUtil
import com.core.utilities.LImageUtil
import com.daimajia.androidanimations.library.Techniques
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_girl_top_user.view.*
import kotlinx.android.synthetic.main.view_row_girl_top_user.view.*

@LogTag("GirlTopUserAdapter")
class GirlTopUserAdapter : AnimationAdapter() {

    private val listGirlTopUser = ArrayList<GirlTopUser>()
    var onClickRootView: ((GirlTopUser) -> Unit?)? = null

    fun setListGirlTopUser(listGirlTopUser: ArrayList<GirlTopUser>) {
        this.listGirlTopUser.clear()
        this.listGirlTopUser.addAll(listGirlTopUser)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.layoutHorizontal.removeAllViews()
            listGirlTopUser.forEach { girlTopUser ->
                val viewGirlTopUser = ViewGirlTopUser(itemView.context)
                val src = if (BuildConfig.DEBUG) {
                    Constants.URL_IMG
                } else {
                    girlTopUser.avatar
                }
                LImageUtil.load(context = viewGirlTopUser.imageView.context,
                        url = src,
                        imageView = viewGirlTopUser.imageView,
                        resError = R.color.black,
                        resPlaceHolder = R.color.black,
                        drawableRequestListener = null)
                viewGirlTopUser.tv.text = girlTopUser.name
                viewGirlTopUser.layoutRootView.setSafeOnClickListener {
                    LAnimationUtil.play(view = it, techniques = Techniques.Pulse)
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
