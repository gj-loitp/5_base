package vn.loitp.app.activity.customviews.dialog.original

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.EditText
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LDialogUtil
import com.loitpcore.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_original_dialog.*
import vn.loitp.app.R

@LogTag("DialogOriginalActivity")
@IsFullScreen(false)
class DialogOriginalActivity : BaseFontActivity(), OnClickListener {
    private var testRun: TestRun? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_original_dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(
                view = this.ivIconLeft,
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = DialogOriginalActivity::class.java.simpleName
        }
        btShow1.setOnClickListener(this)
        btShow2.setOnClickListener(this)
        btShow3.setOnClickListener(this)
        btShowList.setOnClickListener(this)
        btProgressDialog.setOnClickListener(this)
        btInputDialog.setOnClickListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        testRun?.cancel(true)
    }

    override fun onClick(v: View) {
        when (v) {
            btShow1 -> show1()
            btShow2 -> show2()
            btShow3 -> show3()
            btShowList -> showList()
            btProgressDialog -> showProgress()
            btInputDialog -> {
                showInputDialog()
            }
        }
    }

    private fun show1() {
        LDialogUtil.showDialog1(
            context = this,
            title = "Title",
            msg = "Msg",
            button1 = "Button 1",
            onClickButton1 = {
                showShortInformation("Click 1")
            }
        )
    }

    private fun show2() {
        LDialogUtil.showDialog2(
            context = this,
            title = "Title",
            msg = "Msg",
            button1 = "Button 1",
            button2 = "Button 2",
            onClickButton1 = {
                showShortInformation("Click 1")
            },
            onClickButton2 = {
                showShortInformation("Click 2")
            }
        )
    }

    private fun show3() {
        LDialogUtil.showDialog3(
            context = this,
            title = "Title",
            msg = "Msg",
            button1 = "Button 1",
            button2 = "Button 2",
            button3 = "Button 3",
            onClickButton1 = {
                showShortInformation("Click 1")
            },
            onClickButton2 = {
                showShortInformation("Click 2")
            },
            onClickButton3 = {
                showShortInformation("Click 3")
            }
        )
    }

    private fun showList() {
        val size = 50
        val arr = arrayOfNulls<String?>(size)
        for (i in 0 until size) {
            arr[i] = "Item $i"
        }
        LDialogUtil.showDialogList(
            context = this,
            title = "Title",
            arr = arr,
            onClick = { position ->
                showShortInformation("Click position " + position + ", item: " + arr[position])
            }
        )
    }

    private fun showProgress() {
        testRun?.cancel(true)
        testRun = TestRun(this)
        testRun?.execute()
    }

    private class TestRun(private val context: Context) :
        AsyncTask<Void, Int, Void>() {
        private var progressDialog: ProgressDialog? = null

        @Deprecated("Deprecated in Java")
        override fun onPreExecute() {
            super.onPreExecute()
            progressDialog = LDialogUtil.showProgressDialog(
                context = context,
                max = 100,
                title = "Title",
                msg = "Message",
                isCancelAble = false,
                style = ProgressDialog.STYLE_HORIZONTAL,
                buttonTitle = null
            )
        }

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg voids: Void): Void? {
            progressDialog?.max?.let {
                for (i in 0 until it) {
                    publishProgress(i)
                    try {
                        Thread.sleep(25)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            return null
        }

        @Deprecated("Deprecated in Java")
        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            values[0]?.let {
                progressDialog?.progress = it
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            progressDialog?.dismiss()
        }
    }

    private fun showInputDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Title")

        val editText = EditText(this)
        // input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        builder.setView(editText)

        builder.setPositiveButton("OK") { _, _ ->
            val text = editText.text.toString()
            showShortInformation("Text $text")
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        val dialog = builder.create()
        dialog.show()
    }
}
