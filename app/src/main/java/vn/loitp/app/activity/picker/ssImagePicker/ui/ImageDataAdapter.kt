package vn.loitp.app.activity.picker.ssImagePicker.ui

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.loitp.picker.ssImage.loadImage
import vn.loitp.R
import vn.loitp.databinding.PickerSsImageListItemImageDataBinding

/**
 * ImageDataAdapter class to display list of picked images from the picker.
 */
class ImageDataAdapter(private val imageList: List<Uri>) :
    RecyclerView.Adapter<ImageDataAdapter.ImageDataViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageDataViewHolder {
        val binding: PickerSsImageListItemImageDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.picker_ss_image_list_item_image_data,
            parent,
            false
        )
        return ImageDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageDataViewHolder, position: Int) {
        holder.binding.imageView.loadImage(imageList[position])
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    inner class ImageDataViewHolder(val binding: PickerSsImageListItemImageDataBinding) :
        RecyclerView.ViewHolder(binding.root)
}