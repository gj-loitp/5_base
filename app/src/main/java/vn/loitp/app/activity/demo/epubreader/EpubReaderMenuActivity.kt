package vn.loitp.app.activity.demo.epubreader

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.core.utilities.LReaderUtil
import com.core.utilities.LUIUtil
import com.function.epub.model.BookInfo
import com.function.epub.task.GetListBookFromDeviceAndAssetTask
import com.function.epub.viewmodels.EpubViewModel
import com.interfaces.Callback2
import kotlinx.android.synthetic.main.activity_demo_epub_reader.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_demo_epub_reader)
@LogTag("loitppEpubReaderMenuActivity")
@IsFullScreen(false)
class EpubReaderMenuActivity : BaseFontActivity() {

    companion object {
        private const val MAX_BOOK_ASSET = 1
        private const val EXTENSION_EPUB = ".sqlite"
    }

    private var getListBookFromDeviceAndAssetTask: GetListBookFromDeviceAndAssetTask? = null
    private var epubViewModel: EpubViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
        LUIUtil.setDelay(mls = 500, runnable = Runnable {
            ask()
        })
    }

    private fun setupViews() {
        gridBookInfo.onItemClickListener = AdapterView.OnItemClickListener { adapterView: AdapterView<*>, _: View?, i: Int, _: Long ->
            val bookInfo = adapterView.adapter.getItem(i) as BookInfo
            LReaderUtil.readEpub(activity = this, bookInfo = bookInfo, admobAdIdBanner = getString(R.string.str_b))
        }
    }

    private fun setupViewModels() {
        epubViewModel = getViewModel(EpubViewModel::class.java)
        epubViewModel?.let { vm ->
            vm.loadAssetActionLiveData.observe(this, Observer { actionData ->
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
            })
        }
    }

    private fun ask() {
        LDialogUtil.showDialog2(context = this,
                title = "Chọn",
                msg = "Có 2 option",
                button1 = "Load tất cả epub có trong device và 1 file ở asset",
                button2 = "Load tất cả epub trong asset",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        //lấy list epub ở trên all device và 1 file ở asset folder rồi show lên UI
//                        getListBookFromDeviceAndAssetTask = GetListBookFromDeviceAndAssetTask(this@EpubReaderMenuActivity, object : GetListBookFromDeviceAndAssetTask.Callback {
//                            override fun onPreExecute() {
//                                LDialogUtil.showProgress(progressBar)
//                            }
//
//                            override fun onPostExecute(bookInfoList: ArrayList<BookInfo>) {
//                                logD("onPostExecute " + bookInfoList.size)
//                                LDialogUtil.hideProgress(progressBar)
//                                val adapter = BookInfoGridAdapter(this@EpubReaderMenuActivity, bookInfoList)
//                                gridBookInfo.adapter = adapter
//                            }
//                        })
//                        getListBookFromDeviceAndAssetTask?.execute()
                    }

                    override fun onClick2() {
                        epubViewModel?.getListBookAllAsset(maxBookAsset = MAX_BOOK_ASSET, extensionEpub = EXTENSION_EPUB)
                    }
                })
    }

    override fun onDestroy() {
        getListBookFromDeviceAndAssetTask?.cancel(true)
        super.onDestroy()
    }

}
