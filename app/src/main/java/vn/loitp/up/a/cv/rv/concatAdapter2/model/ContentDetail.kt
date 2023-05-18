package vn.loitp.up.a.cv.rv.concatAdapter2.model

import com.loitp.core.base.BaseModel

class ContentDetail : BaseModel() {
    var name: String? = null
    var img: String? = null
    var isSelected: Boolean? = null

    //map for parent
    var isParentTitle: Boolean? = null
    var parentId: Int? = null
    var parentTitle: String? = null
}
