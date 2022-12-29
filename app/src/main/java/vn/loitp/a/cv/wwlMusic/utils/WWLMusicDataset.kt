package vn.loitp.a.cv.wwlMusic.utils

import com.loitp.core.utils.AppUtils.Companion.appPackageName
import vn.loitp.R

object WWLMusicDataset {

    @JvmField
    var datasetItems = ArrayList<DatasetItem>()

    class DatasetItem(id: Int) {
        var id: Int

        @JvmField
        var title: String

        @JvmField
        var subtitle: String

        @JvmField
        var url: String

        init {
            val tmpUrl = "android.resource://" + appPackageName + "/" + R.raw.vid_bigbuckbunny
            this.id = id
            title = String.format("This is element #%d", id)
            subtitle = "Pops Music"
            url = tmpUrl
        }
    }

    init {
        for (i in 0 until 50) {
            datasetItems.add(DatasetItem(i + 1))
        }
    }
}
