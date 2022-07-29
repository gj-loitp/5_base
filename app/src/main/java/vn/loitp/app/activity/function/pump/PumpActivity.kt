package vn.loitp.app.activity.function.pump

import android.os.Bundle
import androidx.core.view.isVisible
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.function.pump.download.Pump
import com.loitpcore.function.pump.download.config.DownloadConfig
import com.loitpcore.function.pump.download.core.DownloadListener
import com.loitpcore.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_pump.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

@LogTag("PumpActivity")
@IsFullScreen(false)
class PumpActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_pump
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = PumpActivity::class.java.simpleName
        }
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
        Pump.newRequestToPicture(Constants.URL_IMG_1, "/loitp/pictures")
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
