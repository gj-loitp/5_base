package vn.loitp.a.cv.layout.autoScrollContent

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseFontActivity
import com.loitp.core.utilities.LSocialUtil
import com.loitp.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.a_auto_scroll_content.*
import vn.loitp.R
import vn.loitp.databinding.AAutoScrollContentBinding

@LogTag("AutoScrollContentActivity")
@IsFullScreen(false)
@IsAutoAnimation(false)
class AutoScrollContentActivity : BaseFontActivity() {

    private lateinit var binding: AAutoScrollContentBinding
    private val lm = LinearLayoutManager(this)
    private var reverse = false
    private var loop = false
    private var canTouch = false
    private var orientationLm = LinearLayoutManager.HORIZONTAL
    private var currentSpeed = 40

    override fun setLayoutResourceId(): Int {
        return R.layout.a_auto_scroll_content
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AAutoScrollContentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            this.ivIconRight?.let {
                LUIUtil.setSafeOnClickListenerElastic(
                    view = it,
                    runnable = {
                        LSocialUtil.openUrlInBrowser(
                            context = context,
                            url = "https://github.com/angelmmg90/AutoScrollContent"
                        )
                    }
                )
                it.isVisible = true
                it.setImageResource(R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = AutoScrollContentActivity::class.java.simpleName
        }

        lm.orientation = LinearLayoutManager.HORIZONTAL
        binding.rvAutoScrollContent.layoutManager = lm

        setUpAutoScrollContent(
            listOf(
                ExampleModel("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed eiusmod tempor incidunt ut labore et dolore magna aliqua.")
            )
        ) {
            Toast.makeText(this, "Item clicked", Toast.LENGTH_SHORT).show()
        }
        setUpSwLoopChecked()
        setUpSwReverseChecked()
        setUpSwHorizontalVerticalChecked()
        setUpSwCanTouchChecked()
        setUpSeekBarCurrentSpeed()
    }

    private fun setUpAutoScrollContent(
        messagesList: List<ExampleModel>,
        onItemClicked: (String) -> Unit
    ) {

        val adapter = ExampleAdapter().apply {
            submitList(messagesList)
        }
        binding.rvAutoScrollContent.adapter = adapter
        binding.rvAutoScrollContent.setItemClickListener { viewHolder, position ->
            viewHolder?.let {
                adapter.onLinkItem(
                    holder = viewHolder,
                    position = position,
                    onLinkItem = onItemClicked
                )
            }
        }
    }

    private fun setUpSwLoopChecked() {
        binding.swLoppContent.setOnCheckedChangeListener { _, isChecked ->
            loop = isChecked
            setUpLoop()
            setUpSpeed()
            setUpReverse()
            setUpCanTouch()
            setUpOrientationLm()
        }
    }

    private fun setUpSwReverseChecked() {
        binding.swReverseContent.setOnCheckedChangeListener { _, isChecked ->
            reverse = isChecked
            setUpLoop()
            setUpSpeed()
            setUpReverse()
            setUpCanTouch()
            setUpOrientationLm()
        }
    }

    private fun setUpSwCanTouchChecked() {
        binding.swCanTouchContent.setOnCheckedChangeListener { _, isChecked ->
            canTouch = isChecked
            setUpLoop()
            setUpSpeed()
            setUpReverse()
            setUpCanTouch()
            setUpOrientationLm()
        }
    }

    private fun setUpSwHorizontalVerticalChecked() {
        binding.swHorizontalVerticalContent.setOnCheckedChangeListener { _, isChecked ->
            orientationLm = if (isChecked) {
                LinearLayoutManager.VERTICAL
            } else {
                LinearLayoutManager.HORIZONTAL
            }

            setUpLoop()
            setUpSpeed()
            setUpReverse()
            setUpCanTouch()
            setUpOrientationLm()
        }
    }

    private fun setUpSeekBarCurrentSpeed() {
        binding.seekbar.progress = currentSpeed
        binding.seekbar.max = 100
        binding.seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                currentSpeed = seek.progress
                setUpSpeed()
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                currentSpeed = seek.progress
                setUpSpeed()
                showShortInformation("Progress is: " + seek.progress + "%")
            }
        })
    }

    private fun setUpSpeed() {
        openAutoScroll()
    }

    private fun setUpLoop() {
        binding.rvAutoScrollContent.setLoopEnabled(loop)
        openAutoScroll()
    }

    private fun setUpReverse() {
        openAutoScroll()
    }

    private fun setUpCanTouch() {
        binding.rvAutoScrollContent.setCanTouch(canTouch)
    }

    private fun openAutoScroll() {
        binding.rvAutoScrollContent.openAutoScroll(speed = currentSpeed, reverse = reverse)
    }

    @SuppressLint("WrongConstant")
    private fun setUpOrientationLm() {
        lm.orientation = orientationLm
        binding.rvAutoScrollContent.layoutManager = lm
    }
}
