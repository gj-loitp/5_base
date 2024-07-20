package vn.loitp.up.a.func.pump

import android.annotation.SuppressLint
import android.os.Bundle
import com.huxq17.download.Pump
import com.huxq17.download.config.DownloadConfig
import com.huxq17.download.core.DownloadListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.APumpBinding
import vn.loitp.up.common.Constants

@LogTag("PumpActivity")
@IsFullScreen(false)
class PumpActivity : BaseActivityFont() {

    private lateinit var binding: APumpBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = APumpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
        }
        DownloadConfig.newBuilder()
            // Optional,set the maximum number of tasks to run, default 3.
            .setMaxRunningTaskNum(2)
            // Optional,set the minimum available storage space size for downloading to avoid insufficient storage space during downloading, default is 4kb.
            .setMinUsableStorageSpace(4 * 1024L)
            .build()

        binding.btPicture.setSafeOnClickListener {
            handleDownloadPicture()
        }
        binding.btPdf.setSafeOnClickListener {
            handleDownloadPdf()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // shutdown will stop all tasks and release some resource.
        Pump.shutdown()
    }

    @SuppressLint("SetTextI18n")
    private fun handleDownloadPicture() {
        Pump.newRequestToPicture(Constants.URL_IMG_1, "/loitp/pictures")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                    binding.tvStatus.text = "onProgress $progress%"
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                    binding.tvStatus.text = "Download Finished $filePath"
                }

                override fun onFailed() {
                    showShortError("Download failed")
                    binding.tvStatus.text = "Download failed"
                }
            })
            // Optionally,Set whether to repeatedly download the downloaded file,default false.
            .forceReDownload(true)
            // Optionally,Set how many threads are used when downloading,default 3.
            .threadNum(3)
            .setRetry(3, 200)
            .submit()
    }

    @SuppressLint("SetTextI18n")
    private fun handleDownloadPdf() {
        Pump.newRequestToDownload("http://www.pdf995.com/samples/pdf.pdf", "/loitp/pdf")
            .listener(object : DownloadListener() {

                override fun onProgress(progress: Int) {
                    binding.tvStatus.text = "onProgress $progress%"
                }

                override fun onSuccess() {
                    val filePath = downloadInfo.filePath
                    showShortInformation("Download Finished $filePath")
                    binding.tvStatus.text = "Download Finished $filePath"
                }

                override fun onFailed() {
                    showShortError("Download failed")
                    binding.tvStatus.text = "Download failed"
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
