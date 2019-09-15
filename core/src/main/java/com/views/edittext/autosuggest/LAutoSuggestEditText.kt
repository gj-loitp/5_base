package com.views.edittext.autosuggest

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.R
import com.core.utilities.LUIUtil
import com.views.layout.relativepopupwindow.LRelativePopupWindow

class LAutoSuggestEditText : RelativeLayout {
    private val TAG = javaClass.simpleName

    lateinit var et: EditText
    lateinit var pb: ProgressBar

    private var resultList = ArrayList<String>()
    private var popupSuggestPopupView: LSuggestPopupView? = null

    var vertPos: Int = LRelativePopupWindow.VerticalPosition.BELOW
    var horizPos: Int = LRelativePopupWindow.HorizontalPosition.CENTER
    var popupWidth: Int = 0
    var popupHeight: Int = 0

    var callback: Callback? = null

    interface Callback {
        fun onTextChanged(text: String)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_auto_suggest_edittext, this)
        et = findViewById(R.id.et)
        pb = findViewById(R.id.pb)

        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    //hideSuggestPopup()
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
        pb.visibility = View.VISIBLE
    }

    fun hideProgress() {
        pb.visibility = View.INVISIBLE
    }

    fun setColorProgressBar(color: Int) {
        LUIUtil.setColorProgressBar(pb, color)
    }

    fun setLastCursorEditText() {
        LUIUtil.setLastCursorEditText(et)
    }

    fun setHintText(hinText: String) {
        et.hint = hinText
    }

    fun setHinTextColor(color: Int) {
        et.setHintTextColor(color)
    }

    fun clearResultList() {
        resultList.clear()
    }

    fun setImeiAction(imeOptions: Int, runnable: Runnable?) {
        LUIUtil.setImeiActionEditText(et, imeOptions, runnable)
    }

    fun setResultList(resultList: ArrayList<String>) {
        this.resultList.clear()
        this.resultList.addAll(resultList)
        showSuggestPopup()
    }

    fun showSuggestPopup() {
        hideProgress()
        if (popupSuggestPopupView == null) {
            popupSuggestPopupView = LSuggestPopupView(context, false, object : LSuggestPopupView.Callback {
                override fun onClick(s: String) {
                    hideSuggestPopup()
                    et.setText(s)
                    LUIUtil.setLastCursorEditText(et)
                }
            })
            popupSuggestPopupView?.let {
                /*it.width = ViewGroup.LayoutParams.MATCH_PARENT
                it.height = ViewGroup.LayoutParams.WRAP_CONTENT*/
                it.width = if (popupWidth == 0) this.width else popupWidth
                it.height = popupHeight
            }
        }
        popupSuggestPopupView?.let {
            it.setStringList(this.resultList)
            //LLog.d(TAG, "showSuggestPopup size: ${resultList.size} - $vertPos - $horizPos")
            if (!it.isShowing) {
                //LLog.d(TAG, "showSuggestPopup")
                //it.showOnAnchor(this, vertPos, horizPos, true)
            }
        }
    }

    fun hideSuggestPopup() {
        popupSuggestPopupView?.let {
            if (it.isShowing) {
                //LLog.d(TAG, "hideSuggestPopup")
                it.dismiss()
            }
        }
    }
}