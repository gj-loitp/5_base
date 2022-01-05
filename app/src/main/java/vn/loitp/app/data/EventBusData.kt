package vn.loitp.app.data

import org.greenrobot.eventbus.EventBus
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie

class EventBusData private constructor() {

    inner class ClickVideoEvent {
        var movie: Movie? = null
        var position: Int = 0
    }

    fun sendClickVideoEvent(movie: Movie, position: Int) {
        val clickVideoEvent = ClickVideoEvent()
        clickVideoEvent.movie = movie
        clickVideoEvent.position = position
        EventBus.getDefault().post(clickVideoEvent)
    }

    companion object {
        val instance = EventBusData()
    }
}
