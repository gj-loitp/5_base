package vn.loitp.a.api.truyentranhtuan

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.ext.setSafeOnClickListener
import com.loitp.core.helper.ttt.helper.ComicUtils
import com.loitp.core.helper.ttt.model.comictype.ComicType
import com.loitp.core.helper.ttt.viewmodel.TTTViewModel
import com.loitp.core.utilities.LDialogUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_ttt_api_comic_list.*
import vn.loitp.R

@LogTag("TTTAPIComicListActivity")
@IsFullScreen(false)
class TTTAPIComicListActivity : BaseFontActivity() {
    private var tttViewModel: TTTViewModel? = null
    private var comicTypeList = ArrayList<ComicType>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_ttt_api_comic_list
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
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
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
