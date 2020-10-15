package com.core.helper.mup.girl.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.R

/**
 * Created by www.muathu@gmail.com on 5/13/2017.
 */

class ViewGirlTopUser : LinearLayout {
    private val logTag = javaClass.simpleName

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        View.inflate(context, R.layout.view_girl_top_user, this)
    }

}
