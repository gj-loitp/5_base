package vn.loitp.app.activity.customviews.progressloadingview.circularprogressindicator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.SimpleAdapter
import com.core.base.BaseFontActivity
import com.views.progressloadingview.circularprogressindicator.CircularProgressIndicator
import kotlinx.android.synthetic.main.activity_circular_progress_indicator.*
import loitp.basemaster.R
import java.util.*

class CircularProgressIndicatorActivity : BaseFontActivity(), View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, ColorPickerDialogFragment.OnColorSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        circularProgress.maxProgress = 10000.0

        progressColor.setOnClickListener(this)
        progressBackgroundColor.setOnClickListener(this)
        textColor.setOnClickListener(this)
        dotColor.setOnClickListener(this)

        progress.setOnSeekBarChangeListener(this)
        progressStrokeWidth.setOnSeekBarChangeListener(this)
        progressBackgroundStrokeWidth.setOnSeekBarChangeListener(this)
        textSize.setOnSeekBarChangeListener(this)
        dotWidth.setOnSeekBarChangeListener(this)

        drawDot.setOnCheckedChangeListener { buttonView, isChecked ->
            circularProgress.setShouldDrawDot(isChecked)
            dotWidth.isEnabled = isChecked
            dotColor.isEnabled = isChecked
        }
        useCustomTextAdapter.setOnCheckedChangeListener { buttonView, isChecked -> circularProgress.setProgressTextAdapter(if (isChecked) TIME_TEXT_ADAPTER else null) }
        fillBackground.setOnCheckedChangeListener { buttonView, isChecked -> circularProgress.isFillBackgroundEnabled = isChecked }

        progressCap.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_cap_butt -> circularProgress.progressStrokeCap = CircularProgressIndicator.CAP_BUTT
                R.id.rb_cap_round -> circularProgress.progressStrokeCap = CircularProgressIndicator.CAP_ROUND
            }
        }

        circularProgress.onProgressChangeListener = CircularProgressIndicator.OnProgressChangeListener { progress, maxProgress -> Log.d("PROGRESS", String.format("Current: %1$.0f, max: %2$.0f", progress, maxProgress)) }

        animationSwitch.setOnCheckedChangeListener { buttonView, isChecked -> circularProgress.isAnimationEnabled = isChecked }

        val gradients = ArrayList<HashMap<String, String>>()
        var gradient = HashMap<String, String>()
        gradient["type"] = "No gradient"
        gradient["value"] = "0"
        gradients.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Linear"
        gradient["value"] = "1"
        gradients.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Radial"
        gradient["value"] = "2"
        gradients.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Sweep"
        gradient["value"] = "3"
        gradients.add(gradient)

        gradientType.adapter = SimpleAdapter(
                this,
                gradients,
                android.R.layout.simple_dropdown_item_1line,
                arrayOf("type"),
                intArrayOf(android.R.id.text1)
        )

        gradientType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                circularProgress.setGradient(Integer.parseInt(gradients[position]["value"]), Color.MAGENTA)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_circular_progress_indicator
    }

    override fun onClick(v: View) {
        val dialog = ColorPickerDialogFragment()
        dialog.setOnColorSelectedListener(this)
        var tag: String? = null
        when (v.id) {
            R.id.progressColor -> tag = "progressColor"
            R.id.progressBackgroundColor -> tag = "progressBackgroundColor"
            R.id.textColor -> tag = "textColor"
            R.id.dotColor -> tag = "dotColor"
        }

        dialog.show(supportFragmentManager, tag)
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        when (seekBar.id) {
            R.id.progress -> circularProgress.setCurrentProgress(progress.toDouble())
            R.id.progressStrokeWidth -> circularProgress.setProgressStrokeWidthDp(progress)
            R.id.dotWidth -> circularProgress.setDotWidthDp(progress)
            R.id.textSize -> circularProgress.setTextSizeSp(progress)
            R.id.progressBackgroundStrokeWidth -> circularProgress.setProgressBackgroundStrokeWidthDp(progress)
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onColorChosen(dialog: ColorPickerDialogFragment, r: Int, g: Int, b: Int) {
        val tag = dialog.tag
        val color = Color.rgb(r, g, b)

        assert(tag != null)

        when (tag) {
            "progressColor" -> circularProgress.progressColor = color
            "progressBackgroundColor" -> circularProgress.progressBackgroundColor = color
            "textColor" -> circularProgress.textColor = color
            "dotColor" -> circularProgress.dotColor = color
        }
    }

    private val TIME_TEXT_ADAPTER = CircularProgressIndicator.ProgressTextAdapter { mTime ->
        var time = mTime
        val hours = (time / 3600).toInt()
        time %= 3600.0
        val minutes = (time / 60).toInt()
        val seconds = (time % 60).toInt()
        val sb = StringBuilder()
        if (hours < 10) {
            sb.append(0)
        }
        sb.append(hours).append(":")
        if (minutes < 10) {
            sb.append(0)
        }
        sb.append(minutes).append(":")
        if (seconds < 10) {
            sb.append(0)
        }
        sb.append(seconds)
        sb.toString()
    }
}
