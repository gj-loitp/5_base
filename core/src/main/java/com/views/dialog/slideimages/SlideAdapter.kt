package com.views.dialog.slideimages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import loitp.core.R

class SlideAdapter(private val mContext: Context, private val stringList: List<String>?,
                   private val isShowIconClose: Boolean,
                   private val callback: Callback?) : PagerAdapter() {
    private val screenW: Int = LScreenUtil.screenWidth

    interface Callback {
        fun onClickClose()
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.frm_image_slide, collection, false) as ViewGroup
        val iv = layout.findViewById<ImageView>(R.id.iv)
        val ivClose = layout.findViewById<ImageView>(R.id.ivClose)
        ivClose.visibility = if (isShowIconClose) View.VISIBLE else View.INVISIBLE
        //int sizeW = screenW * 3 / 5;
        val sizeW = screenW
        iv.layoutParams.width = sizeW
        iv.requestLayout()
        val url = stringList?.get(position)
        if (!url.isNullOrEmpty()) {
            LImageUtil.load(mContext, url, iv, screenW, screenW * 9 / 16)
        }
        ivClose.setOnClickListener {
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

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }
}