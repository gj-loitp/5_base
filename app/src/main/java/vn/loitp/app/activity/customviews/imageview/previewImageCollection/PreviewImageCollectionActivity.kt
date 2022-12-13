package vn.loitp.app.activity.customviews.imageview.previewImageCollection

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
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.common.Constants
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_preview_image_collection.*
import pereira.agnaldo.previewimgcol.ImageCollectionView
import vn.loitp.app.R

@LogTag("PreviewImageCollectionActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class PreviewImageCollectionActivity : BaseFontActivity() {

    val Int.px: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_preview_image_collection
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/AgnaldoNP/PreviewImageCollection"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = PreviewImageCollectionActivity::class.java.simpleName
        }

        color.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.mBackgroundColor = if (progress == 0) Color.TRANSPARENT else
                    Color.HSVToColor(floatArrayOf(progress.toFloat(), 100f, 100f))
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        baseRowHeight.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.baseImageHeight = if (progress <= 2) 120.px else progress.px
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        imageMargin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.imageMargin = progress.px
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        maxImagePerRow.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.maxImagePerRow = if (progress == 0) 3 else progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        maxRows.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.maxRows =
                    if (progress == 0) ImageCollectionView.NO_ROW_LIMITS else progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        cornerRadius.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageCollectionView.previewCornerRadius =
                    (imageCollectionView.width * (progress / 100F)).toInt()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        pinchToZoom.setOnCheckedChangeListener { _, isChecked ->
            imageCollectionView.pinchToZoom = isChecked
        }

        showExternalBorderMargins.setOnCheckedChangeListener { _, isChecked ->
            imageCollectionView.showExternalBorderMargins = isChecked
        }

        distributeEvenly.setOnCheckedChangeListener { _, isChecked ->
            imageCollectionView.previewDistributeEvenly = isChecked
        }

        btAddPhoto.setOnClickListener {
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
                        imageCollectionView.addImage(bitmap)
                        file.delete()
                    }
                }.build().start()
        }

        btClearPhotos.setOnClickListener {
            imageCollectionView.clearImages()
        }

        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.bkg_1),
            onClick = object : ImageCollectionView.OnImageClickListener {
                override fun onClick(bitmap: Bitmap, imageView: ImageView) {
                    showShortInformation("Test Click image 08")
                }
            }
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(resources, R.drawable.bkg_2),
            onLongClick = object : ImageCollectionView.OnImageLongClickListener {
                override fun onLongClick(bitmap: Bitmap, imageView: ImageView) {
                    showShortInformation("Long Click")
                }
            }
        )
        imageCollectionView.addImageK(
            drawableRes = R.drawable.bkg_3,
            onClickUnit = { _: Bitmap?, _: ImageView? ->
                showShortInformation("landscape_02")
            }
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.loitp
            )
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.iv
            )
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.logo
            )
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.bkg_1
            )
        )
        imageCollectionView.addImage(
            bitmap = BitmapFactory.decodeResource(
                /* res = */ resources,
                /* id = */ R.drawable.bkg_2
            )
        )
        imageCollectionView.addImage(
            url = Constants.URL_IMG,
            placeHolder = pereira.agnaldo.previewimgcol.R.drawable.blur
        )
        imageCollectionView.addImage(
            url = Constants.URL_IMG_1,
            placeHolder = pereira.agnaldo.previewimgcol.R.drawable.blur
        )

        imageCollectionView.setOnMoreClicked(object : ImageCollectionView.OnMoreClickListener {
            override fun onMoreClicked(bitmaps: List<Bitmap>) {
                showShortInformation("oi oi oi oi ")
            }
        })

        imageCollectionView.setOnMoreClicked {

        }
    }

}
