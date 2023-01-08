package vn.loitp.a.cv.layout.scrollView2d

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
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSizeOfView
import com.loitp.views.sv.LScrollView
import kotlinx.android.synthetic.main.a_layout_scrollview_2d_advance.*
import vn.loitp.R

@LogTag("ScrollView2DAdvanceActivity")
@IsFullScreen(false)
class ScrollView2DAdvanceActivityFont : BaseActivityFont() {

    companion object {
        private const val WIDTH_PX = 300
        private const val HEIGHT_PX = 150
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.a_layout_scrollview_2d_advance
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
                vg1.setSizeOfView(width = WIDTH_PX, height = HEIGHT_PX)
                vg1.setSizeOfView(width = ViewGroup.LayoutParams.MATCH_PARENT, height = HEIGHT_PX)
                vg3.setSizeOfView(width = WIDTH_PX, height = ViewGroup.LayoutParams.MATCH_PARENT)
                vg4.setSizeOfView(
                    width = ViewGroup.LayoutParams.MATCH_PARENT,
                    height = ViewGroup.LayoutParams.MATCH_PARENT
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
                val button = Button(this@ScrollView2DAdvanceActivityFont)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "Date $i"
                button.setOnClickListener {
                    showShortInformation("Click " + button.text.toString())
                }
                publishProgress(button, ll2)
            }

            // gen view group 3
            for (i in 0 until row) {
                val button = Button(this@ScrollView2DAdvanceActivityFont)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "$i:00:00"
                button.setOnClickListener {
                    showShortInformation("Click " + button.text.toString())
                }
                publishProgress(button, ll3)
            }

            // gen view group 4
            for (i in 0 until row) {
                val linearLayout = LinearLayout(this@ScrollView2DAdvanceActivityFont)
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.setMargins(0, HEIGHT_PX * i, 0, 0)
                linearLayout.layoutParams = layoutParams
                linearLayout.orientation = LinearLayout.HORIZONTAL
                for (j in 0 until column) {
                    val button = Button(this@ScrollView2DAdvanceActivityFont)
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
            val sticker0 = ImageView(this@ScrollView2DAdvanceActivityFont)
            sticker0.setImageResource(R.drawable.loitp)
            sticker0.scaleType = ImageView.ScaleType.CENTER_CROP
            val rl0 = RelativeLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
            rl0.setMargins(WIDTH_PX, HEIGHT_PX * 7, 0, 0)
            sticker0.layoutParams = rl0
            publishProgress(sticker0, rl4)
            val sticker1 = ImageView(this@ScrollView2DAdvanceActivityFont)
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
