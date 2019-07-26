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
import com.core.utilities.LLog
import com.core.utilities.LUIUtil
import com.views.layout.relativepopupwindow.RelativePopupWindow
import loitp.core.R

class LAutoSuggestEditText : RelativeLayout {
    private val TAG = javaClass.simpleName

    lateinit var et: EditText
    lateinit var pb: ProgressBar

    private var resultList = ArrayList<String>()
    val popup = SuggestPopupView(context)

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
        resultList.clear()
        resultList.addAll(resultList)
        showSuggestPopup()
    }

    fun showSuggestPopup() {
        LLog.d(TAG, "showSuggestPopup size: " + resultList.size)
        /*popup.width = ViewGroup.LayoutParams.MATCH_PARENT
        popup.height = ViewGroup.LayoutParams.WRAP_CONTENT*/

        popup.width = this.width
        popup.height = ViewGroup.LayoutParams.WRAP_CONTENT

        val vertPos = RelativePopupWindow.VerticalPosition.BELOW
        val horizPos = RelativePopupWindow.HorizontalPosition.CENTER
        popup.showOnAnchor(this, vertPos, horizPos, true)
    }
}