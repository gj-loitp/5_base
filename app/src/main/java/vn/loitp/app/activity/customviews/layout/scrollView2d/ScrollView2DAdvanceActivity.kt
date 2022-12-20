package vn.loitp.app.activity.customviews.layout.scrollView2d

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitpcore.core.utilities.LUIUtil
import com.loitpcore.views.scrollView.LScrollView
import kotlinx.android.synthetic.main.activity_scrollview_2d_advance_layout.*
import vn.loitp.app.R

@LogTag("ScrollView2DAdvanceActivity")
@IsFullScreen(false)
class ScrollView2DAdvanceActivity : BaseFontActivity() {

    companion object {
        private const val WIDTH_PX = 300
        private const val HEIGHT_PX = 150
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_scrollview_2d_advance_layout
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        vg2.setOnScrollListener(object : LScrollView.ScrollListener {
            override fun onScrollChange(
                view: View,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                logD("vg2 setOnScrollListener $scrollX")
                vg4.scrollTo(scrollX, vg4.scrollY)
            }
        })
        vg3.setOnScrollListener(object : LScrollView.ScrollListener {
            override fun onScrollChange(
                view: View,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                logD("vg3 setOnScrollListener $scrollX")
                vg4.scrollTo(vg4.scrollX, scrollY)
            }
        })

        vg4.setScrollChangeListener { _: View?, x: Int, y: Int, oldX: Int, oldY: Int ->
            tvInfo.text = "setScrollChangeListener $x - $y - $oldX - $oldY"
            logD("vg4 setOnScrollListener $x")
            vg2.scrollTo(x, vg2.scrollY)
            vg3.scrollTo(vg3.scrollX, y)
        }
        btGenLine.setOnClickListener {
            if (btGenLine.isClickable) {
                btGenLine.isClickable = false
                btGenLine.setTextColor(Color.GRAY)
                LUIUtil.setSizeOfView(vg1, WIDTH_PX, HEIGHT_PX)
                LUIUtil.setSizeOfView(vg2, ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT_PX)
                LUIUtil.setSizeOfView(vg3, WIDTH_PX, ViewGroup.LayoutParams.MATCH_PARENT)
                LUIUtil.setSizeOfView(
                    vg4,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                Render(30, 24).execute()
                // new Render(7, 12).execute();
            }
        }
    }

    // check convert coroutine
    private inner class Render(private val column: Int, private val row: Int) :
        AsyncTask<Void, View, Void>() {
        override fun onPreExecute() {
            super.onPreExecute()
            logD("onPreExecute")
            pb.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg voids: Void): Void? {
            logD("doInBackground")
            genLine(column, row)
            return null
        }

        override fun onProgressUpdate(vararg values: View) {
            super.onProgressUpdate(*values)
            logD("onProgressUpdate " + System.currentTimeMillis())
            val child = values[0]
            val parent = values[1] as ViewGroup
            parent.addView(child)
        }

//        private fun sleep(mls: Int) {
//            try {
//                Thread.sleep(mls.toLong())
//            } catch (e: InterruptedException) {
//                e.printStackTrace()
//            }
//        }

        @SuppressLint("SetTextI18n")
        private fun genLine(column: Int, row: Int) {
            // gen view group 2
            for (i in 0 until column) {
                val button = Button(this@ScrollView2DAdvanceActivity)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "Date $i"
                button.setOnClickListener {
                    showShortInformation("Click " + button.text.toString())
                }
                publishProgress(button, ll2)
            }

            // gen view group 3
            for (i in 0 until row) {
                val button = Button(this@ScrollView2DAdvanceActivity)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "$i:00:00"
                button.setOnClickListener {
                    showShortInformation("Click " + button.text.toString())
                }
                publishProgress(button, ll3)
            }

            // gen view group 4
            for (i in 0 until row) {
                val linearLayout = LinearLayout(this@ScrollView2DAdvanceActivity)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, HEIGHT_PX * i, 0, 0)
                linearLayout.layoutParams = layoutParams
                linearLayout.orientation = LinearLayout.HORIZONTAL
                for (j in 0 until column) {
                    val button = Button(this@ScrollView2DAdvanceActivity)
                    button.setBackgroundResource(R.drawable.bg_square)
                    button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                    button.text = "Pos $i - $j"
                    button.setOnClickListener {
                        showShortInformation("Click " + button.text.toString())
                    }
                    linearLayout.addView(button)
                }
                publishProgress(linearLayout, rl4)
            }

            // add sticker img
            val sticker0 = ImageView(this@ScrollView2DAdvanceActivity)
            sticker0.setImageResource(R.drawable.loitp)
            sticker0.scaleType = ImageView.ScaleType.CENTER_CROP
            val rl0 = RelativeLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
            rl0.setMargins(WIDTH_PX, HEIGHT_PX * 7, 0, 0)
            sticker0.layoutParams = rl0
            publishProgress(sticker0, rl4)
            val sticker1 = ImageView(this@ScrollView2DAdvanceActivity)
            sticker1.setImageResource(R.drawable.loitp)
            sticker1.scaleType = ImageView.ScaleType.CENTER_CROP
            val rl1 =
                RelativeLayout.LayoutParams((WIDTH_PX * 2.5).toInt(), (HEIGHT_PX * 2.5).toInt())
            rl1.setMargins((WIDTH_PX * 1.5).toInt(), HEIGHT_PX * 2, 0, 0)
            sticker1.layoutParams = rl1
            publishProgress(sticker1, rl4)
        }

        override fun onPostExecute(aVoid: Void?) {
            super.onPostExecute(aVoid)
            logD("onPostExecute")
            pb.visibility = View.GONE
        }
    }
}
