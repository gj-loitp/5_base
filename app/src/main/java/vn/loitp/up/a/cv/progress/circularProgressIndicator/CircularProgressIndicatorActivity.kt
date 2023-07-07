package vn.loitp.up.a.cv.progress.circularProgressIndicator

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import android.widget.SimpleAdapter
import androidx.core.view.isVisible
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.views.loading.circularProgressIndicator.CircularProgressIndicator
import vn.loitp.BuildConfig
import vn.loitp.R
import vn.loitp.databinding.AProgressCircularProgressIndicatorBinding

@LogTag("CircularProgressIndicatorActivity")
@IsFullScreen(false)
class CircularProgressIndicatorActivity :
    BaseActivityFont(),
    View.OnClickListener,
    SeekBar.OnSeekBarChangeListener,
    ColorPickerDialogFragment.OnColorSelectedListener {

    private lateinit var binding: AProgressCircularProgressIndicatorBinding

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = AProgressCircularProgressIndicatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.isVisible = false
            this.tvTitle?.text = CircularProgressIndicatorActivity::class.java.simpleName
        }

        binding.circularProgress.maxProgress = 10000.0

        binding.progressColor.setOnClickListener(this)
        binding.progressBackgroundColor.setOnClickListener(this)
        binding.textColor.setOnClickListener(this)
        binding.dotColor.setOnClickListener(this)

        binding.progress.setOnSeekBarChangeListener(this)
        binding.progressStrokeWidth.setOnSeekBarChangeListener(this)
        binding.progressBackgroundStrokeWidth.setOnSeekBarChangeListener(this)
        binding.textSize.setOnSeekBarChangeListener(this)
        binding.dotWidth.setOnSeekBarChangeListener(this)

        binding.drawDot.setOnCheckedChangeListener { _, isChecked ->
            binding.circularProgress.setShouldDrawDot(isChecked)
            binding.dotWidth.isEnabled = isChecked
            binding.dotColor.isEnabled = isChecked
        }
        binding.useCustomTextAdapter.setOnCheckedChangeListener { _, isChecked ->
            binding.circularProgress.setProgressTextAdapter(
                if (isChecked) timeTextAdapter else null
            )
        }
        binding.fillBackground.setOnCheckedChangeListener { _, isChecked ->
            binding.circularProgress.isFillBackgroundEnabled = isChecked
        }

        binding.progressCap.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbCapButt ->
                    binding.circularProgress.progressStrokeCap =
                        CircularProgressIndicator.CAP_BUTT
                R.id.rbCapRound ->
                    binding.circularProgress.progressStrokeCap =
                        CircularProgressIndicator.CAP_ROUND
            }
        }

        binding.circularProgress.onProgressChangeListener =
            CircularProgressIndicator.OnProgressChangeListener { progress, maxProgress ->
                logD(String.format("Current: %1$.0f, max: %2$.0f", progress, maxProgress))
            }

        binding.animationSwitch.setOnCheckedChangeListener { _, isChecked ->
            binding.circularProgress.isAnimationEnabled = isChecked
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

        binding.gradientType.adapter = SimpleAdapter(
            this,
            listGradient,
            android.R.layout.simple_dropdown_item_1line,
            arrayOf("type"),
            intArrayOf(android.R.id.text1)
        )

        binding.gradientType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                listGradient[position]["value"]?.let { v ->
                    binding.circularProgress.setGradient(Integer.parseInt(v), Color.MAGENTA)
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

    override fun onProgressChanged(
        seekBar: SeekBar,
        progress: Int,
        fromUser: Boolean
    ) {
        when (seekBar.id) {
            R.id.progress -> binding.circularProgress.setCurrentProgress(progress.toDouble())
            R.id.progressStrokeWidth -> binding.circularProgress.setProgressStrokeWidthDp(progress)
            R.id.dotWidth -> binding.circularProgress.setDotWidthDp(progress)
            R.id.textSize -> binding.circularProgress.setTextSizeSp(progress)
            R.id.progressBackgroundStrokeWidth -> binding.circularProgress.setProgressBackgroundStrokeWidthDp(
                progress
            )
        }
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}

    override fun onStopTrackingTouch(seekBar: SeekBar) {}

    override fun onColorChosen(
        dialog: ColorPickerDialogFragment,
        r: Int,
        g: Int,
        b: Int
    ) {
        val tag = dialog.tag
        val color = Color.rgb(r, g, b)

        if (BuildConfig.DEBUG && tag == null) {
            error("Assertion failed")
        }

        when (tag) {
            "progressColor" -> binding.circularProgress.progressColor = color
            "progressBackgroundColor" -> binding.circularProgress.progressBackgroundColor = color
            "textColor" -> binding.circularProgress.textColor = color
            "dotColor" -> binding.circularProgress.dotColor = color
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
