package vn.loitp.up.a.cv.rv.netView

import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.common.NOT_FOUND
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.setScreenOrientation
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import vn.loitp.R
import vn.loitp.databinding.ANetViewBinding

@LogTag("NetViewActivity")
@IsFullScreen(false)
class NetViewActivity : BaseActivityFont() {
    companion object {
        const val MAX_SIZE = 100
    }

    private lateinit var binding: ANetViewBinding
    private var netAdapter = NetAdapter()
    private val isEnableAnimation = true

    override fun setLayoutResourceId(): Int {
        return NOT_FOUND
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ANetViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setScreenOrientation(isPortrait = true)
        setupViews()
    }

    private fun setupViews() {
        binding.lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(runnable = {
                onBaseBackPressed()
            })
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NetViewActivity::class.java.simpleName
        }
        netAdapter.onClickRootView = { net ->
            showShortInformation(net.name)
        }
        binding.rvNetView.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Stop only scrolling.
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        binding.rvNetView.layoutManager = GridLayoutManager(this, 1)

        if (isEnableAnimation) {
            val scaleAdapter = ScaleInAnimationAdapter(netAdapter)
            scaleAdapter.setDuration(1000)
            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            binding.rvNetView.adapter = scaleAdapter
        } else {
            binding.rvNetView.adapter = netAdapter
        }

        binding.btAddItem.setOnClickListener {
            addNet()
        }
        binding.btClearAll.setOnClickListener {
            netAdapter.clear()
        }
    }

    private fun addNet() {
        if (netAdapter.itemCount >= MAX_SIZE) {
            showShortInformation("Max size")
            return
        }
        val net = Net()
        net.name = "${netAdapter.itemCount + 1}"
        netAdapter.addNet(net)

        when (netAdapter.itemCount) {
            0 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            1 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            2 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            3 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            4 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            5 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            6 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            7 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            8 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            9 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            10 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            11 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            12 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            13 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            14 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            15 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            16 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            17 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            18 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            19 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            20 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            21 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            22 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            23 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            24 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            25 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            26 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            27 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            28 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            29 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            30 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            31 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            32 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            33 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            34 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            35 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            36 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            37 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            38 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            39 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            40 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            41 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            42 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            43 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            44 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            45 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            46 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            47 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            48 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            49 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            50 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            51 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            52 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            53 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            54 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            55 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            56 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            57 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            58 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            59 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            60 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            61 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            62 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            63 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            64 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            65 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            66 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            67 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            68 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            69 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            70 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            71 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            72 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            73 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            74 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            75 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            76 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            77 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            78 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            79 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            80 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            81 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            82 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            83 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            84 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            85 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            86 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            87 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            88 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            89 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            90 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            91 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            92 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            93 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            94 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            95 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            96 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            97 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            98 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            99 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            100 -> {
                binding.rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
        }
    }
}
