package vn.loitp.app.activity.function.pump

import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.function.pump.download.Pump
import com.function.pump.download.config.DownloadConfig
import com.function.pump.download.core.DownloadListener
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_pump.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("PumpActivity")
@IsFullScreen(false)
class PumpActivity : BaseFontActivity() {
    companion object {
        private const val TAG = "groupA"
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pump
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        DownloadConfig.newBuilder()
            // Optional,set the maximum number of tasks to run, default 3.
            .setMaxRunningTaskNum(2)
            // Optional,set the minimum available storage space size for downloading to avoid insufficient storage space during downloading, default is 4kb.
            .setMinUsableStorageSpace(4 * 1024L)
            .build()

        btPicture.setSafeOnClickListener {
            handleDownloadPicture()
        }
        btPdf.setSafeOnClickListener {
            handleDownloadPdf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // shutdown will stop all tasks and release some resource.
        Pump.shutdown()
    }

    private fun handleDownloadPicture() {
        Pump.newRequestToPicture(Constants.URL_IMG, "/loitp/picture")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                    tvStatus.text = "onProgress $progress%"
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                    tvStatus.text = "Download Finished $filePath"
                }

                override fun onFailed() {
                    showShortError("Download failed")
                    tvStatus.text = "Download failed"
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }

    private fun handleDownloadPdf() {
        Pump.newRequestToDownload("http://www.pdf995.com/samples/pdf.pdf", "/loitp/pdf")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                    tvStatus.text = "onProgress $progress%"
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                    tvStatus.text = "Download Finished $filePath"
                }

                override fun onFailed() {
                    showShortError("Download failed")
                    tvStatus.text = "Download failed"
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }
}
