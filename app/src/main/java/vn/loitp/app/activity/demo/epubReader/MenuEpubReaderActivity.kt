package vn.loitp.app.activity.demo.epubReader

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.loitpcore.annotation.IsFullScreen
import com.loitpcore.annotation.LogTag
import com.loitpcore.core.base.BaseApplication
import com.loitpcore.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LReaderUtil
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.function.epub.model.BookInfo
import com.loitpcore.function.epub.viewmodels.EpubViewModel
import kotlinx.android.synthetic.main.activity_demo_epub_reader.*
import vn.loitp.app.R

@LogTag("MenuEpubReaderActivity")
@IsFullScreen(false)
class MenuEpubReaderActivity : BaseFontActivity() {

    companion object {
        private const val MAX_BOOK_ASSET = 1
        private const val EXTENSION_EPUB = ".sqlite"
    }

    private var epubViewModel: EpubViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_demo_epub_reader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
        LUIUtil.setDelay(mls = 500, runnable = {
            ask()
        })
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuEpubReaderActivity::class.java.simpleName
        }
        gridBookInfo.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View?, i: Int, _: Long ->
                val bookInfo = adapterView.adapter.getItem(i) as BookInfo
                LReaderUtil.readEpub(
                    activity = this,
                    bookInfo = bookInfo,
                )
            }
    }

    private fun setupViewModels() {
        epubViewModel = getViewModel(EpubViewModel::class.java)
        epubViewModel?.let { vm ->
            vm.loadAssetActionLiveData.observe(this) { actionData ->
                logD("<<<loadAssetActionLiveData action " + BaseApplication.gson.toJson(actionData))
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar = progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar = progressBar)
                    if (isSuccess == true) {
                        actionData.data?.let { bookInfoList ->
                            val adapter = BookInfoGridAdapter(bookInfoList = bookInfoList)
                            gridBookInfo.adapter = adapter
                        }
                    }
                }
            }
            vm.loadDeviceAndAssetActionLiveData.observe(this) { actionData ->
                logD(
                    "<<<loadDeviceAndAssetActionLiveData action " + BaseApplication.gson.toJson(
                        actionData
                    )
                )
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess
                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar = progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar = progressBar)
                    if (isSuccess == true) {
                        actionData.data?.let { bookInfoList ->
                            val adapter = BookInfoGridAdapter(bookInfoList = bookInfoList)
                            gridBookInfo.adapter = adapter
                        }
                    }
                }
            }
        }
    }

    private fun ask() {
        LDialogUtil.showDialog2(
            context = this,
            title = "Chọn",
            msg = "Có 2 option",
            button1 = "Load tất cả epub có trong device và 1 file ở asset",
            button2 = "Load tất cả epub trong asset",
            onClickButton1 = {
                epubViewModel?.getListBookFromDeviceAndAsset()
            },
            onClickButton2 = {
                epubViewModel?.getListBookAllAsset(
                    maxBookAsset = MAX_BOOK_ASSET,
                    extensionEpub = EXTENSION_EPUB
                )
            }
        )
    }
}
