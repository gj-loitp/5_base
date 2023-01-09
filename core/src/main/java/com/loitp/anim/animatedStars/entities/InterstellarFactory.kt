package com.loitp.anim.animatedStars.entities

import com.loitp.anim.animatedStars.entities.stars.BaseStar
import com.loitp.anim.animatedStars.entities.stars.RoundyStar
import com.loitp.anim.animatedStars.entities.stars.ShinyStar
import com.loitp.anim.animatedStars.entities.stars.TinyStar

fun StarConstraints.create(
    x: Int,
    y: Int,
    color: Int,
    listener: BaseStar.StarCompleteListener
): Star {
    val starSize = this.getRandomStarSize()

    return if (starSize >= this.bigStarThreshold) {

        // big star ones randomly be Shiny or Round
        if (Math.random() < 0.7) {
            ShinyStar(starConstraints = this, x = x, y = y, color = color, listener = listener)
        } else {
            RoundyStar(starConstraints = this, x = x, y = y, color = color, listener = listener)
        }
    } else {
        // others will ne tiny
        TinyStar(starConstraints = this, x = x, y = y, color = color, listener = listener)
    }
}
