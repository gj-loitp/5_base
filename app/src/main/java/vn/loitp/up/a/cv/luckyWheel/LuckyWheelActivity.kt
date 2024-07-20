package vn.loitp.up.a.cv.luckyWheel

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.isVisible
import com.bluehomestudio.luckywheel.WheelItem
import com.loitp.annotation.IsAutoAnimation
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.openUrlInBrowser
import com.loitp.core.ext.setSafeOnClickListenerElastic
import vn.loitp.R
import vn.loitp.databinding.ALuckyWheelBinding

@LogTag("LuckyWheelActivity")
@IsFullScreen(false)
@IsAutoAnimation(true)
class LuckyWheelActivity : BaseActivityFont() {
    private lateinit var binding: ALuckyWheelBinding
    private var wheelItems = ArrayList<WheelItem>()

//    override fun setLayoutResourceId(): Int {
//        return NOT_FOUND
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ALuckyWheelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generateWheelItems()
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
                        url = "https://github.com/mmoamenn/LuckyWheel_Android"
                    )
                })
                it.isVisible = true
                it.setImageResource(com.loitp.R.drawable.ic_baseline_code_48)
            }
            this.tvTitle?.text = LuckyWheelActivity::class.java.simpleName
        }

        binding.lw.addWheelItems(wheelItems)
        binding.lw.setTarget(1)
        binding.lw.setLuckyWheelReachTheTarget {
            showShortInformation("Target Reached")
        }

        binding.start.setOnClickListener {
            binding.lw.rotateWheelTo(1)
        }
    }

    private fun generateWheelItems() {
        wheelItems.add(
            WheelItem(
                Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_1
                ), "100 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_2
                ), "0 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#F00E6F"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_3
                ), "30 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_4
                ), "6000 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#fc6c6c"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_6
                ), "9 $"
            )
        )
        wheelItems.add(
            WheelItem(
                Color.parseColor("#00E6FF"), BitmapFactory.decodeResource(
                    resources, R.drawable.face_7
                ), "20 $"
            )
        )
    }
}
