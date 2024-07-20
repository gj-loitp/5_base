package vn.loitp.up.a.picker.attachmentManager

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.getRealPathFromURI
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.mirza.attachmentmanager.managers.AttachmentManager
import com.mirza.attachmentmanager.models.AttachmentDetail
import vn.loitp.databinding.AAttachmentManagerBinding

@LogTag("AttachmentManagerActivity")
@IsFullScreen(false)
class AttachmentManagerActivity : BaseActivityFont() {
    //TODO do not work if target sdk 33
    private lateinit var binding: AAttachmentManagerBinding

    private var attachmentManager: AttachmentManager? = null
    private var allAttachments: ArrayList<AttachmentDetail>? = arrayListOf()
    private var mLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            attachmentManager?.manipulateAttachments(this, result.resultCode, result.data)?.let {

                if (it.size > 0 && it[0].mimeType?.contains("pdf", ignoreCase = true) == true) {
                    if (it.size > 0 && it[0].size!! <= 2000000) {
                        allAttachments?.addAll(it)
                    } else {
                        Toast.makeText(
                            this,
                            "File size can't be more than 2MB",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else if (it.size > 0) {
                    allAttachments?.addAll(it)
                } else {
                    // do sth
                }
            }
            var s = ""
            allAttachments?.forEach {
//                logD(">>> ${it.path}")
                val realPath = this.getRealPathFromURI(it.uri)
                s += it.path + " ---> " + realPath + "\n\n\n"
            }
            binding.tvPath.text = s
        }
    var gallery = arrayOf(
//        "image/png",
//        "image/jpg",
//        "image/jpeg",
        "image/*",
        "video/*"
    )
    private var files = arrayOf(
        "application/msword",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .ppt & .pptx
        "application/pdf",
        "video/*",
        "image/*",
    )

//    override fun setLayoutResourceId(): Int {
//        return R.layout.a_attachment_manager
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AAttachmentManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        attachmentManager = AttachmentManager.AttachmentBuilder(this) // must pass Context
            .fragment(null) // pass fragment reference if you are in fragment
            .setUiTitle(getString(com.mirza.attachmentmanager.R.string.m_choose)) // title of dialog or bottom sheet
            .allowMultiple(true) // set true if you want make multiple selection, default is false
            .asBottomSheet(true) // set true if you need to show selection as bottom sheet, default is as Dialog
            .setOptionsTextColor(com.loitp.R.color.red)
            .setImagesColor(com.loitp.R.color.deepPink)
//            .hide(HideOption.DOCUMENT)// You can hide any option do you want
            .setMaxPhotoSize(200000) // Set max camera photo size in bytes
            .galleryMimeTypes(gallery) // mime types for gallery
            .filesMimeTypes(files) // mime types for files
            .build()

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
                            url = "https://github.com/Zaid-Mirza/AttachmentManager"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AttachmentManagerActivity::class.java.simpleName
        }
        binding.btPick.setSafeOnClickListener {
            attachmentManager?.openSelection(mLauncher)
        }
        binding.btCamera.setSafeOnClickListener {
            attachmentManager?.startCamera(mLauncher)
        }
        binding.btGallery.setSafeOnClickListener {
            attachmentManager?.openGallery(mLauncher)
        }
        binding.btFile.setSafeOnClickListener {
            attachmentManager?.openFileSystem(mLauncher)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        attachmentManager?.handlePermissionResponse(
            requestCode,
            permissions,
            grantResults,
            mLauncher
        )
    }
}
