package vn.loitp.app.activity.customviews.edittext.autosuggest

import android.graphics.Color
import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LLog
import com.views.edittext.autosuggesttextview.LAutoSuggestEditText
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editext_auto_suggest.*
import loitp.basemaster.R
import vn.loitp.app.app.LApplication

class EditTextAutoSuggestActivity : BaseFontActivity() {
    private var disposableSearch: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        aet0.setHintText("1/2 screen")
        aet0.setHinTextColor(Color.BLUE)
        aet0.setColorProgressBar(Color.RED)
        aet0.setBackgroundResource(R.drawable.bkg_et)
        aet0.callback = object : LAutoSuggestEditText.Callback {
            override fun onTextChanged(text: String) {
                fakeCallAPI0(text)
            }
        }

        aet1.setHintText("1/2 screen")
        aet1.setHinTextColor(Color.RED)
        aet1.setColorProgressBar(Color.BLUE)
        aet1.setBackgroundResource(R.drawable.bkg_horizontal)
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
        LLog.d(TAG, "fakeCallAPI0 $text")
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
                    LLog.d(TAG, "doOnDispose")
                }
                .subscribe(
                        {
                            LLog.d(TAG, "fakeCallAPI0 " + LApplication.gson.toJson(it))
                            aet0.setResultList(it)
                        },
                        {
                            LLog.e(TAG, "fakeCallAPI0 $it")
                            aet0.clearResultList()
                        }
                )
    }

    private fun fakeCallAPI1(text: String) {
        LLog.d(TAG, "fakeCallAPI0 $text")
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
                    LLog.d(TAG, "doOnDispose")
                }
                .subscribe(
                        {
                            LLog.d(TAG, "fakeCallAPI0 " + LApplication.gson.toJson(it))
                            aet1.setResultList(it)
                        },
                        {
                            LLog.e(TAG, "fakeCallAPI0 $it")
                            aet1.clearResultList()
                        }
                )
    }
}
