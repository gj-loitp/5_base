package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil.Companion.printBeautyJson
import kotlinx.android.synthetic.main.activity_api_ttt_comic_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.ComicUtils
import vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist.GetComicTask
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import vn.loitp.app.activity.api.truyentranhtuan.model.comictype.ComicType

class TTTAPIComicListActivity : BaseFontActivity() {
    private var comicTypeList = ArrayList<ComicType>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        indicatorView.hide()
        comicTypeList.addAll(ComicUtils.comicTypeList)

        btSelect.setOnClickListener {
            showDialogSelect()
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_comic_list
    }

    private fun showDialogSelect() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Chọn thể loại:")
        val items = arrayOfNulls<String>(comicTypeList.size)
        for (i in comicTypeList.indices) {
            items[i] = comicTypeList[i].type
        }
        builder.setItems(items) { _: DialogInterface?, position: Int ->
            textView.text = ""
            tvTitle.text = ""

            GetComicTask(activity, comicTypeList[position].url, indicatorView, object : GetComicTask.Callback {
                @SuppressLint("SetTextI18n")
                override fun onSuccess(comicList: List<Comic>) {
                    printBeautyJson(comicList, textView)
                    tvTitle.text = "Danh sách truyện: " + comicList.size
                }

                override fun onError() {
                    showShort("Error")
                }
            }).execute()
        }
        val dialog = builder.create()
        dialog.show()
    }
}
