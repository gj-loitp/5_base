package vn.loitp.up.a.picker.image

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.nguyenhoanglam.imagepicker.model.GridCount
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.model.ImagePickerConfig
import com.nguyenhoanglam.imagepicker.model.RootDirectory
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker
import vn.loitp.R
import vn.loitp.databinding.APickerImageDemoBinding

@LogTag("ImagePickerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class ImagePickerActivity : BaseActivityFont() {

    private lateinit var binding: APickerImageDemoBinding
    private var adapter: ImageAdapter? = null
    private var images = ArrayList<Image>()

    private val launcher = registerImagePicker {
        images = it
        adapter?.setData(it)
    }

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_picker_image_demo
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APickerImageDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/nguyenhoanglam/ImagePicker"
                    )
                })
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = ImagePickerActivity::class.java.simpleName
        }
        adapter = ImageAdapter(this)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = adapter

        binding.pickImageButton.setSafeOnClickListener {
            start()
        }
        binding.launchFragmentButton.setSafeOnClickListener {
            launchFragment()
        }
    }

    private fun start() {
        val folderMode = binding.folderModeSwitch.isChecked
        val multipleMode = binding.multipleModeSwitch.isChecked
        val cameraOnly = binding.cameraOnlySwitch.isChecked
        val showCamera = binding.showCameraSwitch.isChecked
        val showNumberIndicator = binding.showNumberIndicatorSwitch.isChecked
        val alwaysShowDone = binding.alwaysShowDoneSwitch.isChecked

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
        val folderMode = binding.folderModeSwitch.isChecked
        val multipleMode = binding.multipleModeSwitch.isChecked
        val cameraOnly = binding.cameraOnlySwitch.isChecked
        val showCamera = binding.showCameraSwitch.isChecked
        val showNumberIndicator = binding.showNumberIndicatorSwitch.isChecked
        val alwaysShowDone = binding.alwaysShowDoneSwitch.isChecked

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
