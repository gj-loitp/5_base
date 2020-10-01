package vn.loitp.app.activity.function.downloadmanager

import alirezat775.lib.downloader.Downloader
import alirezat775.lib.downloader.core.OnDownloadListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LStoreUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_download_manager.*
import vn.loitp.app.R
import java.io.File

@LayoutId(R.layout.activity_download_manager)
@LogTag("DownloadManagerActivity")
@IsFullScreen(false)
class DownloadManagerActivity : BaseFontActivity() {
    private var downloader: Downloader? = null
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
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
    }

    @SuppressLint("SetTextI18n")
    private fun getDownloader() {
        val path = LStoreUtil.getFolderPath(context = this, folderName = "ZZZTestDownloader")
        logD("getDownloader path $path")
        val map = HashMap<String, String>()
        map["Authorization"] = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYW1laWQiOiIwOGQ3MWQ2Zi0xZTc0LTYwYjQtOWJmMC1mM2E0YzVkMTkwZGUiLCJyb2xlIjoiMDAwMDAwMDAtMDAwMC0wMDAwLTAwMDAtMDAwMDAwMDAwMDAyIiwianRpIjoiMzUxMmZhM2YtYWMzNi00YmM2LWI5ZTEtOTEyMzc5Y2NlZjQ1IiwiRGF0YVR5cGVzIjoiMiIsIm5iZiI6MTU2OTQ3OTc1OSwiZXhwIjoxNTY5NTY5NzU5LCJpYXQiOjE1Njk0Nzk3NTksImlzcyI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biIsImF1ZCI6Imh0dHBzOi8vZGV2LXBvcnRhbC52aW5ob21lcy52biJ9.6IfkQkMhI0g-XAbKdHNSH5HiP8fsRAJxnpojjyqwFBI"

        val url = "https://images.unsplash.com/photo-1499909762747-883b3641e787?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1336&q=80"
        downloader = Downloader.Builder(mContext = this, mUrl = url)
                .downloadDirectory(path)
                .fileName(fileName = "abc" + System.currentTimeMillis(), extension = "jpg")
                .header(map)
                //.timeOut(10000)
                .downloadListener(object : OnDownloadListener {
                    override fun onStart() {
                        handler.post {
                            tvCurrentStatus.text = "onStart"
                        }
                        logD("onStart")
                    }

                    override fun onPause() {
                        handler.post {
                            tvCurrentStatus.text = "onPause"
                        }
                        logD("onPause")
                    }

                    override fun onResume() {
                        handler.post {
                            tvCurrentStatus.text = "onResume"
                        }
                        logD("onResume")
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                        handler.post {
                            tvCurrentStatus.text = "onProgressUpdate"
                            tvPercent.text = percent.toString().plus("%")
                            tvSize.text = LStoreUtil.getSize(downloadedSize)
                            tvTotalSize.text = LStoreUtil.getSize(totalSize)
                            sbDownloadProgress.progress = percent
                        }
                        logD("onProgressUpdate: percent --> $percent downloadedSize --> $downloadedSize totalSize --> $totalSize ")
                    }

                    override fun onCompleted(file: File?) {
                        handler.post {
                            tvCurrentStatus.text = "onCompleted ${file?.path}"
                        }
                        logD("onCompleted: file --> $file")

                        LStoreUtil.sendBroadcastMediaScan(file)
                    }

                    override fun onFailure(reason: String?) {
                        handler.post {
                            tvCurrentStatus.text = "onFailure: reason --> $reason"
                        }
                        logE("onFailure: reason --> $reason")
                    }

                    override fun onCancel() {
                        handler.post {
                            tvCurrentStatus.text = "onCancel"
                        }
                        logD("onCancel")
                    }
                }).build()
    }
}
