package vn.loitp.a.picker.ssImage

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.app.imagepickerlibrary.ImagePicker
import com.app.imagepickerlibrary.ImagePicker.Companion.registerImagePicker
import com.app.imagepickerlibrary.listener.ImagePickerResultListener
import com.app.imagepickerlibrary.model.ImageProvider
import com.app.imagepickerlibrary.model.PickerType
import com.app.imagepickerlibrary.ui.bottomsheet.SSPickerOptionsBottomSheet
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.utilities.LUIUtil
import com.loitp.picker.ssImage.PickerOptions
import com.loitp.picker.ssImage.isAtLeast11
import kotlinx.android.synthetic.main.a_main_ss_image_picker.*
import vn.loitp.R
import vn.loitp.a.MenuActivity
import vn.loitp.databinding.AMainSsImagePickerBinding

/**
 * MainActivity which displays all the functionality of the ImagePicker library. All the attributes are modified with the ui.
 */
@LogTag("MainActivitySSImagePicker")
@IsFullScreen(false)
@IsAutoAnimation(true)
class MainActivitySSImagePickerFont : BaseActivityFont(),
    SSPickerOptionsBottomSheet.ImagePickerClickListener,
    ImagePickerResultListener, PickerOptionsBottomSheet.PickerOptionsListener {

    companion object {
        private const val IMAGE_LIST = "IMAGE_LIST"
    }

    private lateinit var binding: AMainSsImagePickerBinding
    private val imagePicker: ImagePicker = registerImagePicker(this@MainActivitySSImagePickerFont)
    private val imageList = mutableListOf<Uri>()
    private val imageDataAdapter = ImageDataAdapter(imageList)
    private var pickerOptions = PickerOptions.default()

    override fun setLayoutResourceId(): Int {
        return R.layout.a_main_ss_image_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.a_main_ss_image_picker)
        setUI(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    super.onBaseBackPressed()
                }
            )
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        context.openUrlInBrowser(
                            url = "https://github.com/SimformSolutionsPvtLtd/SSImagePicker"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = MenuActivity::class.java.simpleName
        }

        options_button.setSafeOnClickListener {
            openPickerOptions()
        }
        open_picker_button.setSafeOnClickListener {
            openImagePicker()
        }
        open_sheet_button.setSafeOnClickListener {
            val fragment =
                SSPickerOptionsBottomSheet.newInstance(R.style.CustomPickerBottomSheet)
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
        imagePicker
            .title("My Picker")
            .multipleSelection(pickerOptions.allowMultipleSelection, pickerOptions.maxPickCount)
            .showCountInToolBar(pickerOptions.showCountInToolBar)
            .showFolder(pickerOptions.showFolders)
            .cameraIcon(pickerOptions.showCameraIconInGallery)
            .doneIcon(pickerOptions.isDoneIcon)
            .allowCropping(pickerOptions.openCropOptions)
            .compressImage(pickerOptions.compressImage)
            .maxImageSize(pickerOptions.maxPickSizeMB)
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

    @SuppressLint("NotifyDataSetChanged")
    private fun updateImageList(list: List<Uri>) {
        imageList.clear()
        imageList.addAll(list)
        imageDataAdapter.notifyDataSetChanged()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(IMAGE_LIST, ArrayList(imageList))
        super.onSaveInstanceState(outState)
    }
}
