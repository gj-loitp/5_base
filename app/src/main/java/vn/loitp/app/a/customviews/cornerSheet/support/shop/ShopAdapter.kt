package vn.loitp.app.a.customviews.cornerSheet.support.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.shop_item.view.*
import vn.loitp.R

object ShopDiff : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: ShopItem, newItem: ShopItem) = oldItem == newItem
}

class ShopAdapter(val clickHandler: ShopItemClickListener) :
    ListAdapter<ShopItem, ShopAdapter.ShopItemViewHolder>(ShopDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ShopItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_item, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        holder.bind(getItem(position), clickHandler)
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: ShopItem, callbackHandler: ShopItemClickListener) {
            itemView.shop_image.load(item.url)
            itemView.shop_name.text = item.name
            itemView.shop_image.transitionName = item.id.toString()
            itemView.shop_name.transitionName = item.name + item.id.toString()

            itemView.root.setOnClickListener {
                callbackHandler.onClick(
                    image = itemView.shop_image,
                    text = itemView.shop_name,
                    shopItemId = item.id
                )
            }
        }
    }
}

fun ImageView.load(
    url: String
) {
    Glide.with(this)
        .load(url)
        .into(this)
}

interface ShopItemClickListener {
    fun onClick(
        image: View,
        text: View,
        shopItemId: Long
    )
}
