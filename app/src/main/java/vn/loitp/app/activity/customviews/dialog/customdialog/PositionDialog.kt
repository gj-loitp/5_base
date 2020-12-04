package vn.loitp.app.activity.customviews.dialog.customdialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.core.base.BaseActivity
import vn.loitp.app.R

class PositionDialog : DialogFragment() {
    private val logTag = javaClass.simpleName
    private var anchorView: View? = null

    enum class Position {
        DEFAULT,
        TOP_LEFT, TOP_CENTER, TOP_RIGHT,
        CENTER_LEFT, CENTER_CENTER, CENTER_RIGHT,
        BOTTOM_LEFT, BOTTOM_CENTER, BOTTOM_RIGHT
    }

    enum class Style {
        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT
    }

    private var position = Position.CENTER_CENTER

    //TODO RIGHT_TO_LEFT do not work
    private var style = Style.LEFT_TO_RIGHT

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(context, R.style.FullDialogTheme)
        val inflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_position, null)
        dialogBuilder.setView(dialogView)
        isCancelable = true
        init(dialogView)
        val alertDialog = dialogBuilder.create()
        alertDialog.window?.let { w ->
            w.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            anchorView?.let { anchorV ->

                when (style) {
                    Style.LEFT_TO_RIGHT -> {
                        var posX: Int? = null
                        var posY: Int? = null
                        when (position) {
                            Position.TOP_LEFT -> {
                                posX = anchorV.left
                                posY = anchorV.top
                            }
                            Position.TOP_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = anchorV.top
                            }
                            Position.TOP_RIGHT -> {
                                posX = anchorV.right
                                posY = anchorV.top
                            }
                            Position.CENTER_LEFT -> {
                                posX = anchorV.left
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.CENTER_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.CENTER_RIGHT -> {
                                posX = anchorV.right
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.BOTTOM_LEFT -> {
                                posX = anchorV.left
                                posY = anchorV.bottom
                            }
                            Position.BOTTOM_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = anchorV.bottom
                            }
                            Position.BOTTOM_RIGHT -> {
                                posX = anchorV.right
                                posY = anchorV.bottom
                            }
                            Position.DEFAULT -> {
                                //do nothing
                            }
                        }
                        posX?.let { x ->
                            posY?.let { y ->
                                w.attributes?.let { a ->
                                    a.gravity = Gravity.TOP or Gravity.START
                                    a.x = x
                                    a.y = y
                                    a.windowAnimations = R.style.FullDialogTheme_AnimLeftRight
                                }
                            }
                        }
                    }
                    Style.RIGHT_TO_LEFT -> {
                        var posX: Int? = null
                        var posY: Int? = null
                        when (position) {
                            Position.TOP_LEFT -> {
                                //TODO
                                posX = anchorV.left
                                posY = anchorV.top
                            }
                            Position.TOP_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = anchorV.top
                            }
                            Position.TOP_RIGHT -> {
                                posX = anchorV.right
                                posY = anchorV.top
                            }
                            Position.CENTER_LEFT -> {
                                posX = anchorV.left
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.CENTER_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.CENTER_RIGHT -> {
                                posX = anchorV.right
                                posY = (anchorV.top + anchorV.bottom) / 2
                            }
                            Position.BOTTOM_LEFT -> {
                                posX = anchorV.left
                                posY = anchorV.bottom
                            }
                            Position.BOTTOM_CENTER -> {
                                posX = (anchorV.left + anchorV.right) / 2
                                posY = anchorV.bottom
                            }
                            Position.BOTTOM_RIGHT -> {
                                posX = anchorV.right
                                posY = anchorV.bottom
                            }
                            Position.DEFAULT -> {
                                //do nothing
                            }
                        }
                        Log.d(logTag, ">>>>>>>>>> $posX $posY")
                        posX?.let { x ->
                            posY?.let { y ->
                                w.attributes?.let { a ->
                                    a.gravity = Gravity.TOP or Gravity.START
                                    a.x = x
                                    a.y = y
                                    a.windowAnimations = R.style.FullDialogTheme_AnimRightLeft
                                }
                            }
                        }
                    }
                }
            }
        }
        return alertDialog
    }

    private fun init(v: View) {
        val btOK = v.findViewById(R.id.btOK) as View
        val btCancel = v.findViewById(R.id.btCancel) as View
        btOK.setOnClickListener { dismiss() }
        btCancel.setOnClickListener { dismiss() }
    }

    fun showImmersivePos(activity: Activity, anchorView: View, sizeWidthPx: Int?, sizeHeightPx: Int?, position: Position) {
        this.position = position
        this.anchorView = anchorView
        if (activity is BaseActivity) {
            activity.supportFragmentManager.let { fm ->
                show(fm, logTag)
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
