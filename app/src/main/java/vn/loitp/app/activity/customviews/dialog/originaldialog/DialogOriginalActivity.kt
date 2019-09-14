package vn.loitp.app.activity.customviews.dialog.originaldialog

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import com.core.base.BaseFontActivity
import com.core.utilities.LDialogUtil
import com.views.LToast
import kotlinx.android.synthetic.main.activity_dialog_original.*


class DialogOriginalActivity : BaseFontActivity(), OnClickListener {
    private var testRun: TestRun? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(loitp.basemaster.R.id.bt_show_1).setOnClickListener(this)
        findViewById<View>(loitp.basemaster.R.id.bt_show_2).setOnClickListener(this)
        findViewById<View>(loitp.basemaster.R.id.bt_show_3).setOnClickListener(this)
        findViewById<View>(loitp.basemaster.R.id.bt_show_list).setOnClickListener(this)
        findViewById<View>(loitp.basemaster.R.id.bt_progress_dialog).setOnClickListener(this)
        btInputDialog.setOnClickListener { showInputDialog() }
    }

    override fun onDestroy() {
        super.onDestroy()
        testRun?.cancel(true)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return loitp.basemaster.R.layout.activity_dialog_original
    }

    override fun onClick(v: View) {
        when (v.id) {
            loitp.basemaster.R.id.bt_show_1 -> show1()
            loitp.basemaster.R.id.bt_show_2 -> show2()
            loitp.basemaster.R.id.bt_show_3 -> show3()
            loitp.basemaster.R.id.bt_show_list -> showList()
            loitp.basemaster.R.id.bt_progress_dialog -> showProgress()
        }
    }

    private fun show1() {
        LDialogUtil.showDialog1(activity, "Title", "Msg", "Button 1"
                , object : LDialogUtil.Callback1 {
            override fun onClick1() {
                LToast.show(activity, "Click 1", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun show2() {
        LDialogUtil.showDialog2(activity, "Title", "Msg", "Button 1", "Button 2", object : LDialogUtil.Callback2 {
            override fun onClick1() {
                LToast.showShort(activity, "Click 1", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }

            override fun onClick2() {
                LToast.showShort(activity, "Click 2", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun show3() {
        LDialogUtil.showDialog3(activity, "Title", "Msg", "Button 1", "Button 2", "Button 3", object : LDialogUtil.Callback3 {
            override fun onClick1() {
                LToast.showShort(activity, "Click 1", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }

            override fun onClick2() {
                LToast.showShort(activity, "Click 2", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }

            override fun onClick3() {
                LToast.showShort(activity, "Click 3", loitp.basemaster.R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun showList() {
        val size = 50
        val arr = arrayOfNulls<String?>(size)
        for (i in 0 until size) {
            arr[i] = "Item $i"
        }
        LDialogUtil.showDialogList(activity, "Title", arr, object : LDialogUtil.CallbackList {
            override fun onClick(position: Int) {
                LToast.show(activity, "Click position " + position + ", item: " + arr[position], loitp.basemaster.R.drawable.l_bkg_horizontal)
            }
        })
    }

    private fun showProgress() {
        testRun?.cancel(true)
        testRun = TestRun(activity)
        testRun?.execute()
    }

    private class TestRun internal constructor(private val context: Context) : AsyncTask<Void, Int, Void>() {
        private var progressDialog: ProgressDialog? = null

        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = LDialogUtil.showProgressDialog(context, 100, "Title",
                    "Message", false, ProgressDialog.STYLE_HORIZONTAL, null, null)
        }

        override fun doInBackground(vararg voids: Void): Void? {
            progressDialog?.max?.let {
                for (i in 0 until it) {
                    publishProgress(i)
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }
            return null
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                progressDialog?.progress = it
            }
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            progressDialog?.dismiss()
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Title")

        val input = EditText(activity)
        //input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(input)

        builder.setPositiveButton("OK") { dialog, which ->
            val text = input.text.toString()
            LToast.show(activity, "Text $text")
        }
        builder.setNegativeButton("Cancel") { dialog, which -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }
}
