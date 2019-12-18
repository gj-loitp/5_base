package vn.loitp.app.activity.picker.tedimagepicker

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import com.core.utilities.LLog
import com.utils.util.ConvertUtils
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.TedRxImagePicker
import kotlinx.android.synthetic.main.activity_ted_image_picker_demo.*

class DemoTedImagePickerActivity : BaseFontActivity() {
    private var selectedUriList: List<Uri>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setNormalSingleButton()
        setNormalMultiButton()
        setRxSingleButton()
        setRxSingleButtonThenResize()
        setRxMultiButton()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return loitp.basemaster.R.layout.activity_ted_image_picker_demo
    }

    private fun setNormalSingleButton() {
        btnNormalSingle.setOnClickListener {
            LLog.d(TAG, "setNormalSingleButton")
            TedImagePicker.with(this)
                    .start { uri ->
                        LLog.d(TAG, "setNormalSingleButton $uri")
                        showSingleImage(uri)
                    }
        }
    }

    private fun setNormalMultiButton() {
        btnNormalMulti.setOnClickListener {
            TedImagePicker.with(this)
                    //.mediaType(MediaType.IMAGE)
                    //.scrollIndicatorDateFormat("YYYYMMDD")
                    //.buttonGravity(ButtonGravity.BOTTOM)
                    .errorListener { message -> LLog.d(TAG, "message: $message") }
                    .selectedUri(selectedUriList)
                    .startMultiImage { list: List<Uri> -> showMultiImage(list) }
        }
    }

    private fun setRxSingleButton() {
        btnRxSingle.setOnClickListener {
            TedRxImagePicker.with(this)
                    .start()
                    .subscribe(this::showSingleImage, Throwable::printStackTrace)
        }
    }

    private fun setRxSingleButtonThenResize() {
        btnRxSingleThenResize.setOnClickListener {
            TedRxImagePicker.with(this)
                    .start()
                    .subscribe({ uri ->
                        resize(uri)
                    }, Throwable::printStackTrace)
        }
    }

    private fun setRxMultiButton() {
        btnRxMulti.setOnClickListener {
            TedRxImagePicker.with(this)
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

        uriList.forEach {
            val img = ImageView(this)
            val size = ConvertUtils.dp2px(200f)
            val layoutParams = LinearLayout.LayoutParams(size, size)
            img.layoutParams = layoutParams
            LImageUtil.load(this, it, img)
            containerSelectedPhotos.addView(img)
        }
    }

    private fun resize(uri: Uri) {
        ivImage.visibility = View.VISIBLE
        containerSelectedPhotos.visibility = View.GONE
        LImageUtil.load(this, uri, ivImage)
    }
}
