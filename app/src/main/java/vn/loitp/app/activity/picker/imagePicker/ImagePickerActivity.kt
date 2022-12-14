package vn.loitp.app.activity.picker.imagePicker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitpcore.annotation.IsAutoAnimation
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.ext.setSafeOnClickListener
import com.loitpcore.core.utilities.LSocialUtil
import com.loitpcore.core.utilities.LUIUtil
import com.nguyenhoanglam.imagepicker.model.GridCount
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import kotlinx.android.synthetic.main.activity_image_picker.*
import vn.loitp.app.R

@LogTag("ImagePickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ImagePickerActivity : BaseFontActivity() {

    private var adapter: ImageAdapter? = null
    private var images = ArrayList<Image>()

    private val launcher = registerImagePicker {
        images = it
        adapter?.setData(it)
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_image_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(view = it, runnable = {
                    LSocialUtil.openUrlInBrowser(
                        context = context, url = "https://github.com/nguyenhoanglam/ImagePicker"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ImagePickerActivity::class.java.simpleName
        }
        adapter = ImageAdapter(this)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        pickImageButton.setSafeOnClickListener {
            start()
        }
        launchFragmentButton.setSafeOnClickListener {
            launchFragment()
        }
    }

    private fun start() {
        val folderMode = folderModeSwitch.isChecked
        val multipleMode = multipleModeSwitch.isChecked
        val cameraOnly = cameraOnlySwitch.isChecked
        val showCamera = showCameraSwitch.isChecked
        val showNumberIndicator = showNumberIndicatorSwitch.isChecked
        val alwaysShowDone = alwaysShowDoneSwitch.isChecked

        val config = ImagePickerConfig(
            statusBarColor = "#00796B",
            isLightStatusBar = false,
            toolbarColor = "#009688",
            toolbarTextColor = "#FFFFFF",
            toolbarIconColor = "#FFFFFF",
            backgroundColor = "#000000",
            progressIndicatorColor = "#009688",
            selectedIndicatorColor = "#2196F3",
            isCameraOnly = cameraOnly,
            isMultipleMode = multipleMode,
            isFolderMode = folderMode,
            doneTitle = "DONE",
            folderTitle = "Albums",
            imageTitle = "Photos",
            isShowCamera = showCamera,
            isShowNumberIndicator = showNumberIndicator,
            isAlwaysShowDoneButton = alwaysShowDone,
            rootDirectory = RootDirectory.DCIM,
            subDirectory = "Example",
            maxSize = 10,
            limitMessage = "You could only select up to 10 photos",
            selectedImages = images
        )

        launcher.launch(config)

    }

    private fun launchFragment() {
        val folderMode = folderModeSwitch.isChecked
        val multipleMode = multipleModeSwitch.isChecked
        val cameraOnly = cameraOnlySwitch.isChecked
        val showCamera = showCameraSwitch.isChecked
        val showNumberIndicator = showNumberIndicatorSwitch.isChecked
        val alwaysShowDone = alwaysShowDoneSwitch.isChecked

        val config = ImagePickerConfig(
            isFolderMode = folderMode,
            isMultipleMode = multipleMode,
            isCameraOnly = cameraOnly,
            isShowCamera = showCamera,
            isShowNumberIndicator = showNumberIndicator,
            isAlwaysShowDoneButton = alwaysShowDone,
            rootDirectory = RootDirectory.DOWNLOADS,
            subDirectory = "Photos",
            selectedImages = images,
            statusBarColor = "#000000",
            isLightStatusBar = false,
            backgroundColor = "#FFFFFF",
            folderGridCount = GridCount(2, 4),
            imageGridCount = GridCount(3, 5),
        )

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance(config))
            .commitAllowingStateLoss()
    }

    override fun onBaseBackPressed() {
        val fm = supportFragmentManager
        val fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            super.onBaseBackPressed()
        } else {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss()
        }
    }
}
