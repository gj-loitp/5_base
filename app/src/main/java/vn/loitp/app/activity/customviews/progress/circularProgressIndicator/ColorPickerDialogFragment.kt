package vn.loitp.app.activity.customviews.progress.circularProgressIndicator

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import vn.loitp.R

class ColorPickerDialogFragment : BottomSheetDialogFragment() {

    private var onColorSelectedListener: OnColorSelectedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.frm_dialog_color_picker, container, false)

        val sbRed = rootView.findViewById<SeekBar>(R.id.sbRed)
        val sbGreen = rootView.findViewById<SeekBar>(R.id.sbGreen)
        val sbBlue = rootView.findViewById<SeekBar>(R.id.sbBlue)

        val mode = PorterDuff.Mode.SRC_ATOP

        //TODO fix setColorFilter
        sbRed.progressDrawable.setColorFilter(Color.rgb(sbRed.progress, 0, 0), mode)
        sbGreen.progressDrawable.setColorFilter(Color.rgb(0, sbGreen.progress, 0), mode)
        sbBlue.progressDrawable.setColorFilter(Color.rgb(0, 0, sbBlue.progress), mode)

        sbRed.thumb.setColorFilter(Color.rgb(sbRed.progress, 0, 0), mode)
        sbGreen.thumb.setColorFilter(Color.rgb(0, sbGreen.progress, 0), mode)
        sbBlue.thumb.setColorFilter(Color.rgb(0, 0, sbBlue.progress), mode)

        val viewColorResult = rootView.findViewById<View>(R.id.viewColorResult)
        val btSelectColorResult = rootView.findViewById<Button>(R.id.btSelectColorResult)

        val seekBarChangeListener = object : DefaultSeekbarChangeListener() {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val redProgress = sbRed.progress
                val greenProgress = sbGreen.progress
                val blueProgress = sbBlue.progress

                viewColorResult.setBackgroundColor(
                    Color.rgb(
                        redProgress,
                        greenProgress,
                        blueProgress
                    )
                )

                when (seekBar.id) {
                    R.id.sbRed -> {
                        seekBar.progressDrawable.setColorFilter(Color.rgb(redProgress, 0, 0), mode)
                        seekBar.thumb.setColorFilter(Color.rgb(redProgress, 0, 0), mode)
                    }
                    R.id.sbGreen -> {
                        seekBar.progressDrawable.setColorFilter(
                            Color.rgb(0, greenProgress, 0),
                            mode
                        )
                        seekBar.thumb.setColorFilter(Color.rgb(0, greenProgress, 0), mode)
                    }
                    R.id.sbBlue -> {
                        seekBar.progressDrawable.setColorFilter(Color.rgb(0, 0, blueProgress), mode)
                        seekBar.thumb.setColorFilter(Color.rgb(0, 0, blueProgress), mode)
                    }
                }
            }
        }

        sbRed.setOnSeekBarChangeListener(seekBarChangeListener)
        sbGreen.setOnSeekBarChangeListener(seekBarChangeListener)
        sbBlue.setOnSeekBarChangeListener(seekBarChangeListener)

        btSelectColorResult.setOnClickListener {
            onColorSelectedListener?.onColorChosen(
                dialog = this@ColorPickerDialogFragment,
                r = sbRed.progress, g = sbGreen.progress, b = sbBlue.progress
            )
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
