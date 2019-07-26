package com.views.edittext.autosuggesttextview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.core.utilities.LUIUtil
import com.views.layout.relativepopupwindow.RelativePopupWindow
import loitp.core.R

class LAutoSuggestEditText : RelativeLayout {
    private val TAG = javaClass.simpleName

    lateinit var et: EditText
    lateinit var pb: ProgressBar

    private var resultList = ArrayList<String>()
    private var popup: SuggestPopupView? = null

    private var vertPos: Int = 0
    private var horizPos: Int = 0

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
                showProgress()
                hideSuggestPopup()
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

    fun clearResultList() {
        resultList.clear()
    }

    fun setResultList(resultList: ArrayList<String>) {
        this.resultList.clear()
        this.resultList.addAll(resultList)
        showSuggestPopup()
    }

    fun showSuggestPopup() {
        hideProgress()
        popup = SuggestPopupView(context, object : SuggestPopupView.Callback {
            override fun onClick(s: String) {
                et.setText(s)
            }
        })
        popup?.let {
            /*it.width = ViewGroup.LayoutParams.MATCH_PARENT
            it.height = ViewGroup.LayoutParams.WRAP_CONTENT*/
            it.width = this.width
            it.height = ViewGroup.LayoutParams.WRAP_CONTENT
            vertPos = RelativePopupWindow.VerticalPosition.BELOW
            horizPos = RelativePopupWindow.HorizontalPosition.CENTER

            it.setStringList(this.resultList)

            //LLog.d(TAG, "showSuggestPopup size: ${resultList.size} - $vertPos - $horizPos")
            it.showOnAnchor(this, vertPos, horizPos, true)
        }
    }

    fun hideSuggestPopup() {
        //LLog.d(TAG, "hideSuggestPopup")
        popup?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
    }
}