package vn.loitp.app.activity.customviews.edittext.autosuggest

import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.LToast
import com.views.edittext.autosuggest.LAutoSuggestEditText
import com.views.layout.relativepopupwindow.LRelativePopupWindow
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editext_auto_suggest.*
import vn.loitp.app.R

class EditTextAutoSuggestActivity : BaseFontActivity() {
    private var disposableSearch: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aet0.popupHeight = LScreenUtil.screenHeight / 2
        aet0.vertPos = LRelativePopupWindow.VerticalPosition.BELOW
        aet0.horizPos = LRelativePopupWindow.HorizontalPosition.CENTER
        aet0.setHintText("1/2 screen")
        aet0.setHinTextColor(Color.BLUE)
        aet0.setColorProgressBar(Color.RED)
        aet0.setBackgroundResource(R.drawable.bkg_et)
        aet0.setImeiAction(EditorInfo.IME_ACTION_SEARCH, Runnable {
            LToast.show(activity, "Text ${aet0.editText.text}")
        })
        aet0.callback = object : LAutoSuggestEditText.Callback {
            override fun onTextChanged(text: String) {
                fakeCallAPI0(text)
            }
        }

        aet1.popupWidth = LScreenUtil.screenWidth * 1 / 2
        aet1.popupHeight = LScreenUtil.screenHeight * 1 / 4
        aet1.vertPos = LRelativePopupWindow.VerticalPosition.ALIGN_BOTTOM
        aet1.horizPos = LRelativePopupWindow.HorizontalPosition.RIGHT
        aet1.setHintText("3/4 screen")
        aet1.setHinTextColor(Color.WHITE)
        aet1.editText.setTextColor(Color.WHITE)
        aet1.setColorProgressBar(Color.WHITE)
        aet1.setBackgroundResource(R.drawable.l_bkg_horizontal)
        aet1.setImeiAction(EditorInfo.IME_ACTION_DONE, Runnable {
            LToast.show(activity, "Text ${aet1.editText.text}")
        })
        aet1.callback = object : LAutoSuggestEditText.Callback {
            override fun onTextChanged(text: String) {
                fakeCallAPI1(text)
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_auto_suggest
    }

    private fun fakeCallAPI0(text: String) {
        disposableSearch?.dispose()
        if (text.isEmpty()) {
            return
        }
        disposableSearch = Single.create<ArrayList<String>> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
            }
            val stringList = ArrayList<String>()
            for (i in 0..10) {
                stringList.add("Result $text $i")
            }
            it.onSuccess(stringList)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose {
                    logD("doOnDispose")
                }
                .subscribe(
                        {
                            aet0.setResultList(it)
                        },
                        {
                            aet0.clearResultList()
                        }
                )
    }

    private fun fakeCallAPI1(text: String) {
        disposableSearch?.dispose()
        if (text.isEmpty()) {
            return
        }
        disposableSearch = Single.create<ArrayList<String>> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
            }
            val stringList = ArrayList<String>()
            for (i in 0..50) {
                stringList.add("Result $text $i")
            }
            it.onSuccess(stringList)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose {
                    logD("doOnDispose")
                }
                .subscribe(
                        {
                            aet1.setResultList(it)
                        },
                        {
                            aet1.clearResultList()
                        }
                )
    }
}
