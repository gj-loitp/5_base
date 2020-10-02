package vn.loitp.app.activity.customviews.dialog.swipeawaydialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import com.core.common.Constants
import com.core.utilities.LDialogUtil
import com.interfaces.Callback1
import com.interfaces.Callback2
import com.interfaces.Callback3
import com.interfaces.CallbackList
import com.views.LToast
import com.views.dialog.swipeawaydialog.support.SwipeAwayDialogFragment
import vn.loitp.app.R

class SADialog : SwipeAwayDialogFragment() {
    private val logTag = javaClass.simpleName

    companion object {
        val KEY: String? = null
        const val KEY_1 = 1
        const val KEY_2 = 2
        const val KEY_3 = 3
        const val KEY_4 = 4
        const val KEY_5 = 5
        const val KEY_6 = 6
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        var key = Constants.NOT_FOUND
        if (bundle != null) {
            key = bundle.getInt(KEY)
        }
        return when (key) {
            KEY_1 -> show1()
            KEY_2 -> show2()
            KEY_3 -> show3()
            KEY_4 -> showList()
            KEY_5 -> showProgress()
            KEY_6 -> showCustom()
            else -> show1()
        }
    }

    private fun show1(): AlertDialog {
        return LDialogUtil.showDialog1(context = requireContext(),
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                callback1 = object : Callback1 {
                    override fun onClick1() {
                        LToast.show(R.drawable.l_bkg_toast)
                    }
                })
    }

    private fun show2(): AlertDialog {
        return LDialogUtil.showDialog2(context = requireContext(),
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                button2 = "Button 2",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        LToast.showShort("Click 1", R.drawable.l_bkg_toast)
                    }

                    override fun onClick2() {
                        LToast.showShort("Click 2", R.drawable.l_bkg_toast)
                    }
                })
    }

    private fun show3(): AlertDialog {
        return LDialogUtil.showDialog3(context = requireContext(),
                title = "Title",
                msg = "Msg",
                button1 = "Button 1",
                button2 = "Button 2",
                button3 = "Button 3",
                callback3 = object : Callback3 {
                    override fun onClick1() {
                        LToast.showShort("Click 1", R.drawable.l_bkg_toast)
                    }

                    override fun onClick2() {
                        LToast.showShort("Click 2", R.drawable.l_bkg_toast)
                    }

                    override fun onClick3() {
                        LToast.showShort("Click 3", R.drawable.l_bkg_toast)
                    }
                })
    }

    private fun showList(): AlertDialog {
        val size = 50
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Item $i"
        }
        return LDialogUtil.showDialogList(context = requireContext(),
                title = "Title",
                arr = arr,
                callbackList = object : CallbackList {
                    override fun onClick(position: Int) {
                        LToast.show("Click position " + position + ", item: " + arr[position], R.drawable.l_bkg_toast)
                    }
                })
    }

    private fun showProgress(): AlertDialog {
        val progressDialog = LDialogUtil.showProgressDialog(context = requireContext(),
                max = 100,
                title = "Title",
                msg = "Message",
                isCancelAble = false,
                style = ProgressDialog.STYLE_HORIZONTAL,
                buttonTitle = null,
                callback1 = null)
        //TODO convert asynctask to rx
        object : AsyncTask<Void, Int, Void>() {
            var i = 0

            override fun doInBackground(vararg voids: Void): Void? {
                i = 0
                while (i < progressDialog.max) {
                    publishProgress(i)
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                    i++
                }
                return null
            }

            override fun onProgressUpdate(vararg values: Int?) {
                super.onProgressUpdate(*values)
                values[0]?.let {
                    progressDialog.progress = it
                }

            }

            override fun onPostExecute(aVoid: Void) {
                progressDialog.dismiss()
                super.onPostExecute(aVoid)
            }
        }.execute()
        return progressDialog
    }

    @SuppressLint("InflateParams")
    private fun showCustom(): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dlg_swipe_away_custom, null)

        val btY = view.findViewById<Button>(R.id.btYes)
        btY.setOnClickListener {
            LToast.show("Click yes")
        }

        val btN = view.findViewById<Button>(R.id.btNo)
        btN.setOnClickListener {
            LToast.show("Click no")
        }

        builder.setView(view)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}
