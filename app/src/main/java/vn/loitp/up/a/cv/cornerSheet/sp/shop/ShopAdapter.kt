package vn.loitp.up.a.cv.cornerSheet.sp.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.loitp.databinding.IShopItemBinding

object ShopDiff : DiffUtil.ItemCallback<ShopItem>() {
    override fun areItemsTheSame(
        oldItem: ShopItem,
        newItem: ShopItem
    ) = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: ShopItem,
        newItem: ShopItem
    ) = oldItem == newItem
}

class ShopAdapter(private val clickHandler: ShopItemClickListener) :
    ListAdapter<ShopItem, ShopAdapter.ShopItemViewHolder>(ShopDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ShopItemViewHolder {
        val binding = IShopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ShopItemViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position), clickHandler)
    }

    class ShopItemViewHolder(val binding: IShopItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShopItem, callbackHandler: ShopItemClickListener) {
            binding.shopImage.load(item.url)
            binding.shopName.text = item.name
            binding.shopImage.transitionName = item.id.toString()
            binding.shopName.transitionName = item.name + item.id.toString()

            binding.root.setOnClickListener {
                callbackHandler.onClick(
                    image = binding.shopImage,
                    text = binding.shopName,
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
