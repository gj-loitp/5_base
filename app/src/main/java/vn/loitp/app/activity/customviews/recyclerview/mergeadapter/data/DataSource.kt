package vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data

import com.core.common.Constants
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.Banner
import vn.loitp.app.activity.customviews.recyclerview.mergeadapter.data.model.User

object DataSource {

    fun getListUser() = ArrayList<User>().apply {
        for (i in 0..5) {
            add(User(i, "Loitp$i", Constants.URL_IMG_ANDROID))
        }
    }

    fun getBanner() = ArrayList<Banner>().apply {
        add(Banner(bannerImage = R.drawable.loitp))
    }
}
