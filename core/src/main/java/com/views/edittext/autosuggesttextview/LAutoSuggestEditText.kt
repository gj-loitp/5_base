package com.views.edittext.autosuggesttextview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.core.utilities.LUIUtil
import loitp.core.R

class LAutoSuggestEditText : RelativeLayout {
    private val TAG = javaClass.simpleName

    lateinit var et: EditText
    lateinit var pb: ProgressBar

    var callback: Callback? = null

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

    interface Callback {
        fun onTextChanged(text: String)
    }
}