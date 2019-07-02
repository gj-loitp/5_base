package vn.loitp.app.activity.customviews.scrollablepanel

import java.util.*

/**
 * Created by kelin on 16-11-18.
 */

class OrderInfo {
    var id: Long = 0
    var guestName: String? = null
    var status: Status? = null
    var isBegin: Boolean = false

    enum class Status {
        CHECK_IN,
        REVERSE,
        BLANK;

        companion object {

            private val VALUES = Collections.unmodifiableList(Arrays.asList(*values()))
            private val SIZE = VALUES.size
            private val RANDOM = Random()

            @JvmStatic
            fun randomStatus(): Status {
                return VALUES[RANDOM.nextInt(SIZE)]
            }
        }
    }
}
