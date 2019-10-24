package vn.loitp.app.activity.customviews.recyclerview.parallaxrecyclerviewyayandroid

import java.util.*

/**
 * Created by www.muathu@gmail.com on 1/22/2018.
 */

class FakeData private constructor() {

    var stringList: List<String> = ArrayList()

    companion object {
        val instance = FakeData()
    }
}
