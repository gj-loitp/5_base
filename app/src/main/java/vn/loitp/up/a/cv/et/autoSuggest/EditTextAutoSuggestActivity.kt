package vn.loitp.up.a.cv.et.autoSuggest

import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.screenHeight
import com.loitp.core.ext.screenWidth
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.et.autoSuggest.LAutoSuggestEditText
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import vn.loitp.R
import vn.loitp.databinding.AEtAutoSuggestBinding

@LogTag("EditTextAutoSuggestActivity")
@IsFullScreen(false)
class EditTextAutoSuggestActivity : BaseActivityFont() {
    private var disposableSearch: Disposable? = null
    private lateinit var binding: AEtAutoSuggestBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AEtAutoSuggestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft?.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = EditTextAutoSuggestActivity::class.java.simpleName
        }
        binding.aet0.apply {
            this.popupHeight = screenHeight / 2
            this.vertPos = RelativePopupWindow.VerticalPosition.BELOW
            this.horizPos = RelativePopupWindow.HorizontalPosition.CENTER
            this.setHintText("1/2 screen")
            this.setHinTextColor(Color.BLUE)
            this.editText.setTextColor(Color.YELLOW)
            this.setColorProgressBar(Color.RED)
            this.setBackgroundResource(R.drawable.bg_et)
            this.setImeiAction(
                imeOptions = EditorInfo.IME_ACTION_SEARCH,
                runnable = {
                    showShortInformation("Text ${binding.aet0.editText.text}")
                }
            )
            this.callback = object : LAutoSuggestEditText.Callback {
                override fun onTextChanged(text: String) {
                    fakeCallAPI0(text)
                }
            }
        }

        binding.aet1.apply {
            this.popupWidth = screenWidth * 1 / 2
            this.popupHeight = screenHeight * 1 / 4
            this.vertPos = RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM
            this.horizPos = RelativePopupWindow.HorizontalPosition.RIGHT
            this.setHintText("3/4 screen")
            this.setHinTextColor(Color.RED)
            this.editText.setTextColor(Color.BLUE)
            this.setColorProgressBar(Color.GREEN)
            this.setBackgroundResource(com.loitp.R.drawable.l_bkg_horizontal)
            this.setImeiAction(EditorInfo.IME_ACTION_DONE) {
                showShortInformation("Text ${binding.aet1.editText.text}")
            }
            this.callback = object : LAutoSuggestEditText.Callback {
                override fun onTextChanged(text: String) {
                    fakeCallAPI1(text)
                }
            }
        }
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
                e.printStackTrace()
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
                    binding.aet0.setResultList(it)
                },
                {
                    binding.aet0.clearResultList()
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
                e.printStackTrace()
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
                    binding.aet1.setResultList(it)
                },
                {
                    binding.aet1.clearResultList()
                }
            )
    }
}
