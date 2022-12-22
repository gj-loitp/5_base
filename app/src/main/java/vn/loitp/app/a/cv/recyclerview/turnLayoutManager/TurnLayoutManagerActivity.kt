package vn.loitp.app.a.cv.recyclerview.turnLayoutManager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import cdflynn.android.library.turn.TurnLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_0.*
import kotlinx.android.synthetic.main.activity_0.lActionBar
import kotlinx.android.synthetic.main.activity_turn_layout_manager.*
import kotlinx.android.synthetic.main.activity_video_exo_player2.*
import kotlinx.android.synthetic.main.view_controls_tlm.*
import vn.loitp.R

@LogTag("TurnLayoutManagerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TurnLayoutManagerActivity : BaseFontActivity() {

    private var layoutManager: TurnLayoutManager? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_turn_layout_manager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        lActionBar.apply {
            LUIUtil.setSafeOnClickListenerElastic(view = this.ivIconLeft, runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(view = it, runnable = {
                    LSocialUtil.openUrlInBrowser(
                        context = context, url = "https://github.com/cdflynn/turn-layout-manager"
                    )
                })
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = TurnLayoutManagerActivity::class.java.simpleName
        }

        val adapter = SampleAdapter(this)
        val radius = resources.getDimension(R.dimen.list_radius).toInt()
        val peek = resources.getDimension(R.dimen.list_peek).toInt()
        layoutManager = TurnLayoutManager(
            /* context = */ this,
            /* gravity = */ TurnLayoutManager.Gravity.START,
            /* orientation = */ TurnLayoutManager.Orientation.VERTICAL,
            /* radius = */ radius,
            /* peekDistance = */ peek,
            /* rotate = */ rotate.isChecked
        )
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        seekRadius.setOnSeekBarChangeListener(radiusListener)
        seekPeek.setOnSeekBarChangeListener(peekListener)
        seekRadius.progress = radius
        seekPeek.progress = peek
        gravity.onItemSelectedListener = gravityOptionsClickListener
        orientation.onItemSelectedListener = orientationOptionsClickListener
        gravity.adapter = GravityAdapter(this, R.layout.view_spinner_item_tlm)
        orientation.adapter = OrientationAdapter(this, R.layout.view_spinner_item_tlm)
        rotate.setOnCheckedChangeListener(rotateListener)
        tvControlHandle.setOnClickListener(controlsHandleClickListener)
    }

    private val radiusListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            tvRadius.text = resources.getString(R.string.radius_format, progress)
            if (fromUser) {
                layoutManager?.setRadius(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }
    }

    private val peekListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            tvPeekText.text = resources.getString(R.string.peek_format, progress)
            if (fromUser) {
                layoutManager?.setPeekDistance(progress)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // do nothing
        }
    }

    private val orientationOptionsClickListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        layoutManager?.orientation = TurnLayoutManager.VERTICAL
                        return
                    }
                    1 -> layoutManager?.orientation = TurnLayoutManager.HORIZONTAL
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    private val gravityOptionsClickListener: AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View, position: Int, id: Long
            ) {
                when (position) {
                    0 -> {
                        layoutManager?.setGravity(TurnLayoutManager.Gravity.START)
                        return
                    }
                    1 -> layoutManager?.setGravity(TurnLayoutManager.Gravity.END)
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    private val rotateListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        layoutManager?.setRotate(
            isChecked
        )
    }

    private val controlsHandleClickListener = View.OnClickListener {
        val translationY =
            if (control_panel.translationY == 0f) control_panel.height.toFloat() else 0f
        control_panel.animate().translationY(translationY).start()
        tvControlHandle.animate().translationY(translationY).start()
    }

    private class OrientationAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Vertical", "Horizontal"))

    private class GravityAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Start", "End"))
}
