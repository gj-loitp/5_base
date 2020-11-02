package vn.loitp.app.activity.function.downloadmanager

import alirezat775.lib.downloader.Downloader
import alirezat775.lib.downloader.core.OnDownloadListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_download_manager.*
import vn.loitp.app.R
import java.io.File

@LogTag("DownloadManagerActivity")
@IsFullScreen(false)
class DownloadManagerActivity : BaseFontActivity() {

    companion object {
        const val FOLDER = ".DownloadManager"
        const val FILE_NAME = "LoiDepTrai.txt"
    }

    private var downloaderPhoto: Downloader? = null
    private var downloaderZip: Downloader? = null
    private var lStoreUtilModel: LStoreUtilModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_download_manager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        getDownloaderPhoto()
//        getDownloaderZipGoogle()

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        btStartDownload.setOnClickListener {
            getDownloaderPhoto()
            downloaderPhoto?.download()
        }
        btCancelDownload.setSafeOnClickListener {
            downloaderPhoto?.cancelDownload()
        }
        btPauseDownload.setSafeOnClickListener {
            downloaderPhoto?.pauseDownload()
        }
        btResumeDownload.setSafeOnClickListener {
            getDownloaderPhoto()
            downloaderPhoto?.resumeDownload()
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
        btStartDownloadZipGoogle.setSafeOnClickListener {
            getDownloaderZipGoogle()
            downloaderZip?.download()
        }
    }

    private fun setupViewModels() {
        lStoreUtilModel = getViewModel(LStoreUtilModel::class.java)
        lStoreUtilModel?.let { vm ->
            vm.writeToFileActionLiveData.observe(this, { actionData ->
                logD("<<<writeToFileActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLongInformation("Saved: " + data?.path)
                    }
                }
            })
            vm.readTxtFromFolderActionLiveData.observe(this, { actionData ->
                logD("<<<readTxtFromFolderActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLongInformation("readTxtFromFolderActionLiveData:\n$data")
                    }
                }
            })
            vm.readTxtFromRawFolderActionLiveData.observe(this, { actionData ->
                logD("<<<readTxtFromRawFolderActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLongInformation("readTxtFromRawFolderActionLiveData:\n$data")
                    }
                }
            })
            vm.readTxtFromAssetActionLiveData.observe(this, { actionData ->
                logD("<<<readTxtFromAssetActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLongInformation("readTxtFromAssetActionLiveData:\n$data")
                    }
                }
            })
            vm.unzipActionLiveData.observe(this, { actionData ->
                logD("<<<unzipActionLiveData observe " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                if (isDoing == true) {
                    layoutProgress.visibility = View.VISIBLE
                } else {
                    layoutProgress.visibility = View.GONE
                    if (actionData.isSuccess == true) {
                        val data = actionData.data
                        showLongInformation("unzipActionLiveData:\n$data")

                    } else {
                        showLongError(actionData?.errorResponse?.message)
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDownloaderPhoto() {
        downloaderPhoto = LStoreUtil.getDownloader(
                folderName = Environment.DIRECTORY_PICTURES + "/" + "ZZZTestDownloader",
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIwOGQ3MWQ2Zi0xZTc0LTYwYjQtOWJmMC1mM2E0YzVkMTkwZGUiLCJyb2xlIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAyIiwianRpIjoiMzUxMmZhM2YtYWMzNi00YmM2LWI5ZTEtOTEyMzc5Y2NlZjQ1IiwiRGF0YVR5cGVzIjoiMiIsIm5iZiI6MTU2OTQ3OTc1OSwiZXhwIjoxNTY5NTY5NzU5LCJpYXQiOjE1Njk0Nzk3NTksImlzcyI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biIsImF1ZCI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biJ9.6IfkQkMhI0g-XAbKdHNSH5HiP8fsRAJxnpojjyqwFBI,",
                url = "https://images.unsplash.com/photo-1499909762747-883b3641e787?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1336&q=80",
//                url = "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg",
                onDownloadListener = object : OnDownloadListener {
                    override fun onCancel() {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onCancel"
                        }
                        logD("onCancel")
                    }

                    override fun onCompleted(file: File?) {
                        runOnUiThread {
                            file?.let {
                                tvCurrentStatus?.text = "onCompleted ${it.path}"
                                LStoreUtil.sendBroadcastMediaScan(it)
                            }
                        }
                        logD("onCompleted: file --> $file")
                    }

                    override fun onFailure(reason: String?) {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onFailure: reason --> $reason"
                        }
                        logE("onFailure: reason --> $reason")
                    }

                    override fun onPause() {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onPause"
                        }
                        logD("onPause")
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onProgressUpdate"
                            tvPercent?.text = percent.toString().plus("%")
                            tvSize?.text = LStoreUtil.getSize(downloadedSize)
                            tvTotalSize?.text = LStoreUtil.getSize(totalSize)
                            sbDownloadProgress?.progress = percent
                        }
//                        logD("onProgressUpdate: percent --> $percent downloadedSize --> $downloadedSize totalSize --> $totalSize ")
                    }

                    override fun onResume() {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onResume"
                        }
                        logD("onResume")
                    }

                    override fun onStart() {
                        runOnUiThread {
                            tvCurrentStatus?.text = "onStart"
                        }
                        logD("onStart")
                    }

                }
        )
    }

    @SuppressLint("SetTextI18n")
    private fun getDownloaderZipGoogle() {
        downloaderZip = LStoreUtil.getDownloader(
                url = "https://drive.google.com/uc?export=download&id=0B0-bfr9v36LUenRQNXZmWWIyS2c",
                onDownloadListener = object : OnDownloadListener {
                    override fun onCancel() {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onCancel"
                        }
                        logD("getDownloaderZipGoogle onCancel")
                    }

                    override fun onCompleted(file: File?) {
                        runOnUiThread {
                            file?.let {
                                tvCurrentStatusZip?.text = "onCompleted ${it.path}"
                                lStoreUtilModel?.unzip(file = it)
                            }
                        }
                        logD("getDownloaderZipGoogle onCompleted: file --> $file")
                    }

                    override fun onFailure(reason: String?) {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onFailure: reason --> $reason"
                        }
                        logE("getDownloaderZipGoogle onFailure: reason --> $reason")
                    }

                    override fun onPause() {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onPause"
                        }
                        logD("getDownloaderZipGoogle onPause")
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onProgressUpdate"
                            tvPercentZip?.text = percent.toString().plus("%")
                            tvSizeZip?.text = LStoreUtil.getSize(downloadedSize)
                            tvTotalSizeZip?.text = LStoreUtil.getSize(totalSize)
                            sbDownloadProgressZip?.progress = percent
                        }
                        logD("getDownloaderZipGoogle onProgressUpdate: percent --> $percent downloadedSize --> $downloadedSize totalSize --> $totalSize ")
                    }

                    override fun onResume() {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onResume"
                        }
                        logD("getDownloaderZipGoogle onResume")
                    }

                    override fun onStart() {
                        runOnUiThread {
                            tvCurrentStatusZip?.text = "onStart"
                        }
                        logD("getDownloaderZipGoogle onStart")
                    }

                }
        )
    }
}
