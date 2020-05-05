package vn.loitp.app.activity.customviews.dialog.customdialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.core.base.BaseActivity
import com.core.utilities.LLog
import vn.loitp.app.R

class PositionDialog : DialogFragment() {
    private val TAG = javaClass.simpleName
    private var anchorView: View? = null

    enum class Position {
        DEFAULT,
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER_CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    private var position = Position.CENTER_CENTER

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //LLog.d(TAG, "onCreateDialog")
        val dialogBuilder = AlertDialog.Builder(context, R.style.FullDialogTheme)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_position, null)
        dialogBuilder.setView(dialogView)
        isCancelable = true
        init(dialogView)
        val d = dialogBuilder.create()
        d.window?.let { w ->
            w.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            anchorView?.let { av ->
                var posX: Int? = null
                var posY: Int? = null
                when (position) {
                    Position.TOP_LEFT -> {
                        posX = av.left
                        posY = av.top
                    }
                    Position.TOP_CENTER -> {
                        posX = (av.left + av.right) / 2
                        posY = av.top
                    }
                    Position.TOP_RIGHT -> {
                        posX = av.right
                        posY = av.top
                    }
                    Position.CENTER_LEFT -> {
                        posX = av.left
                        posY = (av.top + av.bottom) / 2
                    }
                    Position.CENTER_CENTER -> {
                        posX = (av.left + av.right) / 2
                        posY = (av.top + av.bottom) / 2
                    }
                    Position.CENTER_RIGHT -> {
                        posX = av.right
                        posY = (av.top + av.bottom) / 2
                    }
                    Position.BOTTOM_LEFT -> {
                        posX = av.left
                        posY = av.bottom
                    }
                    Position.BOTTOM_CENTER -> {
                        posX = (av.left + av.right) / 2
                        posY = av.bottom
                    }
                    Position.BOTTOM_RIGHT -> {
                        posX = av.right
                        posY = av.bottom
                    }
                    Position.DEFAULT -> {
                        //do nothing
                    }
                }
                LLog.d(TAG, "posX: $posX, posY: $posY")
                if (posX != null && posY != null) {
                    w.attributes?.let { a ->
                        a.gravity = Gravity.TOP or Gravity.START
                        a.x = posX
                        a.y = posY
                        a.windowAnimations = R.style.FullDialogTheme_Anim
                    }
                }
            }
        }
        return d
    }

    private fun init(v: View) {
        val btOK = v.findViewById(R.id.btOK) as View
        val btCancel = v.findViewById(R.id.btCancel) as View
        btOK.setOnClickListener { dismiss() }
        btCancel.setOnClickListener { dismiss() }
    }

    /*fun showImmersiveCenter(activity: Activity, sizeWidthPx: Int?, sizeHeightPx: Int?) {
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                fm.executePendingTransactions()
                dialog?.window?.let { w ->
                    w.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility
                    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                    if (sizeWidthPx != null && sizeHeightPx != null) {
                        w.setLayout(sizeWidthPx, sizeHeightPx)
                    }
                }
            }
        }
    }*/

    fun showImmersivePos(activity: Activity, anchorView: View, sizeWidthPx: Int?, sizeHeightPx: Int?, position: Position) {
        this.position = position
        this.anchorView = anchorView
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, TAG)
                fm.executePendingTransactions()
                dialog?.window?.let { w ->
                    w.decorView.systemUiVisibility = activity.window.decorView.systemUiVisibility
                    w.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                    if (sizeWidthPx != null && sizeHeightPx != null) {
                        w.setLayout(sizeWidthPx, sizeHeightPx)
                    }
                }
            }
        }
    }
}