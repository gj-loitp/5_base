package vn.loitp.app.activity.api.truyentranhtuan

import android.annotation.SuppressLint
import android.os.Bundle
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_page_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist.GetReadImgTask

@LayoutId(R.layout.activity_api_ttt_page_list)
@LogTag("TTTAPIPageListActivity")
class TTTAPIPageListActivity : BaseFontActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentLink = "http://truyentranhtuan.com/one-piece-chuong-69/"

        indicatorView.smoothToShow()
        GetReadImgTask(link = currentLink,
                callback = object : GetReadImgTask.Callback {
                    @SuppressLint("SetTextI18n")
                    override fun onSuccess(imagesListOfOneChap: List<String>?) {
                        imagesListOfOneChap?.let {
                            LUIUtil.printBeautyJson(o = imagesListOfOneChap, textView = textView)
                            tvTitle.text = "Danh sách page trong chap 69 - size: " + imagesListOfOneChap.size
                            indicatorView.smoothToHide()
                        }
                    }

                    override fun onError() {
                        showShort("onError")
                        indicatorView.smoothToHide()
                    }
                }).execute()
    }

    override fun setFullScreen(): Boolean {
        return false
    }

}
