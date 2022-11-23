package vn.loitp.app.activity.animation.basicTransitionActivity

import androidx.annotation.Keep

@Keep
class Item internal constructor(
    val name: String,
    val author: String,
    private val mFileName: String
) {

    val id: Int
        get() = name.hashCode() + mFileName.hashCode()

    val photoUrl: String
        get() = LARGE_BASE_URL + mFileName

    @Suppress("unused")
    val thumbnailUrl: String
        get() = THUMB_BASE_URL + mFileName

    companion object {

        private const val LARGE_BASE_URL =
            "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/large/"
        private const val THUMB_BASE_URL =
            "http://storage.googleapis.com/androiddevelopers/sample_data/activity_transition/thumbs/"

        var ITEMS = arrayOf(
            Item("Flying in the Light", "Romain Guy", "flying_in_the_light.jpg"),
            Item("Caterpillar", "Romain Guy", "caterpillar.jpg"),
            Item("Look Me in the Eye", "Romain Guy", "look_me_in_the_eye.jpg"),
            Item("Flamingo", "Romain Guy", "flamingo.jpg"),
            Item("Rainbow", "Romain Guy", "rainbow.jpg"),
            Item("Over there", "Romain Guy", "over_there.jpg"),
            Item("Jelly Fish 2", "Romain Guy", "jelly_fish_2.jpg"),
            Item("Lone Pine Sunset", "Romain Guy", "lone_pine_sunset.jpg")
        )

        fun getItem(id: Int): Item? {
            for (item in ITEMS) {
                if (item.id == id) {
                    return item
                }
            }
            return null
        }
    }
}
