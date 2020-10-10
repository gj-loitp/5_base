package vn.loitp.app.activity.api.truyentranhtuan.model.chap

import com.core.base.BaseModel
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

class Chaps : BaseModel(){
    @SerializedName("chap")
    @Expose
    var chap: List<Chap> = ArrayList()

}
