package vn.loitp.app.a.cv.recyclerview.concatAdapter.data

import com.loitp.core.common.Constants
import vn.loitp.R
import vn.loitp.app.a.cv.recyclerview.concatAdapter.data.model.Banner
import vn.loitp.app.a.cv.recyclerview.concatAdapter.data.model.User

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
