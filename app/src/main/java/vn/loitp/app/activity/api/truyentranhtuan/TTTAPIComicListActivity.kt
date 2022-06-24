package vn.loitp.app.activity.api.truyentranhtuan

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isVisible
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.helper.ttt.helper.ComicUtils
import com.core.helper.ttt.model.comictype.ComicType
import com.core.helper.ttt.viewmodel.TTTViewModel
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_api_ttt_comic_list.*
import kotlinx.android.synthetic.main.activity_api_ttt_comic_list.textView
import vn.loitp.app.R

@LogTag("TTTAPIComicListActivity")
@IsFullScreen(false)
class TTTAPIComicListActivity : BaseFontActivity() {
    private var tttViewModel: TTTViewModel? = null
    private var comicTypeList = ArrayList<ComicType>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_comic_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
        setupViewModels()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.viewShadow?.isVisible = true
            this.tvTitle?.text = TTTAPIComicListActivity::class.java.simpleName
        }
        LDialogUtil.hideProgress(progressBar)
        comicTypeList.addAll(ComicUtils.comicTypeList)

        btSelect.setSafeOnClickListener {
            showDialogSelect()
        }
        btTestQueuedMutableLiveData.setSafeOnClickListener {
            for (i in 0..10) {
                tttViewModel?.setStringQueued("Number $i")
            }
        }
    }

    private fun setupViewModels() {
        tttViewModel = getViewModel(TTTViewModel::class.java)
        tttViewModel?.let { vm ->
            vm.comicTypeLiveEvent.observe(this) { comicType ->
                logD(">>>comicTypeLiveEvent comicType ${comicType.url}")
                vm.getListComic(link = comicType.url)
            }
            vm.testStringQueuedMutableLiveData.observe(this) { s ->
                logD(">>>loitpp testStringQueuedMutableLiveData $s")
            }
            vm.listComicActionLiveData.observe(this) { actionData ->
                val isDoing = actionData.isDoing
                val isSuccess = actionData.isSuccess

                if (isDoing == true) {
                    LDialogUtil.showProgress(progressBar)
                } else {
                    LDialogUtil.hideProgress(progressBar)
                    if (isSuccess == true) {
                        val listComic = actionData.data
                        listComic?.let {
                            LUIUtil.printBeautyJson(o = it, textView = textView)
                        }
                    }
                }
            }
        }
    }

    private fun showDialogSelect() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn thể loại:")
        val items = arrayOfNulls<String>(comicTypeList.size)
        for (i in comicTypeList.indices) {
            items[i] = comicTypeList[i].type
        }
        builder.setItems(items) { _: DialogInterface?, position: Int ->
            tttViewModel?.setComicType(comicTypeList[position])
        }
        val dialog = builder.create()
        dialog.show()
    }
}
