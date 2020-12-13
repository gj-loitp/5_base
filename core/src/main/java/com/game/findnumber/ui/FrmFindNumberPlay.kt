package com.game.findnumber.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFragment
import com.game.findnumber.adapter.FindNumberItemAdapter
import com.game.findnumber.model.FindNumberItem
import kotlinx.android.synthetic.main.l_frm_find_number_play.*

@LogTag("FrmFindNumberPlay")
class FrmFindNumberPlay : BaseFragment() {

    companion object {
        const val MAX_SIZE = 100
    }

    private var netAdapter = FindNumberItemAdapter()

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_find_number_play
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupData(10)
    }

    private fun setupViews() {
        //TODO loitpp custom background depend on level
        ivBackground.setBackgroundResource(R.drawable.bkg_2)
        netAdapter.onClickRootView = { net ->
            //TODO loitpp
        }
        rvFindNumber.addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                // Stop only scrolling.
                return rv.scrollState == RecyclerView.SCROLL_STATE_DRAGGING
            }
        })
        rvFindNumber.layoutManager = GridLayoutManager(context, 1)
        rvFindNumber.adapter = netAdapter
    }

    private fun setupData(size: Int) {
        if (size > MAX_SIZE) {
            return
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
        netAdapter.setListFindNumberItem(listFindNumberItem)

        when (netAdapter.itemCount) {
            0 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 1)
            }
            1 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 1)
            }
            2 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 2)
            }
            3 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 2)
            }
            4 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 2)
            }
            5 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 2)
            }
            6 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 2)
            }
            7 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            8 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            9 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            10 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            11 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            12 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 3)
            }
            13 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            14 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            15 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            16 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            17 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            18 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            19 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            20 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 4)
            }
            21 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            22 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            23 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            24 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            25 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            26 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            27 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            28 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            29 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            30 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 5)
            }
            31 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            32 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            33 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            34 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            35 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            36 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            37 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            38 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            39 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            40 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            41 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            42 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 6)
            }
            43 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            44 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            45 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            46 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            47 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            48 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            49 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            50 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            51 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            52 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            53 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            54 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            55 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            56 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 7)
            }
            57 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            58 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            59 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            60 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            61 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            62 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            63 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            64 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            65 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            66 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            67 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            68 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            69 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            70 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            71 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            72 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 8)
            }
            73 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            74 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            75 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            76 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            77 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            78 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            79 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            80 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            81 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 9)
            }
            82 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            83 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            84 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            85 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            86 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            87 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            88 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            89 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            90 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            91 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            92 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            93 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            94 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            95 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            96 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            97 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            98 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            99 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
            100 -> {
                rvFindNumber.layoutManager = GridLayoutManager(context, 10)
            }
        }
    }
}
