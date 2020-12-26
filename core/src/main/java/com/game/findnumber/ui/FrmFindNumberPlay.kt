package com.game.findnumber.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.core.utilities.LAnimationUtil
import com.core.utilities.LImageUtil
import com.daimajia.androidanimations.library.Techniques
import com.game.findnumber.adapter.FindNumberItemAdapter
import com.game.findnumber.model.FindNumberItem
import com.game.findnumber.model.Level
import kotlinx.android.synthetic.main.l_frm_find_number_play.*

@LogTag("FrmFindNumberPlay")
class FrmFindNumberPlay(
        val level: Level
) : BaseFragment() {

    companion object {
        const val MAX_SIZE = 100
    }

    private var findNumberItemAdapter = FindNumberItemAdapter()
    private var numberTarget = 1

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_find_number_play
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData()
    }

    private fun setupViews() {
        //TODO loitpp custom background depend on level
        LImageUtil.load(context = context, any = R.drawable.bkg_2, imageView = ivBackground)
        findNumberItemAdapter.onClickRootView = { findNumberItem, position ->
            if (findNumberItem.name == numberTarget.toString()) {
                numberTarget++
                setupNumberTarget()

                findNumberItem.status = FindNumberItem.STATUS_CLOSE
                findNumberItemAdapter.updateFindNumberItem(position = position)

                if (numberTarget > findNumberItemAdapter.itemCount) {
                    winGame()
                }
            }
        }
        rvFindNumber.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Stop only scrolling.
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        rvFindNumber.adapter = findNumberItemAdapter
        setupNumberTarget()
    }

    private fun setupNumberTarget() {
        tvNumberTarget.text = "$numberTarget"
        LAnimationUtil.play(view = tvNumberTarget, techniques = Techniques.Flash)
    }

    private fun setupData() {
        var size = level.name
        if (size > MAX_SIZE) {
            size = MAX_SIZE
        }
        val listFindNumberItem = ArrayList<FindNumberItem>()
        for (i in 0..size) {
            val findNumberItem = FindNumberItem()
            findNumberItem.name = "${i + 1}"
            //TODO loitpp
            findNumberItem.rotate = 0f
//            findNumberItem.rotate = LDeviceUtil.getRandomNumber(360).toFloat()
            listFindNumberItem.add(findNumberItem)
        }
        listFindNumberItem.shuffle()

        val spanCount: Int
        when (listFindNumberItem.size) {
            0 -> {
                spanCount = 1
            }
            1 -> {
                spanCount = 1
            }
            2 -> {
                spanCount = 2
            }
            3 -> {
                spanCount = 2
            }
            4 -> {
                spanCount = 2
            }
            5 -> {
                spanCount = 2
            }
            6 -> {
                spanCount = 2
            }
            7 -> {
                spanCount = 3
            }
            8 -> {
                spanCount = 3
            }
            9 -> {
                spanCount = 3
            }
            10 -> {
                spanCount = 3
            }
            11 -> {
                spanCount = 3
            }
            12 -> {
                spanCount = 3
            }
            13 -> {
                spanCount = 4
            }
            14 -> {
                spanCount = 4
            }
            15 -> {
                spanCount = 4
            }
            16 -> {
                spanCount = 4
            }
            17 -> {
                spanCount = 4
            }
            18 -> {
                spanCount = 4
            }
            19 -> {
                spanCount = 4
            }
            20 -> {
                spanCount = 4
            }
            21 -> {
                spanCount = 5
            }
            22 -> {
                spanCount = 5
            }
            23 -> {
                spanCount = 5
            }
            24 -> {
                spanCount = 5
            }
            25 -> {
                spanCount = 5
            }
            26 -> {
                spanCount = 5
            }
            27 -> {
                spanCount = 5
            }
            28 -> {
                spanCount = 5
            }
            29 -> {
                spanCount = 5
            }
            30 -> {
                spanCount = 5
            }
            31 -> {
                spanCount = 6
            }
            32 -> {
                spanCount = 6
            }
            33 -> {
                spanCount = 6
            }
            34 -> {
                spanCount = 6
            }
            35 -> {
                spanCount = 6
            }
            36 -> {
                spanCount = 6
            }
            37 -> {
                spanCount = 6
            }
            38 -> {
                spanCount = 6
            }
            39 -> {
                spanCount = 6
            }
            40 -> {
                spanCount = 6
            }
            41 -> {
                spanCount = 6
            }
            42 -> {
                spanCount = 6
            }
            43 -> {
                spanCount = 7
            }
            44 -> {
                spanCount = 7
            }
            45 -> {
                spanCount = 7
            }
            46 -> {
                spanCount = 7
            }
            47 -> {
                spanCount = 7
            }
            48 -> {
                spanCount = 7
            }
            49 -> {
                spanCount = 7
            }
            50 -> {
                spanCount = 7
            }
            51 -> {
                spanCount = 7
            }
            52 -> {
                spanCount = 7
            }
            53 -> {
                spanCount = 7
            }
            54 -> {
                spanCount = 7
            }
            55 -> {
                spanCount = 7
            }
            56 -> {
                spanCount = 7
            }
            57 -> {
                spanCount = 8
            }
            58 -> {
                spanCount = 8
            }
            59 -> {
                spanCount = 8
            }
            60 -> {
                spanCount = 8
            }
            61 -> {
                spanCount = 8
            }
            62 -> {
                spanCount = 8
            }
            63 -> {
                spanCount = 8
            }
            64 -> {
                spanCount = 8
            }
            65 -> {
                spanCount = 8
            }
            66 -> {
                spanCount = 8
            }
            67 -> {
                spanCount = 8
            }
            68 -> {
                spanCount = 8
            }
            69 -> {
                spanCount = 8
            }
            70 -> {
                spanCount = 8
            }
            71 -> {
                spanCount = 8
            }
            72 -> {
                spanCount = 8
            }
            73 -> {
                spanCount = 9
            }
            74 -> {
                spanCount = 9
            }
            75 -> {
                spanCount = 9
            }
            76 -> {
                spanCount = 9
            }
            77 -> {
                spanCount = 9
            }
            78 -> {
                spanCount = 9
            }
            79 -> {
                spanCount = 9
            }
            80 -> {
                spanCount = 9
            }
            81 -> {
                spanCount = 9
            }
            82 -> {
                spanCount = 10
            }
            83 -> {
                spanCount = 10
            }
            84 -> {
                spanCount = 10
            }
            85 -> {
                spanCount = 10
            }
            86 -> {
                spanCount = 10
            }
            87 -> {
                spanCount = 10
            }
            88 -> {
                spanCount = 10
            }
            89 -> {
                spanCount = 10
            }
            90 -> {
                spanCount = 10
            }
            91 -> {
                spanCount = 10
            }
            92 -> {
                spanCount = 10
            }
            93 -> {
                spanCount = 10
            }
            94 -> {
                spanCount = 10
            }
            95 -> {
                spanCount = 10
            }
            96 -> {
                spanCount = 10
            }
            97 -> {
                spanCount = 10
            }
            98 -> {
                spanCount = 10
            }
            99 -> {
                spanCount = 10
            }
            100 -> {
                spanCount = 10
            }
            101 -> {
                spanCount = 10
            }
            else -> {
                spanCount = 1
            }
        }
//        logD("spanCount $spanCount, findNumberItemAdapter.itemCount: ${findNumberItemAdapter.itemCount}")
        findNumberItemAdapter.setListFindNumberItem(listNet = listFindNumberItem, spanCount = spanCount)
        rvFindNumber.layoutManager = GridLayoutManager(context, spanCount)
    }

    private fun winGame() {
        //TODO loitpp
        showLongInformation("WINNNNNNNNNN")
    }
}
