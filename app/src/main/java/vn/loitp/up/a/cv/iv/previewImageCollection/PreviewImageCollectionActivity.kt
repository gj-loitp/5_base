package vn.loitp.up.a.cv.iv.previewImageCollection

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.ivan200.photobarcodelib.PhotoBarcodeScannerBuilder
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.URL_IMG
import com.loitp.core.common.URL_IMG_1
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import pereira.agnaldo.previewimgcol.ImageCollectionView
import vn.loitp.R
import vn.loitp.databinding.AIvPreviewImageCollectionBinding

@LogTag("PreviewImageCollectionActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PreviewImageCollectionActivity : BaseActivityFont() {

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    private lateinit var binding: AIvPreviewImageCollectionBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AIvPreviewImageCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/AgnaldoNP/PreviewImageCollection"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = PreviewImageCollectionActivity::class.java.simpleName
        }

        binding.color.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.mBackgroundColor =
                    if (progress == 0) Color.TRANSPARENT else
                        Color.HSVToColor(floatArrayOf(progress.toFloat(), 100f, 100f))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.baseRowHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.baseImageHeight =
                    if (progress <= 2) 120.px else progress.px
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.imageMargin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.imageMargin = progress.px
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.maxImagePerRow.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.maxImagePerRow = if (progress == 0) 3 else progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.maxRows.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.maxRows =
                    if (progress == 0) ImageCollectionView.NO_ROW_LIMITS else progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.cornerRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.imageCollectionView.previewCornerRadius =
                    (binding.imageCollectionView.width * (progress / 100F)).toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        binding.pinchToZoom.setOnCheckedChangeListener { _, isChecked ->
            binding.imageCollectionView.pinchToZoom = isChecked
        }

        binding.showExternalBorderMargins.setOnCheckedChangeListener { _, isChecked ->
            binding.imageCollectionView.showExternalBorderMargins = isChecked
        }

        binding.distributeEvenly.setOnCheckedChangeListener { _, isChecked ->
            binding.imageCollectionView.previewDistributeEvenly = isChecked
        }

        binding.btAddPhoto.setOnClickListener {
            PhotoBarcodeScannerBuilder()
                .withActivity(this)
                .withTakingPictureMode()
                .withAutoFocus(true)
                .withFocusOnTap(true)
                .withCameraLockRotate(false)
                .withThumbnails(false)
                .withCameraTryFixOrientation(true)
                .withImageLargerSide(1200)
                .withPictureListener { file ->
                    if (file.exists()) {
                        val bitmap = BitmapFactory.decodeFile(file.absolutePath)
                        binding.imageCollectionView.addImage(bitmap)
                        file.delete()
                    }
                }.build().start()
        }

        binding.btClearPhotos.setOnClickListener {
            binding.imageCollectionView.clearImages()
        }

        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(resources, com.loitp.R.drawable.bkg_1),
            onClick = object : ImageCollectionView.OnImageClickListener {
                override fun onClick(bitmap: Bitmap, imageView: ImageView) {
                    showShortInformation("Test Click image 08")
                }
            }
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(resources, com.loitp.R.drawable.bkg_2),
            onLongClick = object : ImageCollectionView.OnImageLongClickListener {
                override fun onLongClick(bitmap: Bitmap, imageView: ImageView) {
                    showShortInformation("Long Click")
                }
            }
        )
        binding.imageCollectionView.addImageK(
            drawableRes = com.loitp.R.drawable.bkg_3,
            onClickUnit = { _: Bitmap?, _: ImageView? ->
                showShortInformation("landscape_02")
            }
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.loitp
            )
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.iv
            )
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.logo
            )
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ com.loitp.R.drawable.bkg_1
            )
        )
        binding.imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ com.loitp.R.drawable.bkg_2
            )
        )
        binding.imageCollectionView.addImage(
            url = URL_IMG,
            placeHolder = pereira.agnaldo.previewimgcol.R.drawable.blur
        )
        binding.imageCollectionView.addImage(
            url = URL_IMG_1,
            placeHolder = pereira.agnaldo.previewimgcol.R.drawable.blur
        )

        binding.imageCollectionView.setOnMoreClicked(object :
            ImageCollectionView.OnMoreClickListener {
            override fun onMoreClicked(bitmaps: List<Bitmap>) {
                showShortInformation("oi oi oi oi ")
            }
        })

        binding.imageCollectionView.setOnMoreClicked {

        }
    }

}
