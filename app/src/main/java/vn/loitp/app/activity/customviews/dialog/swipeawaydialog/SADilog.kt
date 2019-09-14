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
import com.core.utilities.LLog
import com.views.LToast
import com.views.dialog.swipeawaydialog.support.SwipeAwayDialogFragment
import loitp.basemaster.R

class SADilog : SwipeAwayDialogFragment() {
    private val TAG = javaClass.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments
        var key = Constants.NOT_FOUND
        if (bundle != null) {
            key = bundle.getInt(KEY)
        }
        LLog.d(TAG, "key $key")
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
        return LDialogUtil.showDialog1(context!!, "Title", "Msg", "Button 1",
                object : LDialogUtil.Callback1 {
                    override fun onClick1() {
                        LToast.show(context!!, "Click 1", R.drawable.l_bkg_horizontal)
                    }
                })
    }

    private fun show2(): AlertDialog {
        return LDialogUtil.showDialog2(context!!, "Title", "Msg", "Button 1", "Button 2", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                LToast.showShort(context!!, "Click 1", R.drawable.l_bkg_horizontal)
            }

            override fun onClick2() {
                LToast.showShort(context!!, "Click 2", R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun show3(): AlertDialog {
        return LDialogUtil.showDialog3(context!!, "Title", "Msg", "Button 1", "Button 2", "Button 3", object : LDialogUtil.Callback3 {
            override fun onClick1() {
                LToast.showShort(context!!, "Click 1", R.drawable.l_bkg_horizontal)
            }

            override fun onClick2() {
                LToast.showShort(context!!, "Click 2", R.drawable.l_bkg_horizontal)
            }

            override fun onClick3() {
                LToast.showShort(context!!, "Click 3", R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun showList(): AlertDialog {
        val size = 50
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Item $i"
        }
        return LDialogUtil.showDialogList(context!!, "Title", arr, object : LDialogUtil.CallbackList {
            override fun onClick(position: Int) {
                LToast.show(context!!, "Click position " + position + ", item: " + arr[position], R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun showProgress(): AlertDialog {
        val progressDialog = LDialogUtil.showProgressDialog(activity!!, 100, "Title", "Message", false, ProgressDialog.STYLE_HORIZONTAL, null, null)
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

            protected override fun onProgressUpdate(vararg values: Int?) {
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
        val inflater = activity!!.layoutInflater
        val view = inflater.inflate(R.layout.dlg_swipe_away_custom, null)

        val btY = view.findViewById<Button>(R.id.bt_y)
        btY.setOnClickListener { LToast.show(activity!!, "Click yes") }

        val btN = view.findViewById<Button>(R.id.bt_n)
        btN.setOnClickListener { LToast.show(activity!!, "Click no") }

        builder.setView(view)
        val dialog = builder.create()
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    companion object {
        val KEY: String? = null
        const val KEY_1 = 1
        const val KEY_2 = 2
        const val KEY_3 = 3
        const val KEY_4 = 4
        const val KEY_5 = 5
        const val KEY_6 = 6
    }
}
