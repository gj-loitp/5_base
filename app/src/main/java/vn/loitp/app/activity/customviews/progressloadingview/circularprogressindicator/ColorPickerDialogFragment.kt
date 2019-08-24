package vn.loitp.app.activity.customviews.progressloadingview.circularprogressindicator

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

import loitp.basemaster.R

/**
 * Created by Anton on 13.03.2018.
 */

class ColorPickerDialogFragment : BottomSheetDialogFragment() {

    private var onColorSelectedListener: OnColorSelectedListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.dialog_color_picker, container, false)

        val red = rootView.findViewById<SeekBar>(R.id.sb_red)
        val green = rootView.findViewById<SeekBar>(R.id.sb_green)
        val blue = rootView.findViewById<SeekBar>(R.id.sb_blue)

        val mode = PorterDuff.Mode.SRC_ATOP

        red.progressDrawable.setColorFilter(Color.rgb(red.progress, 0, 0), mode)
        green.progressDrawable.setColorFilter(Color.rgb(0, green.progress, 0), mode)
        blue.progressDrawable.setColorFilter(Color.rgb(0, 0, blue.progress), mode)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            red.thumb.setColorFilter(Color.rgb(red.progress, 0, 0), mode)
            green.thumb.setColorFilter(Color.rgb(0, green.progress, 0), mode)
            blue.thumb.setColorFilter(Color.rgb(0, 0, blue.progress), mode)
        }

        val colorResult = rootView.findViewById<View>(R.id.color_result)
        val selectColor = rootView.findViewById<Button>(R.id.btn_select_color_result)

        val seekBarChangeListener = object : DefaultSeekbarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val redProgress = red.progress
                val greenProgress = green.progress
                val blueProgress = blue.progress

                colorResult.setBackgroundColor(Color.rgb(redProgress, greenProgress, blueProgress))

                when (seekBar.id) {
                    R.id.sb_red -> {
                        seekBar.progressDrawable.setColorFilter(Color.rgb(redProgress, 0, 0), mode)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            seekBar.thumb.setColorFilter(Color.rgb(redProgress, 0, 0), mode)
                        }
                    }
                    R.id.sb_green -> {
                        seekBar.progressDrawable.setColorFilter(Color.rgb(0, greenProgress, 0), mode)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            seekBar.thumb.setColorFilter(Color.rgb(0, greenProgress, 0), mode)
                        }
                    }
                    R.id.sb_blue -> {
                        seekBar.progressDrawable.setColorFilter(Color.rgb(0, 0, blueProgress), mode)
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            seekBar.thumb.setColorFilter(Color.rgb(0, 0, blueProgress), mode)
                        }
                    }
                }
            }
        }

        red.setOnSeekBarChangeListener(seekBarChangeListener)
        green.setOnSeekBarChangeListener(seekBarChangeListener)
        blue.setOnSeekBarChangeListener(seekBarChangeListener)

        selectColor.setOnClickListener {
            onColorSelectedListener!!.onColorChosen(this@ColorPickerDialogFragment,
                    red.progress, green.progress, blue.progress)
            dismiss()
        }

        return rootView
    }

    fun setOnColorSelectedListener(onColorSelectedListener: OnColorSelectedListener) {
        this.onColorSelectedListener = onColorSelectedListener
    }

    interface OnColorSelectedListener {
        fun onColorChosen(dialog: ColorPickerDialogFragment, r: Int, g: Int, b: Int)
    }
}
