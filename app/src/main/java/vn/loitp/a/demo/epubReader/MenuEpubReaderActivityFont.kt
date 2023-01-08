package vn.loitp.a.demo.epubReader

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.base.BaseApplication
import com.loitp.core.ext.*
import com.loitp.func.epub.model.BookInfo
import com.loitp.func.epub.vm.EpubViewModel
import kotlinx.android.synthetic.main.a_demo_epub_reader.*
import vn.loitp.R

@LogTag("MenuEpubReaderActivity")
@IsFullScreen(false)
class MenuEpubReaderActivityFont : BaseActivityFont() {

    companion object {
        private const val MAX_BOOK_ASSET = 1
        private const val EXTENSION_EPUB = ".sqlite"
    }

    private var epubViewModel: EpubViewModel? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.a_demo_epub_reader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
        setDelay(mls = 500, runnable = {
            ask()
        })
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = MenuEpubReaderActivityFont::class.java.simpleName
        }
        gridBookInfo.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View?, i: Int, _: Long ->
                val bookInfo = adapterView.adapter.getItem(i) as BookInfo
                this.readEpub(
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
                    progressBar.showProgress()
                } else {
                    progressBar.hideProgress()
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
                    progressBar.showProgress()
                } else {
                    progressBar.hideProgress()
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
        this.showDialog2(
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
