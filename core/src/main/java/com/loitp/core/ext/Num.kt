package com.loitp.core.ext

import java.util.*

/**
 * Created by Loitp on 06,January,2023
 * Galaxy One company,
 * Vietnam
 * +840766040293
 * freuss47@gmail.com
 */
fun Int.getRandomNumber(): Int {
    val r = Random()
    return r.nextInt(this)
}
