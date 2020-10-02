package com.core.helper.girl.ui

import alirezat775.lib.downloader.core.OnDownloadListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.girl.model.GirlPageDetail
import com.core.utilities.LAppResource
import com.core.utilities.LSocialUtil
import com.core.utilities.LStoreUtil
import com.views.viewpager.viewpagertransformers.ZoomOutSlideTransformer
import kotlinx.android.synthetic.main.l_activity_girl_slide.*
import java.io.File

@LogTag("GalleryCoreSlideActivity")
@IsFullScreen(false)
class GirlSlideActivity : BaseFontActivity() {

    companion object {
        const val KEY_LIST_DATA = "KEY_LIST_DATA"
        const val KEY_POSITION = "KEY_POSITION"
    }

    private val listData = ArrayList<GirlPageDetail>()
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_girl_slide)

        setupData()
        setupViews()
    }

    private fun setupData() {
        intent?.getSerializableExtra(KEY_LIST_DATA)?.let {
            listData.addAll(it as ArrayList<GirlPageDetail>)
        }
        currentPosition = intent?.getIntExtra(KEY_POSITION, 0) ?: 0
    }

    private fun setupViews() {
        val slidePagerAdapter = SlidePagerAdapter(supportFragmentManager)
        viewPager.setPageTransformer(true, ZoomOutSlideTransformer())
        viewPager.adapter = slidePagerAdapter
        viewPager.currentItem = currentPosition

        btDownload.setOnClickListener {
            val src = listData[currentPosition].src
            src?.let {
                save(url = it)
            }
        }
        btShare.setOnClickListener {
            val src = listData[currentPosition].src
            src?.let {
                LSocialUtil.share(activity = this, msg = it)
            }
        }
        btReport.setOnClickListener {
            LSocialUtil.sendEmail(context = this)
        }
    }

    private inner class SlidePagerAdapter internal constructor(fm: FragmentManager)
        : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment {
            val frmIvSlideGirl = FrmIvSlideGirl()
            val bundle = Bundle()
            bundle.putSerializable(FrmIvSlideGirl.KEY_DATA, listData[position])
            frmIvSlideGirl.arguments = bundle
            return frmIvSlideGirl
        }

        override fun getCount(): Int {
            return listData.size
        }
    }

    private fun save(url: String) {
        val downloader = LStoreUtil.getDownloader(
                folderName = LAppResource.getString(R.string.app_name),
                url = url,
                fileName = "Img" + System.currentTimeMillis(),
                fileNameExtension = "png",
                onDownloadListener = object : OnDownloadListener {
                    override fun onCancel() {
                    }

                    override fun onCompleted(file: File?) {
                        file?.let {
                            showLong("Saved in ${it.path}")
                            LStoreUtil.sendBroadcastMediaScan(it)
                        }
                    }

                    override fun onFailure(reason: String?) {
                        showLong("Download failed $reason")
                    }

                    override fun onPause() {
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                    }

                    override fun onResume() {
                    }

                    override fun onStart() {
                    }

                }
        )
        downloader?.download()
    }
}
