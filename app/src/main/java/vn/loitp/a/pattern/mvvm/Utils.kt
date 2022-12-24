package vn.loitp.a.pattern.mvvm

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("bind:imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view).load(url).into(view)
}
