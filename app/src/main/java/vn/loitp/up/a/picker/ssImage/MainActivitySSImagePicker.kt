package vn.loitp.up.a.picker.ssImage

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.core.view.isVisible
import com.app.imagepickerlibrary.ImagePicker
import com.app.imagepickerlibrary.ImagePicker.Companion.registerImagePicker
import com.app.imagepickerlibrary.listener.ImagePickerResultListener
import com.app.imagepickerlibrary.model.ImageProvider
import com.app.imagepickerlibrary.model.PickerType
import com.app.imagepickerlibrary.ui.bottomsheet.SSPickerOptionsBottomSheet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.bitmapToFile
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.picker.ssImage.PickerOptions
import com.loitp.picker.ssImage.isAtLeast11
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import vn.loitp.R
import vn.loitp.databinding.AMainSsImagePickerBinding
import vn.loitp.up.a.MenuActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


/**
 * MainActivity which displays all the functionality of the ImagePicker library. All the attributes are modified with the ui.
 */
@LogTag("MainActivitySSImagePicker")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MainActivitySSImagePicker : BaseActivityFont(),
    SSPickerOptionsBottomSheet.ImagePickerClickListener, ImagePickerResultListener,
    PickerOptionsBottomSheet.PickerOptionsListener {

    companion object {
        private const val IMAGE_LIST = "IMAGE_LIST"
    }

    private lateinit var binding: AMainSsImagePickerBinding
    private val imagePicker: ImagePicker = registerImagePicker(this@MainActivitySSImagePicker)
    private val imageList = mutableListOf<Uri>()
    private val imageDataAdapter = ImageDataAdapter(imageList)
    private var pickerOptions = PickerOptions.default()

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = DataBindingUtil.setContentView(this, R.layout.a_main_ss_image_picker)
//        setUI(savedInstanceState)

        binding = AMainSsImagePickerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUI(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                super.onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/SimformSolutionsPvtLtd/SSImagePicker"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }

        binding.optionsButton.setSafeOnClickListener {
            openPickerOptions()
        }
        binding.openPickerButton.setSafeOnClickListener {
            openImagePicker()
        }
        binding.openSheetButton.setSafeOnClickListener {
            val fragment = SSPickerOptionsBottomSheet.newInstance(R.style.CustomPickerBottomSheet)
            fragment.show(supportFragmentManager, SSPickerOptionsBottomSheet.BOTTOM_SHEET_TAG)
        }
    }

    private fun setUI(savedInstanceState: Bundle?) {
        binding.imageRecyclerView.adapter = imageDataAdapter
        if (savedInstanceState != null && savedInstanceState.containsKey(IMAGE_LIST)) {
            val uriList: List<Uri> =
                savedInstanceState.getParcelableArrayList(IMAGE_LIST) ?: listOf()
            updateImageList(uriList)
        }
    }

    /**
     * This method receives the selected picker type option from the bottom sheet.
     */
    override fun onImageProvider(provider: ImageProvider) {
        when (provider) {
            ImageProvider.GALLERY -> {
                pickerOptions = pickerOptions.copy(pickerType = PickerType.GALLERY)
                openImagePicker()
            }

            ImageProvider.CAMERA -> {
                pickerOptions = pickerOptions.copy(pickerType = PickerType.CAMERA)
                openImagePicker()
            }

            ImageProvider.NONE -> {
                //User has pressed cancel show anything or just leave it blank.
            }
        }
    }

    /**
     * Opens the options for picker. The picker option is bottom sheet with many input parameters.
     */
    private fun openPickerOptions() {
        val fragment = PickerOptionsBottomSheet.newInstance(pickerOptions)
        fragment.setClickListener(this)
        fragment.show(supportFragmentManager, PickerOptionsBottomSheet.BOTTOM_SHEET_TAG)
    }

    /**
     * Once the picker options are selected in bottom sheet
     * we will receive the latest picker options in this method
     */
    override fun onPickerOptions(pickerOptions: PickerOptions) {
        this.pickerOptions = pickerOptions
        openImagePicker()
    }

    /**
     * Open the image picker according to picker type and the ui options.
     * The new system picker is only available for Android 13+.
     */
    private fun openImagePicker() {
        imagePicker.title("My Picker")
            .multipleSelection(pickerOptions.allowMultipleSelection, pickerOptions.maxPickCount)
            .showCountInToolBar(pickerOptions.showCountInToolBar)
            .showFolder(pickerOptions.showFolders).cameraIcon(pickerOptions.showCameraIconInGallery)
            .doneIcon(pickerOptions.isDoneIcon).allowCropping(pickerOptions.openCropOptions)
            .compressImage(pickerOptions.compressImage).maxImageSize(pickerOptions.maxPickSizeMB)
            .extension(pickerOptions.pickExtension)
        if (isAtLeast11()) {
            imagePicker.systemPicker(pickerOptions.openSystemPicker)
        }
        imagePicker.open(pickerOptions.pickerType)
    }

    /**
     * Single Selection and the image captured from camera will be received in this method.
     */
    override fun onImagePick(uri: Uri?) {
        uri?.let { updateImageList(listOf(it)) }
    }

    /**
     * Multiple Selection uris will be received in this method
     */
    override fun onMultiImagePick(uris: List<Uri>?) {
        if (!uris.isNullOrEmpty()) {
            updateImageList(uris)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("NotifyDataSetChanged")
    private fun updateImageList(list: List<Uri>) {
        imageList.clear()
        imageList.addAll(list)
        imageDataAdapter.notifyDataSetChanged()

        //work
//        testResize(list.firstOrNull())

        val listTest = ArrayList<Uri>()
        for (i in 0..500) {
            list.firstOrNull()?.let {
                listTest.add(it)
            }
        }

        //C1 run tuan tu work -> se bi no responding
//        showDialogProgress()
//        val timeStart = System.currentTimeMillis()
//        listTest.forEach { uri ->
//            testResize(uri)
//        }
//        val timeEnd = System.currentTimeMillis()
//        logD("bench ${timeEnd - timeStart}")
//        hideDialogProgress()

        //C2 run parallel
        showDialogProgress()
        val timeStart = System.currentTimeMillis()
        //TODO run coroutine parallel
        GlobalScope.launch {
            listTest.mapIndexed { index, uri ->
                async {
                    logD(">>>index $index")
                    testResize(uri)
                }
            }.awaitAll()
            val timeEnd = System.currentTimeMillis()
            logD("bench ${timeEnd - timeStart}")
            hideDialogProgress()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(IMAGE_LIST, ArrayList(imageList))
        super.onSaveInstanceState(outState)
    }

    private fun testResize(uri: Uri?) {
        if (uri == null) {
            return
        }
        logD("testResize>>> uri ${uri.path}")

        val options = BitmapFactory.Options().apply { inJustDecodeBounds = true }
        val inputStream = contentResolver.openInputStream(uri)
        BitmapFactory.decodeStream(inputStream, null, options)

        val width = options.outWidth
        val height = options.outHeight
        logD("size $width x $height")

        val expectWidth = 1000
        val expectHeight = expectWidth * height / width
        logD("expect size $expectWidth x $expectHeight")

        Glide.with(this).asBitmap()
            .load(uri)
            .apply(RequestOptions().override(expectWidth, expectHeight))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Bitmap?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Bitmap?>?,
                    isFirstResource: Boolean
                ): Boolean {
                    logE("onLoadFailed $e")
                    return false
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any?,
                    target: Target<Bitmap?>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // resource is your loaded Bitmap
                    resource?.let { bmp ->
                        logE("onResourceReady ${bmp.width}x${bmp.height}")

                        //save bitmap to file
                        val f = bmp.bitmapToFile("Img${System.currentTimeMillis()}.png")
                        logE(">>>path ${f?.path}")
                    }

                    binding.ivTestResize.postDelayed({
                        binding.ivTestResize.setImageBitmap(resource)
                    }, 10)
                    return true
                }
            }).submit()
    }
}
