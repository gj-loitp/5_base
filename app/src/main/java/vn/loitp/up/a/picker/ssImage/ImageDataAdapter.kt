package vn.loitp.up.a.picker.ssImage

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loitp.picker.ssImage.loadImage
import vn.loitp.R
import vn.loitp.databinding.IPickerSsImageListItemBinding

/**
 * ImageDataAdapter class to display list of picked images from the picker.
 */
class ImageDataAdapter(private val imageList: List<Uri>) :
    RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageDataViewHolder {
        val binding: IPickerSsImageListItemBinding = DataBindingUtil.inflate(
            /* inflater = */ LayoutInflater.from(parent.context),
            /* layoutId = */ R.layout.i_picker_ss_image_list_item,
            /* parent = */ parent,
            /* attachToParent = */ false
        )
        return ImageDataViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ImageDataViewHolder,
        position: Int
    ) {
        holder.binding.imageView.loadImage(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageDataViewHolder(val binding: IPickerSsImageListItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}