package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_chap_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.chaplist.GetChapTask
import vn.loitp.app.activity.api.truyentranhtuan.model.chap.TTTChap

@LayoutId(R.layout.activity_api_ttt_chap_list)
@LogTag("TTTAPIChapListActivity")
class TTTAPIChapListActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val urlComic = "http://truyentranhtuan.com/one-piece/"
        GetChapTask(context = applicationContext,
                url = urlComic,
                callback = object : GetChapTask.Callback {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(tttChap: TTTChap?) {
                        tttChap?.let {
                            LUIUtil.printBeautyJson(tttChap, textView)
                            indicatorView.smoothToHide()
                            tvTitle.text = "Chap truyện One Piece - size: " + tttChap.chaps?.chap?.size
                        }
                    }

                    override fun onError() {
                        showShort("onError")
                    }
                }).execute()
    }

    override fun setFullScreen(): Boolean {
        return false
    }
}
