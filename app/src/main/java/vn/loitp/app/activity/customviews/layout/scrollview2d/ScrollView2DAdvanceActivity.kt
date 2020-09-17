package vn.loitp.app.activity.customviews.layout.scrollview2d

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
import com.annotation.LayoutId
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import com.views.LToast.showShort
import com.views.scrollview.LHorizontalScrollView
import com.views.scrollview.LScrollView
import kotlinx.android.synthetic.main.activity_layout_scrollview_2d_advance.*
import vn.loitp.app.R

@LayoutId(R.layout.activity_layout_scrollview_2d_advance)
class ScrollView2DAdvanceActivity : BaseFontActivity() {
    companion object {
        private const val WIDTH_PX = 300
        private const val HEIGHT_PX = 150
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        vg2.setOnScrollListener(object : LHorizontalScrollView.ScrollListener {
            override fun onScrollChange(view: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                logD("vg2 setOnScrollListener $scrollX")
                vg4.scrollTo(scrollX, vg4.scrollY)
            }
        })
        vg3.setOnScrollListener(object : LScrollView.ScrollListener {
            override fun onScrollChange(view: View, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                logD("vg3 setOnScrollListener $scrollX")
                vg4.scrollTo(vg4.scrollX, scrollY)
            }
        })

        vg4.setScrollChangeListner { _: View?, x: Int, y: Int, oldX: Int, oldY: Int ->
            tvInfo.text = "setScrollChangeListner $x - $y - $oldX - $oldY"
            logD("vg4 setOnScrollListener $x")
            vg2.scrollTo(x, vg2.scrollY)
            vg3.scrollTo(vg3.scrollX, y)
        }
        btGenLine.setOnClickListener {
            if (btGenLine.isClickable) {
                btGenLine.isClickable = false
                btGenLine.setTextColor(Color.GRAY)
                LUIUtil.setSize(vg1, WIDTH_PX, HEIGHT_PX)
                LUIUtil.setSize(vg2, ViewGroup.LayoutParams.MATCH_PARENT, HEIGHT_PX)
                LUIUtil.setSize(vg3, WIDTH_PX, ViewGroup.LayoutParams.MATCH_PARENT)
                LUIUtil.setSize(vg4, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                Render(30, 24).execute()
                //new Render(7, 12).execute();
            }
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    //TODO convert coroutine
    private inner class Render internal constructor(private val column: Int, private val row: Int) : AsyncTask<Void, View, Void>() {
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
            //gen view group 2
            for (i in 0 until column) {
                val button = Button(activity)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "Date $i"
                button.setOnClickListener {
                    showShort(activity, "Click " + button.text.toString(), R.drawable.l_bkg_horizontal)
                }
                publishProgress(button, ll2)
            }

            //gen view group 3
            for (i in 0 until row) {
                val button = Button(activity)
                button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                button.text = "$i:00:00"
                button.setOnClickListener {
                    showShort(activity, "Click " + button.text.toString(), R.drawable.l_bkg_horizontal)
                }
                publishProgress(button, ll3)
            }

            //gen view group 4
            for (i in 0 until row) {
                val linearLayout = LinearLayout(activity)
                val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                layoutParams.setMargins(0, HEIGHT_PX * i, 0, 0)
                linearLayout.layoutParams = layoutParams
                linearLayout.orientation = LinearLayout.HORIZONTAL
                for (j in 0 until column) {
                    val button = Button(activity)
                    button.setBackgroundResource(R.drawable.bg_square)
                    button.layoutParams = LinearLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
                    button.text = "Pos $i - $j"
                    button.setOnClickListener {
                        showShort(activity, "Click " + button.text.toString(), R.drawable.l_bkg_horizontal)
                    }
                    linearLayout.addView(button)
                }
                publishProgress(linearLayout, rl4)
            }

            //add sticker img
            val sticker0 = ImageView(activity)
            sticker0.setImageResource(R.drawable.loitp)
            sticker0.scaleType = ImageView.ScaleType.CENTER_CROP
            val rl0 = RelativeLayout.LayoutParams(WIDTH_PX, HEIGHT_PX)
            rl0.setMargins(WIDTH_PX, HEIGHT_PX * 7, 0, 0)
            sticker0.layoutParams = rl0
            publishProgress(sticker0, rl4)
            val sticker1 = ImageView(activity)
            sticker1.setImageResource(R.drawable.loitp)
            sticker1.scaleType = ImageView.ScaleType.CENTER_CROP
            val rl1 = RelativeLayout.LayoutParams((WIDTH_PX * 2.5).toInt(), (HEIGHT_PX * 2.5).toInt())
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