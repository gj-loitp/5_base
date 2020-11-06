package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_comic_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils
import vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist.GetComicTask
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import vn.loitp.app.activity.api.truyentranhtuan.model.comictype.ComicType

@LogTag("TTTAPIComicListActivity")
@IsFullScreen(false)
class TTTAPIComicListActivity : BaseFontActivity() {
    private var comicTypeList = ArrayList<ComicType>()

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_comic_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        indicatorView.hide()
        comicTypeList.addAll(ComicUtils.comicTypeList)

        btSelect.setOnClickListener {
            showDialogSelect()
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
            textView.text = ""
            tvTitle.text = ""

            indicatorView.smoothToShow()
            GetComicTask(
                    link = comicTypeList[position].url,
                    callback = object : GetComicTask.Callback {
                        @SuppressLint("SetTextI18n")
                        override fun onSuccess(comicList: List<Comic>) {
                            LUIUtil.printBeautyJson(o = comicList, textView = textView)
                            tvTitle?.text = "Danh sách truyện: " + comicList.size
                            indicatorView?.smoothToHide()
                        }

                        override fun onError() {
                            showShortError("Error")
                            indicatorView?.smoothToHide()
                        }
                    }).execute()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
