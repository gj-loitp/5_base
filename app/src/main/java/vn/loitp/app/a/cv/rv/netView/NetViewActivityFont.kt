package vn.loitp.app.a.cv.rv.netView

import android.os.Bundle
import android.view.MotionEvent
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SimpleOnItemTouchListener
import com.loitp.annotation.IsFullScreen
import com.loitp.annotation.LogTag
import com.loitp.core.base.BaseActivityFont
import com.loitp.core.ext.setSafeOnClickListenerElastic
import com.loitp.core.ext.setScreenOrientation
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.activity_net_view.*
import vn.loitp.R

@LogTag("NetViewActivity")
@IsFullScreen(false)
class NetViewActivityFont : BaseActivityFont() {
    companion object {
        const val MAX_SIZE = 100
    }

    private var netAdapter = NetAdapter()
    private val isEnableAnimation = true

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_net_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setScreenOrientation(isPortrait = true)
        setupViews()
    }

    private fun setupViews() {
        lActionBar.apply {
            this.ivIconLeft.setSafeOnClickListenerElastic(
                runnable = {
                    onBaseBackPressed()
                }
            )
            this.ivIconRight?.setImageResource(R.color.transparent)
            this.tvTitle?.text = NetViewActivityFont::class.java.simpleName
        }
        netAdapter.onClickRootView = { net ->
            showShortInformation(net.name)
        }
        rvNetView.addOnItemTouchListener(object : SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Stop only scrolling.
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        rvNetView.layoutManager = GridLayoutManager(this, 1)

        if (isEnableAnimation) {
            val scaleAdapter = ScaleInAnimationAdapter(netAdapter)
            scaleAdapter.setDuration(1000)
            scaleAdapter.setInterpolator(OvershootInterpolator())
            scaleAdapter.setFirstOnly(true)
            rvNetView.adapter = scaleAdapter
        } else {
            rvNetView.adapter = netAdapter
        }

        btAddItem.setOnClickListener {
            addNet()
        }
        btClearAll.setOnClickListener {
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
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            1 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 1)
            }
            2 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            3 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            4 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            5 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            6 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 2)
            }
            7 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            8 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            9 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            10 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            11 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            12 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 3)
            }
            13 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            14 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            15 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            16 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            17 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            18 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            19 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            20 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 4)
            }
            21 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            22 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            23 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            24 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            25 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            26 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            27 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            28 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            29 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            30 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 5)
            }
            31 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            32 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            33 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            34 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            35 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            36 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            37 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            38 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            39 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            40 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            41 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            42 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 6)
            }
            43 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            44 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            45 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            46 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            47 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            48 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            49 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            50 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            51 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            52 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            53 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            54 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            55 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            56 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 7)
            }
            57 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            58 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            59 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            60 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            61 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            62 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            63 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            64 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            65 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            66 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            67 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            68 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            69 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            70 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            71 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            72 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 8)
            }
            73 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            74 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            75 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            76 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            77 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            78 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            79 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            80 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            81 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 9)
            }
            82 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            83 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            84 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            85 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            86 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            87 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            88 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            89 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            90 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            91 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            92 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            93 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            94 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            95 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            96 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            97 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            98 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            99 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
            100 -> {
                rvNetView.layoutManager = GridLayoutManager(this, 10)
            }
        }
    }
}
