package vn.loitp.up.a.cv.rv.concatAdapter2.model

import com.loitp.core.base.BaseModel

class DummyContent : BaseModel() {
    var id: Int? = null
    var title: String? = null

    var listContentDetail: ArrayList<ContentDetail> = ArrayList()
}
