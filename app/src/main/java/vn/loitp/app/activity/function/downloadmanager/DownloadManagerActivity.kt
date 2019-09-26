package vn.loitp.app.activity.function.downloadmanager

import alirezat775.lib.downloader.Downloader
import alirezat775.lib.downloader.core.OnDownloadListener
import android.os.Bundle
import android.os.Handler
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.core.utilities.LStoreUtil
import kotlinx.android.synthetic.main.activity_download_manager.*
import loitp.basemaster.R
import java.io.File

class DownloadManagerActivity : BaseFontActivity() {
    private var downloader: Downloader? = null
    private val handler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false

        getDownloader()

        btStartDownload.setOnClickListener {
            getDownloader()
            downloader?.download()
        }
        btCancelDownload.setOnClickListener {
            downloader?.cancelDownload()
        }
        btPauseDownload.setOnClickListener {
            downloader?.pauseDownload()
        }
        btResumeDownload.setOnClickListener {
            getDownloader()
            downloader?.resumeDownload()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_download_manager
    }

    private fun getDownloader() {
        val path = LStoreUtil.getFolderPath(activity, "ZZZTestDownloader")
        LLog.d(TAG, "path $path")
        val map = HashMap<String, String>()
        map.put("token", "abc")
        downloader = Downloader.Builder(
                this,
                "https://s3-us-west-2.amazonaws.com/uw-s3-cdn/wp-content/uploads/sites/6/2017/11/04133712/waterfall.jpg"
        )
                .downloadDirectory(path)
                .fileName("abc", "jpg")
                .header(map)
                //.timeOut(10000)
                .downloadListener(object : OnDownloadListener {
                    override fun onStart() {
                        handler.post { tvCurrentStatus.text = "onStart" }
                        LLog.d(TAG, "onStart")
                    }

                    override fun onPause() {
                        handler.post { tvCurrentStatus.text = "onPause" }
                        LLog.d(TAG, "onPause")
                    }

                    override fun onResume() {
                        handler.post { tvCurrentStatus.text = "onResume" }
                        LLog.d(TAG, "onResume")
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                        handler.post {
                            tvCurrentStatus.text = "onProgressUpdate"
                            tvPercent.text = percent.toString().plus("%")
                            tvSize.text = getSize(downloadedSize)
                            tvTotalSize.text = getSize(totalSize)
                            sbDownloadProgress.progress = percent
                        }
                        LLog.d(TAG, "onProgressUpdate: percent --> $percent downloadedSize --> $downloadedSize totalSize --> $totalSize ")
                    }

                    override fun onCompleted(file: File?) {
                        handler.post { tvCurrentStatus.text = "onCompleted ${file?.path}" }
                        LLog.d(TAG, "onCompleted: file --> $file")
                    }

                    override fun onFailure(reason: String?) {
                        handler.post { tvCurrentStatus.text = "onFailure: reason --> $reason" }
                        LLog.d(TAG, "onFailure: reason --> $reason")
                    }

                    override fun onCancel() {
                        handler.post { tvCurrentStatus.text = "onCancel" }
                        LLog.d(TAG, "onCancel")
                    }
                }).build()
    }

    fun getSize(size: Int): String {
        var s = ""
        val kb = (size / 1024).toDouble()
        val mb = kb / 1024
        val gb = kb / 1024
        val tb = kb / 1024
        if (size < 1024) {
            s = "$size Bytes"
        } else if (size >= 1024 && size < 1024 * 1024) {
            s = String.format("%.2f", kb) + " KB"
        } else if (size >= 1024 * 1024 && size < 1024 * 1024 * 1024) {
            s = String.format("%.2f", mb) + " MB"
        } else if (size >= 1024 * 1024 * 1024 && size < 1024 * 1024 * 1024 * 1024) {
            s = String.format("%.2f", gb) + " GB"
        } else if (size >= 1024 * 1024 * 1024 * 1024) {
            s = String.format("%.2f", tb) + " TB"
        }
        return s
    }
}
