package com.loitp.views.dlg.slideImages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.loitp.R
import com.loitp.core.ext.loadGlide
import kotlinx.android.synthetic.main.l_f_image_slide.view.*

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LSlideAdapter(
    private val mContext: Context,
    private val stringList: List<String>?,
    private val isShowIconClose: Boolean,
    private val callback: Callback?
) : PagerAdapter() {
//    private val screenW: Int = LScreenUtil.screenWidth

    interface Callback {
        fun onClickClose()
    }

    override fun instantiateItem(
        collection: ViewGroup,
        position: Int
    ): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.l_f_image_slide, collection, false) as ViewGroup

        layout.ivClose.visibility = if (isShowIconClose) View.VISIBLE else View.INVISIBLE
//        val sizeW = screenW
//        imageView.layoutParams.width = sizeW
//        imageView.requestLayout()
        val url = stringList?.get(position)
//        LImageUtil.load(context = mContext, any = url, imageView = iv, resPlaceHolder = screenW, resError = screenW * 9 / 16)
        layout.imageView.loadGlide(any = url)

        layout.ivClose.setOnClickListener {
            callback?.onClickClose()
        }
        layout.setOnClickListener {
            callback?.onClickClose()
        }
        collection.addView(layout)
        return layout
    }

    override fun getCount(): Int {
        return stringList?.size ?: 0
    }

    override fun isViewFromObject(
        arg0: View,
        arg1: Any
    ): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(
        container: ViewGroup,
        position: Int,
        `object`: Any
    ) {
        // super.destroyItem(container, position, object);
    }
}
