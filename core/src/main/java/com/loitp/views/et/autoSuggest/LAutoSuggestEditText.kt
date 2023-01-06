package com.loitp.views.et.autoSuggest

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.loitp.R
import com.loitp.core.ext.setImeiActionEditText
import com.loitp.core.ext.setLastCursorEditText
import com.loitp.core.utilities.LUIUtil

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LAutoSuggestEditText : RelativeLayout {
//    private val logTag = javaClass.simpleName

    lateinit var editText: EditText
    lateinit var progressBar: ProgressBar

    private var resultList = ArrayList<String>()
    private var popupSuggestPopupView: LSuggestPopupView? = null

    var vertPos: Int = RelativePopupWindow.VerticalPosition.BELOW
    var horizPos: Int = RelativePopupWindow.HorizontalPosition.CENTER
    var popupWidth: Int = 0
    var popupHeight: Int = 0

    var callback: Callback? = null

    interface Callback {
        fun onTextChanged(text: String)
    }

    constructor(
        context: Context,
        attrs: AttributeSet
    ) : super(context, attrs) {
        init()
    }

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyle: Int
    ) : super(
        context,
        attrs,
        defStyle
    ) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.l_v_auto_suggest_et, this)
        editText = findViewById(R.id.editText)
        progressBar = findViewById(R.id.progressBar)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    // hideSuggestPopup()
                    hideProgress()
                } else {
                    showProgress()
                }
                callback?.onTextChanged(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progressBar.visibility = View.INVISIBLE
    }

    fun setColorProgressBar(color: Int) {
        LUIUtil.setColorProgressBar(progressBar, color)
    }

    @Suppress("unused")
    fun setLastCursorEditText() {
        editText.setLastCursorEditText()
    }

    fun setHintText(hinText: String) {
        editText.hint = hinText
    }

    fun setHinTextColor(color: Int) {
        editText.setHintTextColor(color)
    }

    fun clearResultList() {
        resultList.clear()
    }

    fun setImeiAction(
        imeOptions: Int,
        runnable: Runnable?
    ) {
        editText.setImeiActionEditText(imeOptions = imeOptions, runnable = runnable)
    }

    fun setResultList(resultList: ArrayList<String>) {
        this.resultList.clear()
        this.resultList.addAll(resultList)
        showSuggestPopup()
    }

    @Suppress("unused")
    fun showSuggestPopup() {
        hideProgress()
        if (popupSuggestPopupView == null) {
            popupSuggestPopupView =
                LSuggestPopupView(
                    context, false,
                    object : LSuggestPopupView.Callback {
                        override fun onClick(s: String) {
                            hideSuggestPopup()
                            editText.setText(s)
                            editText.setLastCursorEditText()
                        }
                    }
                )
            popupSuggestPopupView?.let {
                /*it.width = ViewGroup.LayoutParams.MATCH_PARENT
                it.height = ViewGroup.LayoutParams.WRAP_CONTENT*/
                it.width = if (popupWidth == 0) this.width else popupWidth
                it.height = popupHeight
            }
        }
        popupSuggestPopupView?.setStringList(this.resultList)
    }

    fun hideSuggestPopup() {
        popupSuggestPopupView?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}
