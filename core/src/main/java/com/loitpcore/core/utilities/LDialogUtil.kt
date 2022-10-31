package com.loitpcore.core.utilities

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.ViewPager
import com.daimajia.androidanimations.library.Techniques
import com.loitpcore.R
import com.loitpcore.views.dialog.slideImages.LSlideAdapter
import com.loitpcore.views.progressLoadingView.window.WP10ProgressBar

/**
 * Created by Loitp on 04,August,2022
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
class LDialogUtil {
    companion object {
        private val alertDialogList = ArrayList<AlertDialog>()

//        private fun logD(msg: String) {
//            LLog.d(logTag, msg)
//        }

        fun clearAll() {
//            logD("clearAll")
            try {
                for (i in alertDialogList.indices) {
                    alertDialogList[i].dismiss()
                }
                alertDialogList.clear()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun showDialog1(
            context: Context,
            title: String? = null,
            msg: String? = null,
            button1: String = LAppResource.getString(R.string.confirm),
            onClickButton1: ((Unit) -> Unit)? = null
        ): AlertDialog {
//            logD("showDialog1")
            clearAll()

            val builder = if (LUIUtil.isDarkTheme()) {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.DarkAlertDialogCustom))
            } else {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.LightAlertDialogCustom))
            }

            if (title.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setTitle(title)
            }
            if (msg.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setMessage(msg)
            }

            builder.setPositiveButton(button1) { _, _ ->
                onClickButton1?.invoke(Unit)
            }
            val dialog = builder.create()
            dialog.show()

            if (LUIUtil.isDarkTheme()) {
                val color = LAppResource.getColor(R.color.white)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
            } else {
                val color = LAppResource.getColor(R.color.colorPrimary)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
            }

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialog2(
            context: Context,
            title: String? = null,
            msg: String? = null,
            button1: String = context.getString(R.string.confirm),
            button2: String = context.getString(R.string.cancel),
            onClickButton1: ((Unit) -> Unit)? = null,
            onClickButton2: ((Unit) -> Unit)? = null
        ): AlertDialog {
            clearAll()
            val builder = if (LUIUtil.isDarkTheme()) {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.DarkAlertDialogCustom))
            } else {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.LightAlertDialogCustom))
            }
            if (title.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setTitle(title)
            }
            if (msg.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setMessage(msg)
            }
            builder.setNegativeButton(button1) { _, _ ->
                onClickButton1?.invoke(Unit)
            }
            builder.setPositiveButton(button2) { _, _ ->
                onClickButton2?.invoke(Unit)
            }
            val dialog = builder.create()
            dialog.show()
            if (LUIUtil.isDarkTheme()) {
                val colorPrimary = LAppResource.getColor(R.color.white)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPrimary)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)
            } else {
                val colorPrimary = LAppResource.getColor(R.color.colorPrimary)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(colorPrimary)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(colorPrimary)
            }

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialog3(
            context: Context,
            title: String? = null,
            msg: String? = null,
            button1: String? = null,
            button2: String? = null,
            button3: String? = null,
            onClickButton1: ((Unit) -> Unit)? = null,
            onClickButton2: ((Unit) -> Unit)? = null,
            onClickButton3: ((Unit) -> Unit)? = null
        ): AlertDialog {
            clearAll()
            val builder = if (LUIUtil.isDarkTheme()) {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.DarkAlertDialogCustom))
            } else {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.LightAlertDialogCustom))
            }
            if (title.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setTitle(title)
            }
            if (msg.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setMessage(msg)
            }
            if (button1.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setNegativeButton(button1) { _, _ ->
                    onClickButton1?.invoke(Unit)
                }
            }
            if (button2.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setPositiveButton(button2) { _, _ ->
                    onClickButton2?.invoke(Unit)
                }
            }
            if (button3.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setNeutralButton(button3) { _, _ ->
                    onClickButton3?.invoke(Unit)
                }
            }

            val dialog = builder.create()
            dialog.show()
            if (LUIUtil.isDarkTheme()) {
                val color = LAppResource.getColor(R.color.white)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
            } else {
                val color = LAppResource.getColor(R.color.colorPrimary)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(color)
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(color)
                dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(color)
            }

            alertDialogList.add(dialog)
            return dialog
        }

        fun showDialogList(
            context: Context,
            title: String? = null,
            arr: Array<String?>,
            onClick: ((Int) -> Unit)? = null
        ): AlertDialog {
            clearAll()
            val builder = if (LUIUtil.isDarkTheme()) {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.DarkAlertDialogCustom))
            } else {
                AlertDialog.Builder(ContextThemeWrapper(context, R.style.LightAlertDialogCustom))
            }
            if (title.isNullOrEmpty()) {
                // do nothing
            } else {
                builder.setTitle(title)
            }
            builder.setItems(arr) { _, which ->
                onClick?.invoke(which)
            }
            val dialog = builder.create()
            dialog.show()
            alertDialogList.add(dialog)
            return dialog
        }

        // style ex ProgressDialog.STYLE_HORIZONTAL
        @Suppress("DEPRECATION")
        fun showProgressDialog(
            context: Context,
            max: Int,
            title: String,
            msg: String,
            isCancelAble: Boolean,
            style: Int,
            buttonTitle: String?,
            onClickButton1: ((Unit) -> Unit)? = null
        ): ProgressDialog {
            clearAll()
            val progressDialog = ProgressDialog(context)
            progressDialog.max = max
            progressDialog.setMessage(msg)
            progressDialog.setCancelable(isCancelAble)
            progressDialog.setTitle(title)
            progressDialog.setProgressStyle(style)
            if (buttonTitle != null) {
                progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, buttonTitle) { _, _ ->
                    onClickButton1?.invoke(Unit)
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

        @SuppressLint("InflateParams")
        fun genCustomProgressDialog(
            context: Context?
        ): Dialog? {
            if (context == null || context !is Activity) {
                return null
            }
            val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.l_dlg_custom_progress)
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)

            val progressBar = dialog.findViewById<WP10ProgressBar>(R.id.progressBar)
            progressBar.showProgressBar()

            dialog.window?.let {
                it.setBackgroundDrawable(ColorDrawable(LAppResource.getColor(R.color.black65)))

                val wlp = it.attributes
                wlp.gravity = Gravity.CENTER

                // wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()

                it.attributes = wlp
                it.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
            }
            return dialog
        }

        fun showProgress(progressBar: ProgressBar?) {
            progressBar?.visibility = View.VISIBLE
        }

        fun hideProgress(progressBar: ProgressBar?) {
            progressBar?.visibility = View.GONE
        }

        fun showDialogSlide(
            context: Context,
            index: Int,
            imgList: List<String>,
            amount: Float,
            isShowController: Boolean,
            isShowIconClose: Boolean
        ): Dialog {
            val dialog = Dialog(context, android.R.style.Theme_Translucent_NoTitleBar)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.l_dlg_slide_images)
            dialog.setCanceledOnTouchOutside(true)
            val slideAdapter = LSlideAdapter(
                mContext = context,
                stringList = imgList,
                isShowIconClose = isShowIconClose,
                callback = object : LSlideAdapter.Callback {
                    override fun onClickClose() {
                        dialog.cancel()
                    }
                }
            )
            val viewPager = dialog.findViewById<ViewPager>(R.id.vp)
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
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
                val next = viewPager.currentItem + 1
                if (next < imgList.size) {
                    viewPager.currentItem = next
                }
            }
            ivPrev.setOnClickListener { view ->
                LAnimationUtil.play(view = view, techniques = Techniques.Pulse)
                val prev = viewPager.currentItem - 1
                if (prev >= 0) {
                    viewPager.currentItem = prev
                }
            }
            dialog.window?.let {
                // it.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                it.setBackgroundDrawable(ColorDrawable(LAppResource.getColor(R.color.black65)))
                it.setDimAmount(amount)

                val wlp = it.attributes
                wlp.gravity = Gravity.CENTER
                // wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
                wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
                it.attributes = wlp
                it.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT
                )
            }
            dialog.show()
            return dialog
        }
    }
}
