package vn.loitp.app.activity.customviews.wwlmusic.utils

import com.utils.util.AppUtils.Companion.appPackageName
import vn.loitp.app.R

object WWLMusicDataset {

    @JvmField
    var datasetItems: Array<DatasetItem?>

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
        val n = 50
        datasetItems = arrayOfNulls(n)
        for (i in 0 until n) {
            datasetItems[i] = DatasetItem(i + 1)
        }
    }
}
