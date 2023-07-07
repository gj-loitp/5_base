package vn.loitp.up.a.cv.iv.imageSlideshow

import android.os.Bundle
import androidx.core.view.isVisible
import com.denzcoskun.imageslider.constants.ActionTypes
import com.denzcoskun.imageslider.constants.AnimationTypes
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemChangeListener
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.interfaces.TouchListener
import com.denzcoskun.imageslider.models.SlideModel
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.*
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.AImageSlideshowBinding

@LogTag("ImageSlideshowActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ImageSlideshowActivity : BaseActivityFont() {

    private lateinit var binding: AImageSlideshowBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AImageSlideshowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.apply {
                this.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/denzcoskun/ImageSlideshow"
                    )
                })
                isVisible = true
                setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ImageSlideshowActivity::class.java.simpleName
        }

        val imageList = ArrayList<SlideModel>() // Create image list
        imageList.add(SlideModel(URL_IMG_9, "The future is our hands."))
        imageList.add(SlideModel(URL_IMG_8, "Climate change is moving very fast."))
        imageList.add(
            SlideModel(
                URL_IMG_7, "The population has decreased by 27 percent in the last 5 years."
            )
        )

        binding.imageSlider.setImageList(imageList, ScaleTypes.CENTER_CROP)
        binding.imageSlider.setSlideAnimation(AnimationTypes.ZOOM_OUT)
        binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun onItemSelected(position: Int) {
                // You can listen here.
            }

            override fun doubleClick(position: Int) {
                // Do not use onItemSelected if you are using a double click listener at the same time.
                // Its just added for specific cases.
                // Listen for clicks under 250 milliseconds.
            }
        })
        binding.imageSlider.setItemChangeListener(object : ItemChangeListener {
            override fun onItemChanged(position: Int) {
                //println("Pos: " + position)
            }
        })
        binding.imageSlider.setTouchListener(object : TouchListener {
            override fun onTouched(touched: ActionTypes, position: Int) {
                if (touched == ActionTypes.DOWN) {
                    binding.imageSlider.stopSliding()
                } else if (touched == ActionTypes.UP) {
                    binding.imageSlider.startSliding(1000)
                }
            }
        })
    }

}
