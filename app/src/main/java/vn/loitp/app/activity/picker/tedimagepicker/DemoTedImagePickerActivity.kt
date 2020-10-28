package vn.loitp.app.activity.picker.tedimagepicker

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LSocialUtil
import com.utils.util.ConvertUtils
import com.views.setSafeOnClickListener
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.TedRxImagePicker
import kotlinx.android.synthetic.main.activity_ted_image_picker_demo.*
import vn.loitp.app.R
import java.io.File

//https://github.com/ParkSangGwon/TedImagePicker

@LayoutId(R.layout.activity_ted_image_picker_demo)
@LogTag("DemoTedImagePickerActivity")
@IsFullScreen(false)
class DemoTedImagePickerActivity : BaseFontActivity() {
    private var selectedUriList: List<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tvMenu.setSafeOnClickListener {
            LSocialUtil.openUrlInBrowser(context = this, url = "https://github.com/ParkSangGwon/TedImagePicker")
        }

        setNormalSingleButton()
        setNormalMultiButton()
        setRxSingleButton()
        setRxSingleButtonThenResize()
        setRxMultiButton()
    }

    private fun setNormalSingleButton() {
        btnNormalSingle.setOnClickListener {
            logD("setNormalSingleButton")
            TedImagePicker.with(context = this)
                    .start { uri ->
                        logD("setNormalSingleButton $uri")
                        showSingleImage(uri)
                    }
        }
    }

    private fun setNormalMultiButton() {
        btnNormalMulti.setOnClickListener {
            TedImagePicker.with(context = this)
                    //.mediaType(MediaType.IMAGE)
                    //.scrollIndicatorDateFormat("YYYYMMDD")
                    //.buttonGravity(ButtonGravity.BOTTOM)
                    .errorListener { message ->
                        logD("message: $message")
                    }
                    .selectedUri(selectedUriList)
                    .startMultiImage { list: List<Uri> -> showMultiImage(list) }
        }
    }

    private fun setRxSingleButton() {
        btnRxSingle.setOnClickListener {
            TedRxImagePicker.with(context = this)
                    .start()
                    .subscribe(this::showSingleImage, Throwable::printStackTrace)
        }
    }

    private fun setRxSingleButtonThenResize() {
        btnRxSingleThenResize.setOnClickListener {
            TedRxImagePicker.with(context = this)
                    .start()
                    .subscribe({ uri ->
                        resize(uri)
                    }, Throwable::printStackTrace)
        }
    }

    private fun setRxMultiButton() {
        btnRxMulti.setOnClickListener {
            TedRxImagePicker.with(this)
                    .selectedUri(selectedUriList)
                    .startMultiImage()
                    .subscribe(this::showMultiImage, Throwable::printStackTrace)
        }
    }

    private fun showSingleImage(uri: Uri) {
        ivImage.visibility = View.VISIBLE
        containerSelectedPhotos.visibility = View.GONE
        LImageUtil.load(this, uri, ivImage)
    }

    private fun showMultiImage(uriList: List<Uri>) {
        this.selectedUriList = uriList
        ivImage.visibility = View.GONE
        containerSelectedPhotos.visibility = View.VISIBLE
        containerSelectedPhotos.removeAllViews()

        uriList.forEach { u ->
            val img = ImageView(this)
            val size = ConvertUtils.dp2px(200f)
            val layoutParams = LinearLayout.LayoutParams(size, size)
            img.layoutParams = layoutParams
            LImageUtil.load(context = this, any = u, imageView = img)
            containerSelectedPhotos.addView(img)
        }
    }

    private fun resize(u: Uri) {
        ivImage.visibility = View.VISIBLE
        containerSelectedPhotos.visibility = View.GONE
        u.path?.let { p ->
            val folderPath = ".resizeImage"
            val fileResize = LImageUtil.resizeImage(context = this, file = File(p), scaleTo = 1024, folderPath = folderPath)
            var hasInvalidImg = false
            if (fileResize?.exists() == true) {
                val uResize = Uri.fromFile(fileResize)
                if (uResize == null) {
                    hasInvalidImg = true
                }
            } else {
                hasInvalidImg = true
            }

            val msg = if (hasInvalidImg) {
                "Resize failed :("
            } else {
                "Resize success -> " + fileResize?.path
            }
            showDialogMsg(msg)
        }
    }
}
