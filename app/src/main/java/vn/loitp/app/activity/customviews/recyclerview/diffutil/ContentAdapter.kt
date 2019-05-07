package com.antonioleiva.diffutilkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_item_diff_util.view.*
import loitp.basemaster.R
import vn.loitp.core.utilities.LImageUtil
import kotlin.properties.Delegates

class ContentAdapter() : RecyclerView.Adapter<ContentAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var items: List<Content> by Delegates.observable(emptyList()) { prop, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.view_item_diff_util, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(content: Content) = with(itemView) {
            LImageUtil.load(context, content.image, image)
            text.text = content.text
        }
    }
}