package vn.loitp.app.activity.function.downloadmanager

import alirezat775.lib.downloader.Downloader
import alirezat775.lib.downloader.core.OnDownloadListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_download_manager.*
import vn.loitp.app.R
import java.io.File

@LayoutId(R.layout.activity_download_manager)
@LogTag("loitppDownloadManagerActivity")
@IsFullScreen(false)
class DownloadManagerActivity : BaseFontActivity() {

    companion object {
        const val FOLDER = ".DownloadManager"
        const val FILE_NAME = "LoiDepTrai.txt"
    }

    private var downloader: Downloader? = null
    private var lStoreUtilModel: LStoreUtilModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        btStartDownload.setSafeOnClickListener {
            getDownloader()
            downloader?.download()
        }
        btCancelDownload.setSafeOnClickListener {
            downloader?.cancelDownload()
        }
        btPauseDownload.setSafeOnClickListener {
            downloader?.pauseDownload()
        }
        btResumeDownload.setSafeOnClickListener {
            getDownloader()
            downloader?.resumeDownload()
        }
        btTestRandomColor.setOnClickListener {
            val randomColor = LStoreUtil.randomColor
            btTestRandomColor.setBackgroundColor(randomColor)
        }
        btTestRandomColorLight.setOnClickListener {
            val randomColor = LStoreUtil.randomColorLight
            btTestRandomColorLight.setBackgroundColor(randomColor)
        }
        btTestWriteToFile.setSafeOnClickListener {
            lStoreUtilModel?.writeToFile(folder = FOLDER, fileName = FILE_NAME, body = "Body btTestWriteToFile setSafeOnClickListener\n${System.currentTimeMillis()}")
        }
        btReadTxtFromFolder.setSafeOnClickListener {
            lStoreUtilModel?.readTxtFromFolder(folderName = FOLDER, fileName = FILE_NAME)
        }
        btReadTxtFromRawFolder.setSafeOnClickListener {
            lStoreUtilModel?.readTxtFromRawFolder(R.raw.loitp)
        }
        btReadTxtFromAsset.setSafeOnClickListener {
            lStoreUtilModel?.readTxtFromAsset("colors.json")
        }
    }

    private fun setupViewModels() {
        lStoreUtilModel = getViewModel(LStoreUtilModel::class.java)
        lStoreUtilModel?.let { vm ->
            vm.writeToFileActionLiveData.observe(this, Observer { actionData ->
                logD("<<<writeToFileActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLong("Saved: " + data?.path)
                    }
                }
            })
            vm.readTxtFromFolderActionLiveData.observe(this, Observer { actionData ->
                logD("<<<readTxtFromFolderActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLong("readTxtFromFolderActionLiveData:\n$data")
                    }
                }
            })
            vm.readTxtFromRawFolderActionLiveData.observe(this, Observer { actionData ->
                logD("<<<readTxtFromRawFolderActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLong("readTxtFromRawFolderActionLiveData:\n$data")
                    }
                }
            })
            vm.readTxtFromAssetActionLiveData.observe(this, Observer { actionData ->
                logD("<<<readTxtFromAssetActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLong("readTxtFromAssetActionLiveData:\n$data")
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDownloader() {
        downloader = LStoreUtil.getDownloader(
                folderName = LStoreUtil.FOLDER_PICTURES + "/" + "ZZZTestDownloader",
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIwOGQ3MWQ2Zi0xZTc0LTYwYjQtOWJmMC1mM2E0YzVkMTkwZGUiLCJyb2xlIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAyIiwianRpIjoiMzUxMmZhM2YtYWMzNi00YmM2LWI5ZTEtOTEyMzc5Y2NlZjQ1IiwiRGF0YVR5cGVzIjoiMiIsIm5iZiI6MTU2OTQ3OTc1OSwiZXhwIjoxNTY5NTY5NzU5LCJpYXQiOjE1Njk0Nzk3NTksImlzcyI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biIsImF1ZCI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biJ9.6IfkQkMhI0g-XAbKdHNSH5HiP8fsRAJxnpojjyqwFBI,",
                url = "https://images.unsplash.com/photo-1499909762747-883b3641e787?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1336&q=80",
                fileName = "abc" + System.currentTimeMillis(),
                fileNameExtension = "jpg",
                onDownloadListener = object : OnDownloadListener {
                    override fun onCancel() {
                        tvCurrentStatus.text = "onCancel"
                        logD("onCancel")
                    }

                    override fun onCompleted(file: File?) {
                        tvCurrentStatus.text = "onCompleted ${file?.path}"
                        logD("onCompleted: file --> $file")

                        LStoreUtil.sendBroadcastMediaScan(file)
                    }

                    override fun onFailure(reason: String?) {
                        tvCurrentStatus.text = "onFailure: reason --> $reason"
                        logE("onFailure: reason --> $reason")
                    }

                    override fun onPause() {
                        tvCurrentStatus.text = "onPause"
                        logD("onPause")
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                        tvCurrentStatus.text = "onProgressUpdate"
                        tvPercent.text = percent.toString().plus("%")
                        tvSize.text = LStoreUtil.getSize(downloadedSize)
                        tvTotalSize.text = LStoreUtil.getSize(totalSize)
                        sbDownloadProgress.progress = percent
                        logD("onProgressUpdate: percent --> $percent downloadedSize --> $downloadedSize totalSize --> $totalSize ")
                    }

                    override fun onResume() {
                        tvCurrentStatus.text = "onResume"
                        logD("onResume")
                    }

                    override fun onStart() {
                        tvCurrentStatus.text = "onStart"
                        logD("onStart")
                    }

                }
        )
    }
}
