package com.game.findnumber.db

import com.R
import com.game.findnumber.model.Level

/**
 * Created by ©Loitp93 on 12/28/2020.
 * VinHMS
 * www.muathu@gmail.com
 */
class Db {
    companion object {

        fun genListLevel(): ArrayList<Level> {
            val listLevel = ArrayList<Level>()

            listLevel.add(Level(id = System.nanoTime().toString(), name = 1, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 2, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 3, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 4, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 5, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 6, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 7, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 8, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 9, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))
            listLevel.add(Level(id = System.nanoTime().toString(), name = 10, col = 2, row = 2, rotate = 0f, frame = R.drawable.flute_k5, status = Level.STATUS_LEVEL_OPEN, timeInMls = 0))

            return listLevel
        }
    }
}