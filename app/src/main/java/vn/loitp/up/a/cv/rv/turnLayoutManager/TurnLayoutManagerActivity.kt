package vn.loitp.up.a.cv.rv.turnLayoutManager

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
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ATurnLayoutManagerBinding

@LogTag("TurnLayoutManagerActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class TurnLayoutManagerActivity : BaseActivityFont() {
    private var layoutManager: TurnLayoutManager? = null
    private lateinit var binding: ATurnLayoutManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ATurnLayoutManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    @SuppressLint("SetTextI18n")
    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.let {
                it.setSafeOnClickListenerElastic(runnable = {
                    context.openUrlInBrowser(
                        url = "https://github.com/cdflynn/turn-layout-manager"
                    )
                })
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
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
            /* rotate = */ binding.rotate.isChecked
        )
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
        binding.seekRadius.setOnSeekBarChangeListener(radiusListener)
        binding.seekPeek.setOnSeekBarChangeListener(peekListener)
        binding.seekRadius.progress = radius
        binding.seekPeek.progress = peek
        binding.gravity.onItemSelectedListener = gravityOptionsClickListener
        binding.orientation.onItemSelectedListener = orientationOptionsClickListener
        binding.gravity.adapter = GravityAdapter(this, R.layout.view_spinner_item_tlm)
        binding.orientation.adapter = OrientationAdapter(this, R.layout.view_spinner_item_tlm)
        binding.rotate.setOnCheckedChangeListener(rotateListener)
        binding.tvControlHandle.setOnClickListener(controlsHandleClickListener)
    }

    private val radiusListener: OnSeekBarChangeListener = object : OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            binding.tvRadius.text = resources.getString(R.string.radius_format, progress)
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
            binding.tvPeekText.text = resources.getString(R.string.peek_format, progress)
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
            if (binding.rlControlPanel.translationY == 0f) binding.rlControlPanel.height.toFloat() else 0f
        binding.rlControlPanel.animate().translationY(translationY).start()
        binding.tvControlHandle.animate().translationY(translationY).start()
    }

    private class OrientationAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Vertical", "Horizontal"))

    private class GravityAdapter(
        context: Context, @LayoutRes resource: Int
    ) : ArrayAdapter<String?>(context, resource, arrayOf("Start", "End"))
}
