package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_fav_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.GetFavListTask
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic

@LayoutId(R.layout.activity_api_ttt_fav_list)
class TTTAPIFavListActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        indicatorView.hide()
        favList
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    private val favList: Unit
        @SuppressLint("SetTextI18n")
        get() {
            GetFavListTask(mActivity = activity,
                    callback = object : GetFavListTask.Callback {
                        override fun onSuccess(comicList: List<Comic>) {
                            LUIUtil.printBeautyJson(o = comicList, textView = textView)
                            tvTitle.text = "Danh sách yêu thích: " + comicList.size
                        }
                    }).execute()
        }
}