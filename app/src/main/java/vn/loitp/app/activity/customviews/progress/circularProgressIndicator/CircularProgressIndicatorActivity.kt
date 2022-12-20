package vn.loitp.app.activity.customviews.progress.circularProgressIndicator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.SimpleAdapter
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LUIUtil
import com.loitpcore.views.progressLoadingView.circularProgressIndicator.CircularProgressIndicator
import kotlinx.android.synthetic.main.activity_progress_circular_progress_indicator.*
import vn.loitp.app.BuildConfig
import vn.loitp.app.R

@LogTag("CircularProgressIndicatorActivity")
@IsFullScreen(false)
class CircularProgressIndicatorActivity :
    BaseFontActivity(),
    View.OnClickListener,
    SeekBar.OnSeekBarChangeListener,
    ColorPickerDialogFragment.OnColorSelectedListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_progress_circular_progress_indicator
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
            this.tvTitle?.text = CircularProgressIndicatorActivity::class.java.simpleName
        }

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

        drawDot.setOnCheckedChangeListener { _, isChecked ->
            circularProgress.setShouldDrawDot(isChecked)
            dotWidth.isEnabled = isChecked
            dotColor.isEnabled = isChecked
        }
        useCustomTextAdapter.setOnCheckedChangeListener { _, isChecked ->
            circularProgress.setProgressTextAdapter(
                if (isChecked) timeTextAdapter else null
            )
        }
        fillBackground.setOnCheckedChangeListener { _, isChecked ->
            circularProgress.isFillBackgroundEnabled = isChecked
        }

        progressCap.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbCapButt ->
                    circularProgress.progressStrokeCap =
                        CircularProgressIndicator.CAP_BUTT
                R.id.rbCapRound ->
                    circularProgress.progressStrokeCap =
                        CircularProgressIndicator.CAP_ROUND
            }
        }

        circularProgress.onProgressChangeListener =
            CircularProgressIndicator.OnProgressChangeListener { progress, maxProgress ->
                logD(String.format("Current: %1$.0f, max: %2$.0f", progress, maxProgress))
            }

        animationSwitch.setOnCheckedChangeListener { _, isChecked ->
            circularProgress.isAnimationEnabled = isChecked
        }

        val listGradient = ArrayList<HashMap<String, String>>()
        var gradient = HashMap<String, String>()
        gradient["type"] = "No gradient"
        gradient["value"] = "0"
        listGradient.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Linear"
        gradient["value"] = "1"
        listGradient.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Radial"
        gradient["value"] = "2"
        listGradient.add(gradient)

        gradient = HashMap()
        gradient["type"] = "Sweep"
        gradient["value"] = "3"
        listGradient.add(gradient)

        gradientType.adapter = SimpleAdapter(
            this,
            listGradient,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("type"),
            intArrayOf(android.R.id.text1)
        )

        gradientType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                listGradient[position]["value"]?.let { v ->
                    circularProgress.setGradient(Integer.parseInt(v), Color.MAGENTA)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
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
            R.id.progressBackgroundStrokeWidth -> circularProgress.setProgressBackgroundStrokeWidthDp(
                progress
            )
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onColorChosen(dialog: ColorPickerDialogFragment, r: Int, g: Int, b: Int) {
        val tag = dialog.tag
        val color = Color.rgb(r, g, b)

        if (BuildConfig.DEBUG && tag == null) {
            error("Assertion failed")
        }

        when (tag) {
            "progressColor" -> circularProgress.progressColor = color
            "progressBackgroundColor" -> circularProgress.progressBackgroundColor = color
            "textColor" -> circularProgress.textColor = color
            "dotColor" -> circularProgress.dotColor = color
        }
    }

    private val timeTextAdapter = CircularProgressIndicator.ProgressTextAdapter { mTime ->
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
