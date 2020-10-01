package com.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import com.R
import com.daimajia.androidanimations.library.Techniques
import com.interfaces.Callback1
import com.interfaces.Callback2
import com.interfaces.Callback3
import com.interfaces.CallbackList
import com.views.dialog.iosdialog.iOSDialog
import com.views.dialog.slideimages.LSlideAdapter
import java.util.*

/**
 * Created by www.muathu@gmail.com on 12/29/2017.
 */

class LDialogUtil {
    companion object {
        private val alertDialogList = ArrayList<AlertDialog>()

        fun clearAll() {
            for (i in alertDialogList.indices) {
                alertDialogList[i].dismiss()
            }
        }

        fun showDialog1(context: Context, title: String?, msg: String, button1: String, callback1: Callback1?): AlertDialog {
            clearAll()
            val builder = AlertDialog.Builder(context)
            if (!title.isNullOrEmpty()) {
                builder.setTitle(title)
            }
            builder.setMessage(msg)
            builder.setPositiveButton(button1) { _, _ ->
                callback1?.onClick1()
            }
            val dialog = builder.create()
            dialog.show()
            val color = LAppResource.getColor(R.color.colorPrimary)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)

            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_POSITIVE))

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialog2(context: Context, title: String?, msg: String, button1: String?, button2: String?, callback2: Callback2?): AlertDialog {
            clearAll()
            val builder = AlertDialog.Builder(context)
            if (!title.isNullOrEmpty()) {
                builder.setTitle(title)
            }
            builder.setMessage(msg)
            if (!button1.isNullOrEmpty()) {
                builder.setNegativeButton(button1) { _, _ ->
                    callback2?.onClick1()
                }
            }
            if (!button2.isNullOrEmpty()) {
                builder.setPositiveButton(button2) { _, _ ->
                    callback2?.onClick2()
                }
            }
            val dialog = builder.create()
            dialog.show()
            val colorPrimary = LAppResource.getColor(R.color.colorPrimary)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPrimary)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)

            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_NEGATIVE))
            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_POSITIVE))

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialog3(context: Context, title: String?, msg: String, button1: String?, button2: String?,
                        button3: String?, callback3: Callback3?): AlertDialog {
            clearAll()
            val builder = AlertDialog.Builder(context)
            if (!title.isNullOrEmpty()) {
                builder.setTitle(title)
            }
            builder.setMessage(msg)
            if (!button1.isNullOrEmpty()) {
                builder.setNegativeButton(button1) { _, _ ->
                    callback3?.onClick1()
                }
            }
            if (!button2.isNullOrEmpty()) {
                builder.setPositiveButton(button2) { _, _ ->
                    callback3?.onClick2()
                }
            }
            if (!button3.isNullOrEmpty()) {
                builder.setNeutralButton(button3) { _, _ ->
                    callback3?.onClick3()
                }
            }
            val dialog = builder.create()
            dialog.show()
            val color = LAppResource.getColor(R.color.colorPrimary)
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
            dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)

            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_NEGATIVE))
            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_POSITIVE))
            LUIUtil.setRipple(context, dialog.getButton(AlertDialog.BUTTON_NEUTRAL))

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialogList(context: Context, title: String?, arr: Array<String?>, callbackList: CallbackList?): AlertDialog {
            clearAll()
            val builder = AlertDialog.Builder(context)
            if (!title.isNullOrEmpty()) {
                builder.setTitle(title)
            }
            builder.setItems(arr) { _, which ->
                callbackList?.onClick(which)
            }
            val dialog = builder.create()
            dialog.show()
            alertDialogList.add(dialog)
            return dialog
        }

        //style ex ProgressDialog.STYLE_HORIZONTAL
        fun showProgressDialog(context: Context, max: Int, title: String, msg: String, isCancelAble: Boolean,
                               style: Int, buttonTitle: String?, callback1: Callback1?): ProgressDialog {
            clearAll()
            val progressDialog = ProgressDialog(context)
            progressDialog.max = max
            progressDialog.setMessage(msg)
            progressDialog.setCancelable(isCancelAble)
            progressDialog.setTitle(title)
            progressDialog.setProgressStyle(style)
            if (buttonTitle != null) {
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, buttonTitle) { _, _ ->
                    callback1?.onClick1()
                }
            }
            progressDialog.show()
            alertDialogList.add(progressDialog)
            return progressDialog
        }

        fun show(dialog: Dialog?) {
            if (dialog != null && !dialog.isShowing) {
                dialog.show()
            }
        }

        fun hide(dialog: Dialog?) {
            if (dialog != null && dialog.isShowing) {
                dialog.cancel()
            }
        }

        fun showIOSDialog1(activity: Activity, title: String, subtitle: String, label1: String,
                           isBold: Boolean, callback1: Callback1?) {
            val iOSDialog = iOSDialog(activity)
            iOSDialog.apply {
                setTitle(title)
                setSubtitle(subtitle)
                setPositiveLabel(label1)
                setBoldPositiveLabel(isBold)
                setPositiveListener(View.OnClickListener {
                    dismiss()
                    callback1?.onClick1()
                })
                show()
            }
        }

        fun showIOSDialog2(activity: Activity, title: String, subtitle: String, label1: String,
                           label2: String, isBold: Boolean, callback2: Callback2?) {
            val iOSDialog = iOSDialog(activity)
            iOSDialog.apply {
                setTitle(title)
                setSubtitle(subtitle)
                setNegativeLabel(label1)
                setPositiveLabel(label2)
                setBoldPositiveLabel(isBold)
                setNegativeListener(View.OnClickListener {
                    dismiss()
                    callback2?.onClick1()
                })
                setPositiveListener(View.OnClickListener {
                    dismiss()
                    callback2?.onClick2()
                })
                show()
            }
        }

        @SuppressLint("InflateParams")
        @JvmOverloads
        fun showCustomProgressDialog(context: Context?, amount: Float = 0f): AlertDialog? {
            if (context == null) {
                return null
            }
            clearAll()
            val builder = AlertDialog.Builder(context)
            val inflater = (context as Activity).layoutInflater
            val view = inflater.inflate(R.layout.l_dlg_custom_progress, null)
            builder.setView(view)
            val dialog = builder.create()
            dialog.window?.let {
                it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                it.setDimAmount(amount)
            }
            dialog.setCancelable(false)
            dialog.show()
            alertDialogList.add(dialog)
            return dialog
        }

        fun showProgress(progressBar: ProgressBar?) {
            progressBar?.visibility = View.VISIBLE
        }

        fun hideProgress(progressBar: ProgressBar?) {
            progressBar?.visibility = View.GONE
        }

        fun showDialogSlide(context: Context, index: Int, imgList: List<String>, amount: Float,
                            isShowController: Boolean,
                            isShowIconClose: Boolean
        ): Dialog {
            val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.l_dlg_slide_images)
            dialog.setCanceledOnTouchOutside(true)
            val slideAdapter = LSlideAdapter(mContext = context, stringList = imgList, isShowIconClose = isShowIconClose,
                    callback = object : LSlideAdapter.Callback {
                        override fun onClickClose() {
                            dialog.cancel()
                        }
                    })
            val viewPager = dialog.findViewById<View>(R.id.vp) as ViewPager
            viewPager.adapter = slideAdapter
            if (index != 0) {
                viewPager.currentItem = index
            }
            LUIUtil.setPullLikeIOSHorizontal(viewPager)
            val ivNext = dialog.findViewById<ImageView>(R.id.ivNext)
            val ivPrev = dialog.findViewById<ImageView>(R.id.ivPrev)
            if (isShowController) {
                ivNext.visibility = View.VISIBLE
                ivPrev.visibility = View.VISIBLE
            } else {
                ivNext.visibility = View.INVISIBLE
                ivPrev.visibility = View.INVISIBLE
            }
            ivNext.setOnClickListener { view ->
                LAnimationUtil.play(view, Techniques.Pulse)
                val next = viewPager.currentItem + 1
                if (next < imgList.size) {
                    viewPager.currentItem = next
                }
            }
            ivPrev.setOnClickListener { view ->
                LAnimationUtil.play(view, Techniques.Pulse)
                val prev = viewPager.currentItem - 1
                if (prev >= 0) {
                    viewPager.currentItem = prev
                }
            }
            dialog.window?.let {
                //it.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                it.setBackgroundDrawable(ColorDrawable(LAppResource.getColor(R.color.black65)))
                it.setDimAmount(amount)

                val wlp = it.attributes
                wlp.gravity = Gravity.CENTER
                //wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                it.attributes = wlp
                it.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
            }
            dialog.show()
            return dialog
        }
    }
}
