package vn.loitp.app.activity.customviews.scrollablePanel

import androidx.annotation.Keep
import com.loitp.core.base.BaseModel
import java.util.* // ktlint-disable no-wildcard-imports

@Keep
class OrderInfo : BaseModel() {
    var id: Long = 0
    var guestName: String? = null
    var status: Status? = null
    var isBegin: Boolean = false

    enum class Status {
        CHECK_IN,
        REVERSE,
        BLANK;

        companion object {

            private val VALUES = Collections.unmodifiableList(listOf(*values()))
            private val SIZE = VALUES.size
            private val RANDOM = Random()

            @JvmStatic
            fun randomStatus(): Status {
                return VALUES[RANDOM.nextInt(SIZE)]
            }
        }
    }
}
