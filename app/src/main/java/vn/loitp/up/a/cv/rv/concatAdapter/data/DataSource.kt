package vn.loitp.up.a.cv.rv.concatAdapter.data

import com.loitp.core.common.URL_IMG_ANDROID
import vn.loitp.R
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.Banner
import vn.loitp.up.a.cv.rv.concatAdapter.data.model.User

object DataSource {

    fun getListUser() = ArrayList<User>().apply {
        for (i in 0..5) {
            add(User(i, "Loitp$i", URL_IMG_ANDROID))
        }
    }

    fun getBanner() = ArrayList<Banner>().apply {
        add(Banner(bannerImage = R.drawable.loitp))
    }
}
