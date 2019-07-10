package vn.loitp.data

import com.core.utilities.LLog

/**
 * Created by www.muathu@gmail.com on 12/26/2017.
 */

class AdmobData private constructor() {

    var idAdmobFull: String? = ""
        get() {
            if (field == null || field!!.isEmpty()) {
                LLog.e(javaClass.simpleName, "idAdmobFull == null || idAdmobFull.isEmpty()")
                return ""
            }
            return field
        }

    companion object {
        val instance = AdmobData()
    }
}

