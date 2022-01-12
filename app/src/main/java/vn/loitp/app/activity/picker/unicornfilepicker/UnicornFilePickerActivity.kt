package vn.loitp.app.activity.picker.unicornfilepicker

import abhishekti7.unicorn.filepicker.UnicornFilePicker
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_unicorn_file_picker.*
import vn.loitp.app.R

@LogTag("UnicornFilePickerActivity")
@IsFullScreen(false)
class UnicornFilePickerActivity : BaseFontActivity() {

    companion object {
        const val REQ_UNICORN_FILE = 999
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_unicorn_file_picker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btPhoto.setSafeOnClickListener {
            UnicornFilePicker.from(this)
                .addConfigBuilder()
                .selectMultipleFiles(false)
                .showOnlyDirectory(true)
                .setRootDirectory(Environment.getExternalStorageDirectory().absolutePath)
                .showHiddenFiles(false)
                .setFilters(arrayOf("png", "jpg", "jpeg"))
                .addItemDivider(true)
                .theme(R.style.UnicornFilePicker_Dracula)
                .build()
                .forResult(REQ_UNICORN_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQ_UNICORN_FILE && resultCode == RESULT_OK) {
            val files = data?.getStringArrayListExtra("filePaths")
            var s = ""
            files?.forEach {
                s = s + "\n" + it
            }
            tv.text = s
        }
    }
}
